package com.balc2013.albanote.view

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.balc2013.albanote.R
import com.balc2013.albanote.etc.Util
import com.balc2013.albanote.etc.Util.makeToast
import com.balc2013.albanote.exception.AuthenticationPeriodHasExpiredException
import com.balc2013.albanote.exception.OverRequestException
import com.balc2013.albanote.interfaces.BaseViewInterface
import com.balc2013.albanote.service.HitupFcmListenerService
import com.balc2013.albanote.view.FcmDataProcessActivity.Companion.TO_ACTIVITY_RESULT_CODE
import com.balc2013.albanote.view.customview.CustomProgressView
import com.balc2013.albanote.view.customview.CustomRefreshView
import com.balc2013.albanote.viewmodel.BaseViewModel

abstract class BaseActivity : AppCompatActivity(), BaseViewInterface {
    lateinit var baseViewModel: BaseViewModel
    var baseBinding: ViewDataBinding? = null

    var refreshView: CustomRefreshView? = null

    var progressView: CustomProgressView? = null

    override var initUnit: () -> Unit = {
        setView()
    }

    abstract fun setObserve()
    abstract fun setView()

    @SuppressLint("LongLogTag")
    fun setExceptionHandler(baseVm: BaseViewModel, binding: ViewDataBinding?) {
        supportActionBar?.hide()
        if (binding == null) return

        baseBinding = binding
        baseViewModel = baseVm
        baseViewModel.apiFailEvent.observe({ lifecycle }, {
            if (it == null) return@observe
            val stackToTrace = it.stackTraceToString()
            when {
                stackToTrace.contains(AuthenticationPeriodHasExpiredException::class.java.name) -> {
                    baseViewModel.startLoginLiveData.value = true
                }
                stackToTrace.contains(OverRequestException::class.java.name) -> {
                    makeToast(this, "잠시 후 다시 시도해 주세요.")
//                    baseViewModel.isRefreshed.value = true
                }
                else -> {
                    Log.e("baseFragmentExceptionHandler", it.stackTraceToString())
                }
            }
        })

        baseViewModel.sendToast.observe({ lifecycle }, {
            if (it != null) {
                makeToast(binding.root.context, it)
                baseViewModel.sendToast.value = null
            }
        })

        baseViewModel.isProgress.observe({ lifecycle }) {
            if (it) {
                Util.isSoftKeyView(window.decorView.rootView, View.GONE)
                if (progressView == null) {
                    val layout = baseBinding!!.root.findViewById<ConstraintLayout>(R.id.container)
                    progressView = CustomProgressView(this)
                    progressView!!.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    layout.addView(progressView)
                } else {
                    progressView!!.visibility = View.VISIBLE
                }
            } else if (progressView != null) {
                progressView!!.visibility = View.GONE
            }
        }

        baseViewModel.isRefreshed.observe({ lifecycle }, {
            if (it) {
                if (refreshView == null) {
                    val layout = baseBinding!!.root.findViewById<ConstraintLayout>(R.id.container)
                    refreshView = CustomRefreshView(this) {
                        baseViewModel.onRefresh.value = true
                        makeToast(this, "onRefresh")
                    }
                    refreshView!!.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    layout.addView(refreshView)
                } else {
                    refreshView!!.visibility = View.VISIBLE
                }
            }
        })

        baseViewModel.onRefresh.observe({ lifecycle }, {
            if (it) {
                initUnit.invoke()
                refreshView?.visibility = View.GONE
            }
        })

        setView()
        setObserve()
    }

    private val mActivityToActivityPassingData = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                when (intent.getIntExtra(TO_ACTIVITY_RESULT_CODE, -1)) {
//                    FcmDataProcessActivity.INTENT_RESULT_CODE_BLIND -> {
//                        showBlindStatus()
//                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mActivityToActivityPassingData)
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mActivityToActivityPassingData)
        LocalBroadcastManager.getInstance(this).registerReceiver(
            mActivityToActivityPassingData,
            IntentFilter(HitupFcmListenerService.TO_ALL_ACTIVITY_BROADCAST)
        )
    }
}

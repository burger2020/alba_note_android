package com.balc2013.albanote.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.balc2013.albanote.R
import com.balc2013.albanote.etc.Util
import com.balc2013.albanote.exception.AuthenticationPeriodHasExpiredException
import com.balc2013.albanote.exception.OverRequestException
import com.balc2013.albanote.interfaces.BaseViewInterface
import com.balc2013.albanote.view.customview.CustomProgressView
import com.balc2013.albanote.view.customview.CustomRefreshView
import com.balc2013.albanote.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseFragment : Fragment(), BaseViewInterface {
    lateinit var baseViewModel: BaseViewModel
    var baseBinding: ViewDataBinding? = null

    var refreshView: CustomRefreshView? = null

    var progressView: CustomProgressView? = null

    override var initUnit: () -> Unit = {
        setView()
    }

    abstract fun setObserve()
    abstract fun setView()

    fun getPersistentView(binding: ViewDataBinding?): View {
        baseBinding = binding

        return binding!!.root
    }

    @SuppressLint("LongLogTag")
    fun setAllProcess(baseVm: BaseViewModel, binding: ViewDataBinding): View {
        baseBinding = binding
        baseViewModel = baseVm
        baseViewModel.apiFailEvent.observe({ lifecycle }, {
            Log.d("OverRequestException_name_", OverRequestException::class.java.name)
            if (it == null) return@observe
            val stackToTrace = it.stackTraceToString()
            when {
                stackToTrace.contains(AuthenticationPeriodHasExpiredException::class.java.name) -> {
                    baseViewModel.startLoginLiveData.value = true
                }
                stackToTrace.contains(OverRequestException::class.java.name) -> {
                    Util.makeToast(requireContext(), "잠시 후 다시 시도해 주세요.")
//                    baseViewModel.isRefreshed.value = true
                }
                else -> {
                    Log.e("baseFragmentExceptionHandler", it.stackTraceToString())
                }
            }
        })

        baseViewModel.sendToast.observe({ lifecycle }, {
            if(it != null){
                Util.makeToast(binding.root.context, it)
                baseViewModel.sendToast.value = null
            }
        })

        baseViewModel.onRefresh.observe({ lifecycle }, {
            if (it) {
                refreshView?.visibility = View.GONE
            }
        })

        baseViewModel.isProgress.observe({ lifecycle }) {
            if (it) {
                Util.isSoftKeyView(requireActivity().window.decorView.rootView, View.GONE)
                if (progressView == null) {
                    val layout = baseBinding!!.root.findViewById<ConstraintLayout>(R.id.container)

                    progressView = CustomProgressView(requireContext())
                    progressView!!.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    layout.addView(progressView)
                } else {
                    progressView!!.visibility = View.VISIBLE
                }
                CoroutineScope(Dispatchers.IO).launch {
                    delay(5000)
                    launch(Dispatchers.Main) {
                        baseViewModel.isProgress.value = false
                    }
                }
            } else {
                if (progressView != null) {
                    progressView!!.visibility = View.GONE
                }
            }
        }

        baseViewModel.isRefreshed.observe({ lifecycle }, {
            if (it) {
                if (refreshView == null) {
                    val layout = baseBinding!!.root.findViewById<ConstraintLayout>(R.id.container)
                    refreshView = CustomRefreshView(requireContext()) {
                        baseViewModel.onRefresh.value = true
//                        Util.makeToast(requireContext(), "onRefresh")
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

        setView()
        setObserve()

        return binding.root
    }
}
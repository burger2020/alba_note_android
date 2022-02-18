package com.balc2013.albanote.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.balc2013.albanote.domain.dto.member.MemberDTO
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

open class BaseViewModel : ViewModel() {
    /**
     * setExceptionHandler(vm, binding) 메서드 호출해야 사용가능
     * */
    val apiFailEvent = MutableLiveData<Throwable?>(null)

    val startLoginLiveData = MutableLiveData(false)
    val isRefreshed = MutableLiveData(false)
    val onRefresh = MutableLiveData(false)
    val isProgress = MutableLiveData(false)
    val sendToast = MutableLiveData<String?>(null)

    var hasInitializedRootView = false

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("throwable", throwable.stackTraceToString())
        viewModelScope.launch(Dispatchers.Main) {
            apiFailEvent.value = throwable
        }
    }

    protected val ioDispatchers = Dispatchers.IO + coroutineExceptionHandler

    fun coroutineException(response: Response<MemberDTO>, unit: Unit) {
        Log.d("response_error_body", response.errorBody().toString())
        Log.d("response_code", response.code().toString())
        Log.d("response_message", response.message())
        Log.d("response_headers", response.headers().toString())
        return unit
    }
}
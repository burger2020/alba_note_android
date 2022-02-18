package com.balc2013.albanote.viewmodel

import androidx.lifecycle.viewModelScope
import com.balc2013.albanote.GlobalApplication.Companion.diffServerTime
import com.balc2013.albanote.etc.LocalDBManager
import com.balc2013.albanote.repository.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val localDb: LocalDBManager
) : BaseViewModel() {

    /**
     * 로그인 상태 확인 및 토큰 유효값인지 확인
     * */
    fun isValidTokenOrLoin() {
        val tokenInfo = localDb.getMyTokenInfo()
        val myInfo = localDb.getMyInfo()
        if (tokenInfo?.accessToken.isNullOrBlank() || myInfo?.name.isNullOrBlank()) {
            onStartLogin()
        } else {
            viewModelScope.launch(ioDispatchers) {
//                val response = futureInvestRepository.getUUID(myInfo!!.id!!)
//                myInfo!!.id = response.id
//                localDb.setMyInfo(myInfo)

//                onPermissionDialog()
//                onStartHomeView()
                getCurrentServerTime()
//                if (myInfo!!.memberTerms.marketing)
//                    Firebase.messaging.subscribeToTopic(FcmTopic.FCM_TOPIC_NORMAL_INFORM.name)
            }
        }
    }

    fun onStartLogin(){
        //todo 로그인 화면 ㄲ
    }

    private fun getCurrentServerTime() {
        viewModelScope.launch(ioDispatchers) {
            diffServerTime =
                System.currentTimeMillis() - memberRepository.getCurrentServerTime()
        }
    }
}
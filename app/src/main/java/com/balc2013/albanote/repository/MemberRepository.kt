package com.balc2013.albanote.repository

import com.balc2013.albanote.api.MemberApi
import com.balc2013.albanote.domain.dto.request.BodyWithMemberIdRequestDTO
import com.balc2013.albanote.etc.LocalDBManager

class MemberRepository(
    private val api: MemberApi, localDBManager: LocalDBManager
) :
    BaseRepository(api, localDBManager) {

    suspend fun postFcmToken(fcmToken: String): Boolean {
        setInitInfo()
        val body = BodyWithMemberIdRequestDTO(fcmToken, memberId)
        val response = api.putMemberPushToken(body, accessToken)
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            errorHandler(response)
            postFcmToken(fcmToken)
        }
    }
}
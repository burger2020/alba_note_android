package com.balc2013.albanote.api

import com.balc2013.albanote.domain.dto.request.BodyWithMemberIdRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT

interface MemberApi : BaseApi {

    @PUT("/fcmToken")
    suspend fun putMemberPushToken(
        @Body body: BodyWithMemberIdRequestDTO<String>,
        @Header("Authorization") authorization: String
    ): Response<Boolean>
}
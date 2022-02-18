package com.balc2013.albanote.api

import com.balc2013.albanote.domain.dto.request.member.MemberLoginRequestDTO
import com.balc2013.albanote.domain.dto.member.MemberTokenResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface BaseApi {

    @POST("/member-service/refreshToken")
    suspend fun postRefreshToken(
        @Body dto: MemberLoginRequestDTO,
        @Header("Authorization") authorization: String //refreshToken
    ): Response<MemberTokenResponseDTO>

    @GET("/currentTime")
    suspend fun getCurrentServerTime(
        @Header("Authorization") authorization: String
    ): Response<Long>
}
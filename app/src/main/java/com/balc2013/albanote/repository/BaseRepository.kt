package com.balc2013.albanote.repository

import android.util.Log
import com.balc2013.albanote.api.BaseApi
import com.balc2013.albanote.domain.dto.ErrorDTO
import com.balc2013.albanote.domain.dto.request.member.MemberLoginRequestDTO
import com.balc2013.albanote.etc.AppConfig
import com.balc2013.albanote.etc.LocalDBManager
import com.balc2013.albanote.exception.ApiRequestException
import com.balc2013.albanote.exception.AuthenticationPeriodHasExpiredException
import com.balc2013.albanote.exception.OverRequestException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

open class BaseRepository constructor(
    private val baseApi: BaseApi,
    private val localDBManager: LocalDBManager
) {
    val memberId: String get() = localDBManager.getMyUUID()
    val accessToken: String get() = localDBManager.getMyAccessToken()

    var beforeErrorCode: Int = -1
    var errorCount = 0
    var retryNum = 0

    /** 서버 현재 시간 조회 **/
    suspend fun getCurrentServerTime(): Long {
        setInitInfo()
        val response = baseApi.getCurrentServerTime(accessToken)
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            errorHandler(response)
            getCurrentServerTime()
        }
    }

    @Throws(Exception::class)
    suspend fun <T> errorHandler(response: Response<T>): Boolean {
        if (response.code() == 401) {
            val id = localDBManager.getMyId() ?: throw AuthenticationPeriodHasExpiredException()
            val pwd = localDBManager.getMyPwd() ?: throw AuthenticationPeriodHasExpiredException()
            val dto = MemberLoginRequestDTO(id, pwd)
            val refreshTokenResponse =
                baseApi.postRefreshToken(dto, localDBManager.getMyRefreshToken())
            when {
                refreshTokenResponse.isSuccessful -> {
                    val tokenModel = refreshTokenResponse.body()!!.convertToModel()
                    CoroutineScope(Dispatchers.IO).launch { localDBManager.setMyToken(tokenModel) }
                }
                refreshTokenResponse.code() == 401 -> { // refresh token invalid
                    throw AuthenticationPeriodHasExpiredException()
                }
                refreshTokenResponse.code() == 403 -> { // refresh token invalid
                    throw AuthenticationPeriodHasExpiredException()
                }
            }
        } else if (beforeErrorCode == response.code()) {
            delay(AppConfig.API_RECONNECT_TIME)
            errorCount++
            if (errorCount > retryNum) {
                beforeErrorCode = -1
                errorCount = 0
                Log.e("errorMessage = ", response.message())
                throw OverRequestException()
            }
        }
        // okhttp 커스텀 인터셉터
//        else if (response.code() == AppConfig.SOCKET_TIME_OUT_CODE) {
//            throw SocketTimeoutException()
//        }
        else if (response.code() in 500..599) {
            beforeErrorCode = response.code()
            errorCount = 0
        } else {
            val json = response.errorBody()?.string().toString()
            val errorDTO = ErrorDTO(JSONObject(json))
            when (errorDTO.code) {
                //member 6xx
//                600 -> throw BlindMemberAccessException()

                else -> throw ApiRequestException(response.code(), response.message())
            }
        }
        return true
    }

    fun setInitInfo() {
        if (memberId == "" || accessToken == "Bearer ") {
            // 여기서 에러나서 로그인 화면 넘어감
            throw AuthenticationPeriodHasExpiredException()
        }
    }
}
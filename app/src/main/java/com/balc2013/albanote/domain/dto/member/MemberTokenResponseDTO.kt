package com.balc2013.albanote.domain.dto.member

import com.balc2013.albanote.domain.dto.BaseDTO
import com.balc2013.albanote.domain.model.MemberTokenData
import com.google.gson.annotations.SerializedName

class MemberTokenResponseDTO(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
) : BaseDTO<MemberTokenData> {
    override fun convertToModel(): MemberTokenData = MemberTokenData(accessToken, refreshToken)
}
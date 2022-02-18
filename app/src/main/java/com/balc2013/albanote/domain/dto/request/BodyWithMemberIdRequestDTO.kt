package com.balc2013.albanote.domain.dto.request

import com.google.gson.annotations.SerializedName

class BodyWithMemberIdRequestDTO<T>(
    @SerializedName("body") val body: T,
    @SerializedName("memberId") val memberId: String
) 
package com.balc2013.albanote.domain.dto.member

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MemberSimpleDTO(
    @SerializedName("memberId") val memberId: String,
    @SerializedName("name") val name: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("imageUrl") val imageUrl: String? = null,
) : Serializable
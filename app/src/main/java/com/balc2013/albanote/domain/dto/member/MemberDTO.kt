package com.balc2013.albanote.domain.dto.member

import com.balc2013.albanote.domain.dto.BaseDTO
import com.balc2013.albanote.domain.model.Member
import com.google.gson.annotations.SerializedName

class MemberDTO(
    @SerializedName("uuid") var uuid: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("pwd") val pwd: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("nickname") var nickname: String? = null,
    @SerializedName("imageUrl") var imageUrl: String? = null,
    @SerializedName("memberTokenInfo") var memberTokenInfo: MemberTokenResponseDTO?
) : BaseDTO<Member> {
    override fun convertToModel(): Member {
        return Member(
            uuid, pwd, id, name, nickname, imageUrl
        )
    }
}
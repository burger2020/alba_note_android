package com.balc2013.albanote.domain.model

import com.balc2013.albanote.domain.dto.member.MemberSimpleDTO
import java.io.Serializable

data class Member(
    var uuid: String? = null,
    var pwd: String? = null,
    var id: String? = null,
    var name: String? = null,
    var nickname: String? = null,
    var imageUrl: String? = null,
//    var memberTerms: MemberTermsDTO = MemberTermsDTO(),
) : Serializable {
    fun convertToSimpleDTO(): MemberSimpleDTO {
        return MemberSimpleDTO(id ?: "", name ?: "", nickname ?: "", imageUrl)
    }
}
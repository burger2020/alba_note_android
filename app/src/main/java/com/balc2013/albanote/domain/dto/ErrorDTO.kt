package com.balc2013.albanote.domain.dto

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

class ErrorDTO(
    @SerializedName("message") var message: String? = null,
    @SerializedName("code") var code: Int? = null
) {
    constructor(jsonObject: JSONObject) : this(
        jsonObject.getString("message"),
        jsonObject.getInt("code")
    )
}
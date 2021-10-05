package com.example.fitplan.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("access_token")
    @Expose
    var accessToken: String,

    @SerializedName("token_type")
    @Expose
    var tokenType: String,

    @SerializedName("expires_in")
    @Expose
    var expiresIn: Int,

    @SerializedName("scope")
    @Expose
    var scope: String,

    @SerializedName("jti")
    @Expose
    var jti: String
)
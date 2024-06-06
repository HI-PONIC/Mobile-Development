package com.example.hi_ponic.data.Response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("identifier")
	val identifier: String? = null,

	@field:SerializedName("password")
	val password: String? = null
)

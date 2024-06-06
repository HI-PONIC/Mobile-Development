package com.example.hi_ponic.data.Response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null

)

package com.example.hi_ponic.data.Response

import com.google.gson.annotations.SerializedName

data class PredictConditionResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)

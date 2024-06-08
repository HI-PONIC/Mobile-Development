package com.example.hi_ponic.data.Response

import com.google.gson.annotations.SerializedName

data class SensorResponse(

	@field:SerializedName("sensorData")
	val sensorData: SensorData? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class SensorData(

	@field:SerializedName("temp")
	val temp: Float? = null,

	@field:SerializedName("tds")
	val tds: Float? = null,

	@field:SerializedName("ph")
	val ph: Float? = null,

	@field:SerializedName("humidity")
	val humidity: Float? = null
)

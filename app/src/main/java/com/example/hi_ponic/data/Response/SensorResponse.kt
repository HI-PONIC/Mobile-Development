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
	val temp: Int? = null,

	@field:SerializedName("tds")
	val tds: Int? = null,

	@field:SerializedName("ph")
	val ph: Int? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null
)

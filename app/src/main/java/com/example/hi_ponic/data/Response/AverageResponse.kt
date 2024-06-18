package com.example.hi_ponic.data.response

import com.google.gson.annotations.SerializedName

data class AverageResponse(

	@field:SerializedName("averageData")
	val averageData: AverageData? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class AverageData(

	@field:SerializedName("avg_ph")
	val avgPh: Float? = null,

	@field:SerializedName("avg_temp")
	val avgTemp: Float? = null,

	@field:SerializedName("avg_humidity")
	val avgHumidity: Float? = null,

	@field:SerializedName("avg_tds")
	val avgTds: Float? = null
)

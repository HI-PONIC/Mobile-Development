package com.example.hi_ponic.data.response

import com.google.gson.annotations.SerializedName

data class ListTanamanResponse(

	@field:SerializedName("plants")
	val plants: List<PlantsItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class PlantsItem(

	@field:SerializedName("date_added")
	val dateAdded: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

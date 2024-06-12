package com.example.hi_ponic.data.response

import com.google.gson.annotations.SerializedName

data class TambahTanamanResponse(

	@field:SerializedName("plant")
	val plant: Plant? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Plant(

	@field:SerializedName("date_added")
	val dateAdded: String? = null,

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

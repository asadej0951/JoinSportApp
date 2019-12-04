package com.onzon.delivery.user.utils.facebook

data class Facebook(
    val id: String,
    val name: String,
    val email: String?,
    val picture: Picture
) {

    data class Picture(
            val data: Data
    )

    data class Data(
            val height: Int,
            val is_silhouette: Boolean,
            val url: String,
            val width: Int
    )
}
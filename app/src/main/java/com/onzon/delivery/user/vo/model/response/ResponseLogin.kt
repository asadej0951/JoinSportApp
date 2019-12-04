package com.onzon.delivery.user.vo.model.response

data class ResponseLogin(
    val `data`: DataLogin
)

data class DataLogin(
    val accessToken: String,
    val email: String,
    val fullname: String,
    val id: Int,
    val language: String,
    val notification: Boolean,
    val role: String,
    val shops: Shops
)

data class Shops(
    val address: String,
    val addressDescription: Any,
    val categories: Categories,
    val categoryId: Int,
    val closedAt: String,
    val coverImage: String,
    val description: String,
    val id: Int,
    val images: List<Any>,
    val latitude: String,
    val longitude: String,
    val name: String,
    val openedAt: String,
    val ownerName: String,
    val reviews: List<Any>,
    val services: List<Service>,
    val tel: String
)

data class Categories(
    val color: Color,
    val id: Int,
    val name: String
)

data class Color(
    val main: String,
    val secondary: String
)

data class Service(
    val id: Int,
    val name: String,
    val price: Int
)
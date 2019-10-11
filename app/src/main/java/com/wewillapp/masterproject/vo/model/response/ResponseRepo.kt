package com.wewillapp.masterproject.vo.model.response


data class ResponseRepo(
    val `data`: List<DataCategory>
)

data class DataCategory(
    val color: DataColorCategory,
    val id: Int,
    val name: String
)

data class DataColorCategory(
    val main: String,
    val secondary: String
)
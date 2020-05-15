package com.wewillapp.masterproject.vo.model.response

data class ResponseOrderList(
    val `data`: List<DataOrderList>,
    val links: LinksOrderListdata,
    val meta: MetOrderListdataa
)

data class LinksOrderListdata(
    val first: String,
    val last: String,
    val next: String?,
    val prev: Any
)

data class DataOrderList(
    val bookingAt: String,
    val id: Int,
    val bookingNO:String,
    val latitude: String,
    val longitude: String,
    val note: String,
    val paymentSlip: Any,
    val paymentStatus: String,
    val paymentType: String? = "",
    val services: List<ServiceOrderList>,
    val users: UserOrder? = null,
    val shippingAddress: String,
    val shopId: Int,
    val staffId: Any,
    val staffs: Any,
    val status: String,
    val serviceLabel:String,
    val bookingUsername:String,
    val totalPrice: Int,
    val userId: Int)

data class UserOrder(var profileImage:String? = "")

data class ServiceOrderList(
    val id: Int,
    val name: String,
    val price: Int
)

data class MetOrderListdataa(
    val current_page: Int,
    val from: Int,
    val last_page: Int,
    val path: String,
    val per_page: Int,
    val to: Int,
    val total: Int
)


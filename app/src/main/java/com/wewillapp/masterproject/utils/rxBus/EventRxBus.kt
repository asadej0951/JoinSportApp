package com.wewillapp.masterproject.utils.rxBus

object EventRxBus {

    fun <RequestType> onAddEventRxBus(rxClass: RequestType) {
        RxBus.publish(rxClass)
    }
}

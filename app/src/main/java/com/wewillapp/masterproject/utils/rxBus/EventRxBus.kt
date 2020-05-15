package com.wewillapp.masterproject.utils.rxBus

import com.wewillapp.masterproject.vo.RxEvent


object EventRxBus {

    fun onSubScritp() {
        var disposable = RxBus.listen(RxEvent::class.java).subscribe {
            print("event rxBus")
        }

//        override fun onDestroy() {
//            super.onDestroy()
//            disposable?.let {
//                if (!disposable.isDisposed) disposable.dispose()
//            }
//        }
    }

    fun <RequestType> onAddEventRxBus(rxClass: RequestType) {
        RxBus.publish(rxClass)
    }

}
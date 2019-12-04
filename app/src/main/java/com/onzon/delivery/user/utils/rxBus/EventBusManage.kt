package com.onzon.delivery.user.utils.rxBus

import com.onzon.delivery.user.utils.MyLog
import io.reactivex.disposables.Disposable

object EventBusManage {

    fun onSubScritp(){
        var personDisposable: Disposable = RxBus.listen(RxEvent::class.java)
            .subscribe {
            MyLog.i(it.event)
        }

//        override fun onDestroy() {
//            super.onDestroy()
//            if (!personDisposable.isDisposed) personDisposable.dispose()
//        }
    }


    fun onAddEventRxBus(param:String){
        RxBus.publish(
            RxEvent(
                param
            )
        )
    }
}
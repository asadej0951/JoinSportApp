package com.wewillapp.masterproject.utils.rxBus


import com.wewillapp.masterproject.utils.MyLog
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
package com.wewillapp.masterproject.data.rest

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.wewillapp.masterproject.data.rest.ApiResponse.onErrorResponseServer
import com.wewillapp.masterproject.vo.Resource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class NetworkBoundResource<RequestType>() {
    private val result = MediatorLiveData<Resource<RequestType>>()

    init {
        setValue(Resource.loading(null))
        fetchFromNetwork()
    }

    private fun setValue(newValue: Resource<RequestType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    @SuppressLint("CheckResult")
    private fun fetchFromNetwork() {
        createCall().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<RequestType>() {
                override fun onNext(response: RequestType) {
                    setValue(Resource.success(response))
                }

                override fun onError(response: Throwable) {
                    setValue(Resource.error(onErrorResponseServer(response), null))
                }

                override fun onComplete() {
                }
            })
    }


fun asLiveData() = result as LiveData<Resource<RequestType>>

protected abstract fun createCall(): Observable<RequestType>

abstract fun saveCallResult(item: String)
}

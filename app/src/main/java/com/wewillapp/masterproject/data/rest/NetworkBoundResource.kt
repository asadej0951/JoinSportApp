package com.wewillapp.masterproject.data.rest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.wewillapp.masterproject.vo.Resource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.observers.DisposableObserver
import java.util.concurrent.TimeUnit


abstract class NetworkBoundResource<RequestType>(val type:String = "default") {
    private val result = MediatorLiveData<Resource<RequestType>>()

    @NonNull
    var disposable: Disposable? = null

    init {
        setValue(Resource.loading(null))
        fetchFromNetwork()
    }


    private fun setValue(newValue: Resource<RequestType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        when (type){
            "default" -> {
                disposable = apiResponse
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<RequestType>() {
                        override fun onNext(response: RequestType) {
                            setValue(Resource.success(response))
                        }

                        override fun onError(response: Throwable) {
                            setValue(Resource.error(response.message!!, null))
                        }

                        override fun onComplete() {
                        }
                    })
            }
            "delay" -> {
                disposable = apiResponse
                    .subscribeOn(Schedulers.io())
                    .delay(0, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<RequestType>() {
                        override fun onNext(response: RequestType) {
                            setValue(Resource.success(response))
                        }

                        override fun onError(response: Throwable) {
                            setValue(Resource.error(response.message!!, null))
                        }

                        override fun onComplete() {
                        }
                    })
            }
            "debounce" -> {
                disposable = apiResponse
                    .subscribeOn(Schedulers.io())
                    .debounce(0, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<RequestType>() {
                        override fun onNext(response: RequestType) {
                            setValue(Resource.success(response))
                        }

                        override fun onError(response: Throwable) {
                            setValue(Resource.error(response.message!!, null))
                        }

                        override fun onComplete() {
                        }
                    })
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<RequestType>>

    protected abstract fun createCall(): Observable<RequestType>

    abstract fun saveCallResult(item: String)


}




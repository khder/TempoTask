package com.tempo.task.data

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class NewsRepository {
    fun getNews(newsLiveData:MutableLiveData<Resource>,
                        searchPhase:String,apiKey:String,page:Int,pageSize:Int){
        val disposable = ApiClient.getClient()?.create(ApiService::class.java)
            ?.getNews(searchPhase,apiKey, page, pageSize)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableSingleObserver<Resource>() {
                override fun onSuccess(resource: Resource) {
                    newsLiveData.value = resource
                }

                override fun onError(e: Throwable) {
                    newsLiveData.value = Resource.error("Unable to get News Try Again")
                }
            })
    }
}
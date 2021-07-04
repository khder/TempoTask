package com.tempo.task.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tempo.task.data.NewsRepository
import com.tempo.task.data.Resource

class NewsViewModel(private val newsRepository: NewsRepository,
private val apiKey:String) :ViewModel() {
    private var page = 1;
    private val pageSize = 20;
    private val newsLiveData: MutableLiveData<Resource> = MutableLiveData()
    private var searchPhase="";
    private var isLoading:Boolean = false
    fun loadNews(){
        newsRepository.getNews(newsLiveData,searchPhase,apiKey,page, pageSize)
    }
    fun getNewsLiveData():MutableLiveData<Resource>{
        return newsLiveData
    }
    fun increasePages(){
        page++;
    }
    fun setSearch(searchPhase:String){
        page = 1
        this.searchPhase = searchPhase
    }
    fun isFirstPage():Boolean{
        return page==1
    }
    fun isLoading():Boolean{
        return isLoading
    }
    fun setIsLoading(isLoading:Boolean){
        this.isLoading = isLoading
    }
}
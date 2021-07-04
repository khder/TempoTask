package com.tempo.task.di

import com.tempo.task.data.NewsRepository

object NewsRepositoryProvider {
    fun getNewsRepository():NewsRepository{
        return NewsRepository()
    }
}
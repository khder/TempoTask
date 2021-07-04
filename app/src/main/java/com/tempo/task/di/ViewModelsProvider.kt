package com.tempo.task.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.tempo.task.R
import com.tempo.task.ui.ViewModelFactory
import com.tempo.task.ui.news.NewsFragment
import com.tempo.task.ui.news.NewsViewModel

object ViewModelsProvider {
    fun getNewsViewModel(fragment:Fragment,context: Context):NewsViewModel{
        val newsRepository = NewsRepositoryProvider.getNewsRepository()
        return ViewModelProvider(fragment,
            ViewModelFactory(NewsViewModel(newsRepository,context.getString(R.string.news_api_key))))
            .get(NewsViewModel::class.java)
    }
}
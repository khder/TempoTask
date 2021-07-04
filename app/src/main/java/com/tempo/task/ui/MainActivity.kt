package com.tempo.task.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tempo.task.R
import com.tempo.task.data.News
import com.tempo.task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController:NavController
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nav = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = nav.navController
        binding.backArrow.setOnClickListener {
            it.visibility = GONE
            navigateToNews()
        }
    }
    fun navigateToNewsDetails(news: News){
        val bundle = bundleOf(getString(R.string.news_details_key) to news)
        navController.navigate(R.id.action_newsFragment_to_newsDetailsFragment, bundle)
        binding.backArrow.visibility = VISIBLE
        binding.title.text = getString(R.string.news_details)
    }
    private fun navigateToNews(){
        navController.navigate(R.id.newsFragment)
    }
}
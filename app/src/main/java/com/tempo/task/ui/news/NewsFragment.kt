package com.tempo.task.ui.news

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tempo.task.R
import com.tempo.task.data.News
import com.tempo.task.databinding.FragmentNewsBinding
import com.tempo.task.di.ViewModelsProvider
import com.tempo.task.ui.MainActivity
import com.tempo.task.utils.NetworkUtils


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {
    private lateinit var binding:FragmentNewsBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPaginationListener()
        binding.searchEditText.setOnEditorActionListener { p0, actionId, p2 ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(binding.searchEditText.text.toString().isNotEmpty()) {
                    newsViewModel.setSearch(binding.searchEditText.text.toString())
                    hideKeyboard(requireActivity())
                    loadNews()
                    return@setOnEditorActionListener true
                }
            }
            return@setOnEditorActionListener false
        }
        newsAdapter = NewsAdapter(object : NewsAdapter.OnItemCLickListener{
            override fun onCLick(news: News) {
                (requireActivity() as MainActivity).navigateToNewsDetails(news)
            }
        })
        binding.newsListView.adapter = newsAdapter
        newsViewModel = ViewModelsProvider.getNewsViewModel(this,requireContext())
        newsViewModel.getNewsLiveData().observe(viewLifecycleOwner, Observer {resource->
            if(resource.status == resource.STATUS_OK){
                if(newsViewModel.isFirstPage()) {
                    binding.progressBar.visibility = GONE
                    binding.newsListView.visibility = VISIBLE
                }else{
                    newsAdapter.removePagination()
                }
                newsViewModel.setIsLoading(false)
                newsAdapter.updateNewsList(resource.articles)
                newsViewModel.increasePages()
            }
        })
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun loadNews(){
        if(NetworkUtils.isNetworkAvailable(requireContext())) {
            if (newsViewModel.isFirstPage()) {
                binding.newsListView.visibility = GONE
                binding.progressBar.visibility = VISIBLE
            } else {
                newsAdapter.addPagination()
            }
            newsViewModel.loadNews()
        }else{
            Toast.makeText(requireContext(), R.string.check_network_connectivity,Toast.LENGTH_LONG).show()
        }
    }
    private fun setupPaginationListener(){
        binding.newsListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(!newsViewModel.isLoading()) {
                    if (dy > 0) //check for scroll down
                    {
                        val visibleItemCount = recyclerView.layoutManager?.childCount!!;
                        val totalItemCount = recyclerView.layoutManager?.itemCount!!;
                        val pastVisiblesItems = (recyclerView.layoutManager as LinearLayoutManager)
                            .findFirstVisibleItemPosition()
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            newsViewModel.setIsLoading(true)
                            loadNews()
                        }
                    }
                }
            }
        })
    }
}
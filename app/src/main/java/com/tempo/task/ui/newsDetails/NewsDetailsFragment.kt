package com.tempo.task.ui.newsDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.tempo.task.R
import com.tempo.task.data.News
import com.tempo.task.databinding.FragmentNewsDetailsBinding


/**
 * A simple [Fragment] subclass.
 * Use the [NewsDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsDetailsFragment : Fragment() {
    private lateinit var news:News
    private lateinit var binding:FragmentNewsDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            news = it.getParcelable(getString(R.string.news_details_key))!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(news.getImage()).into(binding.newsImage)
        binding.newsTitle.text = news.getTitle()
        binding.newsDescription.text = news.getDescription()
        binding.newsContent.text = news.getContent()
        binding.newsSource.text = news.getSource()["name"]
        binding.newsAuthor.text = getString(R.string.author,news.getAuthor())
        binding.newsDate.text = news.getPublishedAt()
        binding.sourceButton.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(news.getUrl())
            startActivity(i)
        }
    }
}
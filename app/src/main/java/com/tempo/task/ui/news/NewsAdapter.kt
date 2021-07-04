package com.tempo.task.ui.news

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tempo.task.R
import com.tempo.task.data.News
import com.tempo.task.databinding.NewsListRowBinding


class NewsAdapter(private val onItemClickListener: OnItemCLickListener):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val ITEM_ROW = 1;
    private val PROGRESS_ROW = 2;
    private val PAGINATION = "pagination"
    private var news:ArrayList<News> = ArrayList()
    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==ITEM_ROW){
            NewsHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.news_list_row, parent, false))
        }else{
            ProgressHolder( LayoutInflater.from(parent.context)
                .inflate(R.layout.progress_row, parent, false))
        }
//        return NewsHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.news_list_row, parent, false)
//        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position)==ITEM_ROW){
            (holder as NewsHolder).bindData(news[position])
            holder.itemView.setOnClickListener {
                onItemClickListener.onCLick(news[holder.adapterPosition])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == news.size - 1 && isLoadingAdded) PROGRESS_ROW else ITEM_ROW
    }

    fun removePagination(){
        isLoadingAdded = false
        val position: Int = news.size - 1
        news.removeAt(position)
        notifyItemRemoved(position)
    }
    fun addPagination(){
        isLoadingAdded = true
        val paginationNews = News()
        paginationNews.setDescription(PAGINATION)
        news.add(paginationNews)
        notifyItemInserted(news.size-1)
    }

    override fun getItemCount(): Int {
        return news.size
    }
    fun updateNewsList(news: List<News>){
        val oldSize = news.size
        this.news.addAll(news)
        notifyItemRangeInserted(oldSize,this.news.size)
    }
    class NewsHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding:NewsListRowBinding = NewsListRowBinding.bind(v)
        fun bindData(news:News){
            binding.newsSource.text = news.getSource()["name"]
            binding.newsText.text = news.getTitle()
            Picasso.get().load(news.getImage()).into(binding.newsImage)
        }
    }
    class ProgressHolder(v:View):RecyclerView.ViewHolder(v)

    interface OnItemCLickListener{
        fun onCLick(news: News)
    }
}
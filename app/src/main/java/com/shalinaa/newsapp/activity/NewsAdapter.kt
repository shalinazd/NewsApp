package com.shalinaa.newsapp.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shalinaa.newsapp.R
import com.shalinaa.newsapp.model.ArticlesItem
import com.shalinaa.newsapp.model.ResponseNews
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter (var context: Context, var listNews: List<ArticlesItem?>?) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var onItemClickCallBack : OnItemClickCallBack? = null

    fun setItemOnClickCallBack (onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClicked(newsData : ArticlesItem)
    }

    inner class ViewHolder (view : View) :RecyclerView.ViewHolder(view){
        fun bind (news: ArticlesItem){
            with(itemView){
                tv_title_item.text = news.title
                tv_date_item.text = news.publishedAt
                tv_duration_item.text = news.author
                Glide.with(context).load(news.urlToImage).centerCrop().into(iv_item_news)
                itemView.setOnClickListener{
                    onItemClickCallBack?.onItemClicked(news)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listNews!!.size

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bind(listNews?.get(position)!!)
        holder.itemView.setOnClickListener{
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_NEWS, listNews!![position])
        }
    }

}
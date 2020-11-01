package com.myproject.rakeshyadavclassnotes.views.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myproject.rakeshyadavclassnotes.R
import com.myproject.rakeshyadavclassnotes.model.ChapterModel
import com.myproject.rakeshyadavclassnotes.model.ChapterTopicModel
import com.myproject.rakeshyadavclassnotes.urls.urls
import com.myproject.rakeshyadavclassnotes.views.activities.VideoActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_chapter_list.*
import kotlinx.android.synthetic.main.row_chapter_topic_list.*

class AdapterChapterTopicList(var mActivity:Activity,var chapterTopicModel:ArrayList<ChapterTopicModel>,val onClick:(pos: ChapterTopicModel)->Unit):RecyclerView.Adapter<AdapterChapterTopicList.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View= LayoutInflater.from(parent.context).inflate(R.layout.row_chapter_topic_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chapterTopicModel.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(chapterTopicModel[position])

    }

    inner class ViewHolder (item: View):RecyclerView.ViewHolder(item), LayoutContainer,View.OnClickListener{
        override val containerView: View?
            get() = itemView

        init{
            btn_PDF.setOnClickListener(this)
            btn_Video.setOnClickListener(this)
        }
        fun bindView(model: ChapterTopicModel)
        {
            tvTopicTitle.text=model.title

            val thumbUrl = urls.IMAGE_URL+model.image
            Glide
                .with(mActivity)
                .load(thumbUrl)
                .error(R.drawable.ic_library_books)
                .placeholder(R.drawable.ic_library_books)
                .into(ivIcon)

        }

        override fun onClick(v: View?) {
            when(v?.id)
            {
                R.id.btn_PDF->{
                    val model=chapterTopicModel[adapterPosition]
                    onClick(model)
                }
                R.id.btn_Video->{
                    val intent= Intent(mActivity,VideoActivity::class.java)
                    mActivity.startActivity(intent)
                }
            }

        }
    }
}
package com.myproject.rakeshyadavclassnotes.views.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myproject.rakeshyadavclassnotes.R
import com.myproject.rakeshyadavclassnotes.model.ChapterModel
import com.myproject.rakeshyadavclassnotes.urls.urls
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_chapter_list.*
import okhttp3.internal.Util

class AdapterChapterList(val mActivity: Activity,var chapterModel:List<ChapterModel>,val onClick:(pos:ChapterModel)->Unit):RecyclerView.Adapter<AdapterChapterList.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View= LayoutInflater.from(parent.context).inflate(R.layout.row_chapter_list,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
            return chapterModel.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(chapterModel[position])
    }

    inner class ViewHolder (item: View):RecyclerView.ViewHolder(item),LayoutContainer,View.OnClickListener{
        override val containerView: View?
            get() = itemView

        init{
            ll_raw_main.setOnClickListener(this)
        }
        fun bindView(model:ChapterModel)
        {
            tvChapterName.text="("+model.iq_category_name+")"

            val thumbUrl = urls.IMAGE_URL+model.iq_category_icon
            Glide
                .with(mActivity)
                .load(thumbUrl)
                .error(R.drawable.ic_library_books)
                .placeholder(R.drawable.ic_library_books)
                .into(ivChapter)

        }

        override fun onClick(v: View?) {
            when(v?.id)
            {
                R.id.ll_raw_main->{
                    val model=chapterModel[adapterPosition]
                    onClick(model)
                }
            }

        }
    }
}
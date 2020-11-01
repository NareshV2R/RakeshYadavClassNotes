package com.myproject.rakeshyadavclassnotes.model

import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray
import java.lang.Exception

@Parcelize
data class ChapterTopicModel(var sub_category_id:String,var category_id:String,var title:String,var image:String,var pdf:String,var quiz_time:String,
var category_order:String,var pq_order:String,var iq_order:String,var video_order:String,var status:String,var cat_slug:String,var created_at:String,
var weapon_title:String,var weapon_status:String,var weapon_icon:String,var new_question_count:Int,var video_count:Int,var new_video_count:Int):Parcelable {
    companion object
    {
        fun getParseChapterTopic(jsonArray:JSONArray?):ArrayList<ChapterTopicModel>
        {
            val chapterTopicModel=ArrayList<ChapterTopicModel>()
            try {
                repeat(jsonArray?.length()?:0)
                {
                    jsonArray!!.getJSONObject(it).run {
                        ChapterTopicModel(
                            getString("sub_category_id"), getString("category_id"), getString("title"), getString("image"),
                            getString("pdf"), getString("quiz_time"), getString("category_order"), getString("pq_order"),
                            getString("iq_order"), getString("video_order"), getString("status"), getString("cat_slug"),
                            getString("created_at"), getString("weapon_title"), getString("weapon_status"), getString("weapon_icon"),
                            getInt("new_question_count"), getInt("video_count"), getInt("new_video_count")
                        ).also {
                            chapterTopicModel.add(it)
                        }
                    }
                }


            }catch (e:Exception)
            {
                Log.v("ChapterTopicModel", "Exc $e")

            }
            return chapterTopicModel
        }
    }

}
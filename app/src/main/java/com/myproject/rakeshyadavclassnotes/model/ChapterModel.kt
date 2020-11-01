package com.myproject.rakeshyadavclassnotes.model

import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

@Parcelize
data class ChapterModel (var iq_category_id:String, var app_category_id:String, var iq_category_name:String, var iq_category_icon:String): Parcelable
{
    companion object
    {
        fun getParseChapterModel(jsonArray: JSONArray?): ArrayList<ChapterModel>?{
            val chapterModels = ArrayList<ChapterModel>()
            try {
                repeat(jsonArray?.length() ?: 0) {
                    jsonArray!!.getJSONObject(it).run {
                        ChapterModel(getString("iq_category_id"), getString("app_category_id"), getString("iq_category_name"), getString("iq_category_icon")).also {
                            chapterModels.add(it)
                        }
                    }
                }
            }catch (e: JSONException){
                Log.v("ChapterModel", "Exc $e")
            }
            return chapterModels
        }
       /* fun getParseChapterModel(apiResponse: JSONObject?): List<ChapterModel>? {
            val chapterModels = ArrayList<ChapterModel>()
            try {
                apiResponse?.getJSONArray("message")?.let {
                    val list = ArrayList<ChapterModel>()
                    repeat(it.length())
                    { pos ->
                        val obj = it.getJSONObject(pos)
                        obj.run {
                            ChapterModel(getString("iq_category_id"), getString("app_category_id"), getString("iq_category_name"), getString("iq_category_icon")).also {
                                chapterModels.add(it)
                            }
                        }
                    }
                    return list
                }

            } catch (e: Exception) {
                Log.v("ProductList", "exe$e")
            }
            return null
        }*/
    }

}
package com.myproject.rakeshyadavclassnotes.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.error.ANError
import com.myproject.rakeshyadavclassnotes.model.ChapterModel
import com.myproject.rakeshyadavclassnotes.model.ChapterTopicModel
import com.myproject.rakeshyadavclassnotes.networking.FastNetworkingCalls
import com.myproject.rakeshyadavclassnotes.urls.urls
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject

class ChapterRepo( private val compositeDisposable: CompositeDisposable)
{
    fun chaptersListObservable(appCatId: Int): LiveData<List<ChapterModel>?> {
        val data: MutableLiveData<List<ChapterModel>?> = MutableLiveData()

        val map = HashMap<String, Any?>()
        map["app_category_id"] = appCatId

        val url=urls.CHAPTER_LIST+"?app_category_id="+appCatId
        Log.v("ChaptersList", "url ${url}")

        FastNetworkingCalls.makeRxCallObservable(FastNetworkingCalls.getRxCallGetObservable(url,"ChapterList", map),
            compositeDisposable, "ChaptersList", object : FastNetworkingCalls.OnApiResult{
                override fun onApiSuccess(response: JSONObject?) {
                    Log.v("ChaptersList", "onSuccess $response")

                    try{
                        if (response != null && response.has("message"))
                        {
                            if( response.optInt("response") == 1)
                            {
                                val list=ChapterModel.getParseChapterModel(response.getJSONArray("message"))
                                Log.v("ChaptersList", "parsing ${list}")
                                data.postValue(list)
                            }
                            else
                            {
                                Log.v("ChaptersList", "resp=0")
                                data.postValue(null)
                            }
                        }
                        else
                        { Log.v("ChaptersList", "resp=null")

                            data.postValue(null)}
                    }catch (e:Exception)
                    {
                        Log.v("ChaptersList", "resp${e}")
                        data.postValue(null)

                    }

                }
                override fun onApiError(anError: ANError) {
                    Log.v("ChaptersList", "onError ${anError.errorBody}")
                    data.postValue(null)
                }

            })

        return data
    }

    fun chaptersTopicListObservable(CatId: String): LiveData<List<ChapterTopicModel>> {
        val data: MutableLiveData<List<ChapterTopicModel>> = MutableLiveData()

        val map = HashMap<String, Any?>()
        map["category_id"] = CatId

        val url=urls.CHAPTER_TOPPIC_LIST+"?category_id="+CatId
        Log.v("ChaptersTopicList", "url ${url}")


        FastNetworkingCalls.makeRxCallObservable(FastNetworkingCalls.getRxCallGetObservable(url,"ChaptersTopicList", map),
            compositeDisposable, "ChaptersTopicList", object : FastNetworkingCalls.OnApiResult{
                override fun onApiSuccess(response: JSONObject?) {
                    Log.v("ChaptersTopicList", "onSuccess $response")
                    if (response != null && response.has("message")) {
                        if( response.optInt("response") == 1) {
                            val list = ChapterTopicModel.getParseChapterTopic(response.getJSONArray("message"))
                            Log.v("ChaptersTopicList", "parsing ${list}")
                            data.postValue(list)
                        }else data.postValue(null)

                    }
                    else
                        data.postValue(null)
                }

                override fun onApiError(anError: ANError) {
                    Log.v("ChaptersTopicList", "onError ${anError.errorBody}")
                    data.postValue(null)
                }

            })

        return data
    }

}
package com.myproject.rakeshyadavclassnotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.myproject.rakeshyadavclassnotes.model.ChapterModel
import com.myproject.rakeshyadavclassnotes.model.ChapterTopicModel
import com.myproject.rakeshyadavclassnotes.repos.ChapterRepo
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject

class ChapterListVM:ViewModel()
{
    private  val compositeDisposable by lazy { CompositeDisposable() }

    private val chaptersRepo by lazy { ChapterRepo(compositeDisposable) }


        fun getChaptersObsarvable(appCatId:Int): LiveData<List<ChapterModel>?>
        {
            return chaptersRepo.chaptersListObservable(appCatId)
        }

        fun getChaptersTopicListObservable(CatId:String): LiveData<List<ChapterTopicModel>>
        {
            return chaptersRepo.chaptersTopicListObservable(CatId)
        }

   /* override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }*/
}
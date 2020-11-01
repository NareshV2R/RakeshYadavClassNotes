package com.myproject.rakeshyadavclassnotes.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.rakeshyadavclassnotes.R
import com.myproject.rakeshyadavclassnotes.model.ChapterModel
import com.myproject.rakeshyadavclassnotes.viewmodel.ChapterListVM
import com.myproject.rakeshyadavclassnotes.views.adapter.AdapterChapterList
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private  var  adapterChapterList:AdapterChapterList?=null
    private var chapterModelList:List<ChapterModel> = ArrayList()
    private  lateinit var viewModel:ChapterListVM
    private var arrayModel=ArrayList<ChapterModel>()
    /*companion object{

        val list = ArrayList<ChapterModel>()

    }*/
    companion object
    {
        private const val TAG="MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*list.add(ChapterModel("Chapter1","advance",""))
        list.add(ChapterModel("Chapter1","advance",""))
        list.add(ChapterModel("Chapter1","advance",""))
        list.add(ChapterModel("Chapter1","advance",""))*/

        viewModel= ViewModelProvider(this).get(ChapterListVM::class.java)

        /*adapterChapterList= AdapterChapterList(this, chapterModelList)
        rvChapterList.layoutManager=LinearLayoutManager(this)
        rvChapterList.adapter=adapterChapterList
*/
        rvChapterList.layoutManager=LinearLayoutManager(this)

        progress.visibility=VISIBLE
        viewModel.getChaptersObsarvable(1).observe(this, Observer {
            progress.visibility= GONE

            Log.v(TAG,"getLISTObservable ${it}")
            try {
                if (it != null) {
                    arrayModel.addAll(it)
                } else {
                    Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
                }

                adapterChapterList = AdapterChapterList(this, arrayModel) {
                    if (!it.iq_category_id.isNullOrEmpty()) {
                        val intent = Intent(this, ChapterActivity::class.java)
                        intent.putExtra("Cat_id", it.iq_category_id)
                        intent.putExtra("Cat_Name", it.iq_category_name)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()

                    }
                }
                rvChapterList.adapter = adapterChapterList
            }
            catch (e: Exception)
            {
                Log.e("Exe","$e")
            }

        })
    }
}

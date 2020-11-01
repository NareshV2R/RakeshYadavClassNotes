package com.myproject.rakeshyadavclassnotes.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.rakeshyadavclassnotes.R
import com.myproject.rakeshyadavclassnotes.model.ChapterTopicModel
import com.myproject.rakeshyadavclassnotes.utils.isNetConnected
import com.myproject.rakeshyadavclassnotes.viewmodel.ChapterListVM
import com.myproject.rakeshyadavclassnotes.views.adapter.AdapterChapterTopicList
import kotlinx.android.synthetic.main.activity_chapter.*

class ChapterActivity : AppCompatActivity() {

    private  var  adapterChapterTopicList: AdapterChapterTopicList?=null
    private var chapterTopicModelList:List<ChapterTopicModel> = ArrayList()
    private  lateinit var viewModel: ChapterListVM
    private var arrayModel=ArrayList<ChapterTopicModel>()

    companion object
    {
        private const val TAG="ChapterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter)

        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        var title=intent.getStringExtra("Cat_Name")
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = title

        progressBar.visibility= View.VISIBLE

        viewModel= ViewModelProvider(this).get(ChapterListVM::class.java)
        rvChapterTopicList.layoutManager= LinearLayoutManager(this)
        val id=intent.getStringExtra("Cat_id")

        if(this.isNetConnected())
        {
            viewModel.getChaptersTopicListObservable(id).observe(this, Observer {
                progressBar.visibility= View.GONE

                Log.v(TAG,"getTopicLISTObservable ${it}")
                if (it != null) {

                    arrayModel.addAll(it)
                }
                else
                {
                    nodata.visibility= View.VISIBLE
                    Toast.makeText(this,"Data not found",Toast.LENGTH_SHORT).show()
                }

                adapterChapterTopicList = AdapterChapterTopicList(this, arrayModel)
                {
                    if(!it.pdf.isNullOrEmpty())
                    {
                        val intent= Intent(this,PDFActivity::class.java)
                        intent.putExtra("pdf",it.pdf)
                        intent.putExtra("pdfName",it.title)
                        intent.putExtra("pdfId",it.sub_category_id)
                        startActivity(intent)
                    }
                }

                rvChapterTopicList.adapter = adapterChapterTopicList
            })

        }
        else
        {
            Toast.makeText(this, "Please Check Internet Connection", Toast.LENGTH_LONG).show()

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when(item.itemId)
        {
            android.R.id.home-> {
                onBackPressed()
                true
            }
            else-> return super.onOptionsItemSelected(item)

        }
    }
}

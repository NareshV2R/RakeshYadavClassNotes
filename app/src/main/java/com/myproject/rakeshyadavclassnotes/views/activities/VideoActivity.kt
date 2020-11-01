package com.myproject.rakeshyadavclassnotes.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.myproject.rakeshyadavclassnotes.R

class VideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val toolbar = findViewById(R.id.toolbarVideo) as Toolbar?
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
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
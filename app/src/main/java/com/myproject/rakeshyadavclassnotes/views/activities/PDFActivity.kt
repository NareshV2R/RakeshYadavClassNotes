package com.myproject.rakeshyadavclassnotes.views.activities

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.myproject.rakeshyadavclassnotes.R
import com.myproject.rakeshyadavclassnotes.urls.urls
import com.myproject.rakeshyadavclassnotes.utils.isNetConnected
import kotlinx.android.synthetic.main.activity_p_d_f.*
import java.io.File


@Suppress("DEPRECATION")
class PDFActivity : AppCompatActivity() {

    var TAG:String="PDFActivity"
    private var pdfFile:String=""
    private var pdfFileName:String=""
    private var pdfFileId:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p_d_f)

        isReadStoragePermissionGranted()
        isWriteStoragePermissionGranted()

        iv_back.setOnClickListener{
            super.onBackPressed()
        }

        iv_full.setOnClickListener{
            toolbarPDF.visibility= GONE
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        }
        iv_popupmenu.setOnClickListener{
            val popup = PopupMenu(this@PDFActivity,iv_popupmenu )
            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)

            popup.setOnMenuItemClickListener     {
                if (it.itemId == R.id.one) {
                    Toast.makeText(applicationContext, "One", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "None", Toast.LENGTH_SHORT).show()
                }
                true
            }

            popup.show()//showing popup menu

        }

        pdfFile=intent.getStringExtra("pdf")
        pdfFileName=intent.getStringExtra("pdfName")
        pdfFileId=intent.getStringExtra("pdfId")

       // Toast.makeText(this@PDFActivity, "Please Wait until the file is download", Toast.LENGTH_LONG).show()
        txt.visibility= VISIBLE
        if(this.isNetConnected())
        {
            show()
        }
        else
        {
            Toast.makeText(this, "Please Check Internet Connection", Toast.LENGTH_LONG).show()

        }
    }

    fun isReadStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v(TAG, "Permission is granted1")
                true
            } else {
                Log.v(TAG, "Permission is revoked1")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    3
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1")
            true
        }
    }

    fun isWriteStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v(TAG, "Permission is granted2")
                true
            } else {
                Log.v(TAG, "Permission is revoked2")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    2
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2")
            true
        }
    }

    private fun show() {


            val url = "/storage/emulated/0/Android/data/com.myproject.rakeshyadavclassnotes/files/Download/RakeshYadavNotes/$pdfFileName$pdfFileId.pdf"
            if(!File(url).exists())
            {
               pdfDownload()
                Handler().postDelayed({
                    pdfShow(url)
                },5000)
            }
            else
            {
                pdfShow(url)
            }
    }

    override fun onBackPressed() {
        if(toolbarPDF.visibility== GONE)
        {
            toolbarPDF.visibility= VISIBLE
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        else
        {
             super.onBackPressed()
        }
    }
    private fun pdfShow(url: String) {

        txt.visibility= GONE
        pdfViews.visibility= VISIBLE

        pdfViews.fromFile(File(url))
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
            .password(null)
           // .scrollHandle(null)
            .enableAntialiasing(true) // improve rendering a little bit on low-res screens
            // spacing between pages in dp. To define spacing color, set view background
            .spacing(0)
            .pageFitPolicy(FitPolicy.WIDTH)
            .onPageChange { page, pageCount ->
                var pageInt=page+1
                tv_pageno.text=pageInt.toString()+"/"+pageCount.toString()
            }
            .scrollHandle( DefaultScrollHandle(this))
            .load()
    }

    private fun pdfDownload()
    {

        pdfViews.visibility= GONE

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(urls.PDF_URL+pdfFile)
        val request = DownloadManager.Request(uri)
        //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setAllowedOverRoaming(true)
        request.setTitle("" + pdfFileName)
        //request.setVisibleInDownloadsUi(true)
       // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(this,Environment.DIRECTORY_DOWNLOADS, "/RakeshYadavNotes/$pdfFileName$pdfFileId.pdf")
        downloadManager.enqueue(request)

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
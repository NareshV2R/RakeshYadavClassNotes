package com.myproject.rakeshyadavclassnotes.utils

import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class FileDownloader
{
    private val TAG = "FileDownloader"

    private val MEGABYTE = 1024 * 1024

    fun downloadFile(fileUrl: String, directory: File) {
        try {
            Log.v(TAG, "downloadFile() invoked ")
            Log.v(TAG, "downloadFile() fileUrl $fileUrl")
            Log.v(TAG, "downloadFile() directory $directory")
            val url = URL(fileUrl)
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            urlConnection.connect()
            val inputStream: InputStream = urlConnection.getInputStream()
            val fileOutputStream = FileOutputStream(directory)
            val totalSize: Int = urlConnection.getContentLength()
            val buffer = ByteArray(MEGABYTE)
            var bufferLength = 0
            while (inputStream.read(buffer).also({ bufferLength = it }) > 0) {
                fileOutputStream.write(buffer, 0, bufferLength)
            }
            fileOutputStream.close()
            Log.v(TAG, "downloadFile() completed ")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "downloadFile() error" + e.message)
            Log.e(TAG, "downloadFile() error" + e.getStackTrace())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            Log.e(TAG, "downloadFile() error" + e.message)
            Log.e(TAG, "downloadFile() error" + e.getStackTrace())
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "downloadFile() error" + e.message)
            Log.e(TAG, "downloadFile() error" + e.getStackTrace())
        }
    }
}
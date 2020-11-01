package com.myproject.rakeshyadavclassnotes.utils

import android.os.Environment

class CheckForSDCard
{
    //Check If SD Card is present or not method
    fun isSDCardPresent(): Boolean {
        return if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED
            )
        ) {
            true
        } else false
    }
}
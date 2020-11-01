package com.myproject.rakeshyadavclassnotes.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.myproject.rakeshyadavclassnotes.R

private const val TAG = "ExtFunctions"

fun TextView.setHtmlText(string: String){
    text = HtmlCompat.fromHtml(string.trim(), HtmlCompat.FROM_HTML_MODE_LEGACY)
}

fun View.hide(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.setVisible(keepVisible: Boolean){
    with(this){
        if (keepVisible) show() else hide()
    }
}

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes messageId: Int){
    Toast.makeText(this, getString(messageId), Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.toastLong(@StringRes messageId: Int){
    Toast.makeText(this, getString(messageId), Toast.LENGTH_LONG).show()
}

fun Context.isNetConnected(): Boolean{
    try {
        var result = false
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    }
                    result = true
                }
            }
        } else {
            if (cm != null) {
                val activeNetwork = cm.activeNetworkInfo
                if (activeNetwork != null) {
// connected to the internet
                    if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    } catch (e: Exception) {
        Log.v(TAG, "" + e)
    }

    return false
}

/*fun Context.getCustomToast(message: String, @DrawableRes icon: Int? = null): Toast{
    val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view = inflater.inflate(R.layout.content_custom_toast, null)

    if (icon != null){
        view.ivtoastIcon.setImageResource(icon)
    }else{
        view.ivtoastIcon.hide()
    }
    view.tvtoastMessage.text = message

    val toast = Toast(this)
    toast.view = view
    toast.setGravity(Gravity.BOTTOM, 0, 150)

    return toast
}*/

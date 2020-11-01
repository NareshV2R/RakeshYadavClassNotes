package com.myproject.rakeshyadavclassnotes.utils

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.myproject.rakeshyadavclassnotes.R

@GlideModule
class MyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {

        val memoryCacheSizeBytes = 1024 * 1024 * 20 // 20mb
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, memoryCacheSizeBytes.toLong()))
        builder.setDefaultRequestOptions(requestOptions())
        // builder.build(context)

        super.applyOptions(context, builder)
    }

    private fun requestOptions(): RequestOptions {
        return RequestOptions()
            .signature(
                ObjectKey(
                    System.currentTimeMillis() / (24 * 60 * 60 * 1000)
                )
            )
            .placeholder(R.drawable.ic_library_books)
            .error(R.drawable.ic_library_books)
            .encodeFormat(Bitmap.CompressFormat.PNG)
            .encodeQuality(100)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .format(DecodeFormat.PREFER_RGB_565)   //PREFER_ARGB_8888
            .skipMemoryCache(false)
    }

    companion object{

        fun getSquareRequestOptions(isCenterCrop:Boolean=true): RequestOptions {
            return RequestOptions().also {
                if(isCenterCrop)
                    it.centerCrop()
                else
                    it.fitCenter()
            }
        }

        fun getRectangleRequestOptions(isCenterCrop:Boolean=true): RequestOptions {
            return RequestOptions().also {
                it.placeholder(R.drawable.ic_library_books)
                it.error(R.drawable.ic_library_books)
                if(isCenterCrop)
                    it.centerCrop()
                else
                    it.fitCenter()
            }
        }
    }
}
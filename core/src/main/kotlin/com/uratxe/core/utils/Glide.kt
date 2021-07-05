package com.uratxe.core.utils

import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun RequestManager.noCacheImages() : RequestOptions{
    return RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
}
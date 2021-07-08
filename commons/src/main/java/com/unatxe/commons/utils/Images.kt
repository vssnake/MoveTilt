package com.unatxe.commons.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.io.ByteArrayOutputStream
import java.io.File

fun Drawable.resize(heightDp: Float, widthDp: Float, resources : Resources) : Drawable {

    val bitmap = Bitmap.createScaledBitmap((this as BitmapDrawable).bitmap, heightDp.toInt(), widthDp.toInt(), false)

    val drawable = BitmapDrawable(resources,bitmap)

    return drawable
}

/**
 * Demonstrates converting a [Drawable] to a [BitmapDescriptor],
 * for use as a marker icon.
 */
fun Bitmap.vectorToBitmap(context: Context, @DrawableRes id: Int, @ColorInt color: Int? = null): BitmapDescriptor {
    val vectorDrawable = ResourcesCompat.getDrawable(context.resources, id, null)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable!!.intrinsicWidth,
        vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    color?.let { DrawableCompat.setTint(vectorDrawable, color) }
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun Bitmap.loadBitmapFromView(v: View): Bitmap? {
    val canvas: Canvas
    v.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    v.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val bitmap: Bitmap = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
    canvas = Canvas(bitmap)
    v.layout(0, 0, v.measuredWidth, v.measuredHeight)
    v.draw(canvas)
    return bitmap
}

fun Bitmap.drawableToBitmap(drawable: Drawable): Bitmap? {
    var bitmap: Bitmap? = null
    if (drawable is BitmapDrawable) {
        val bitmapDrawable = drawable
        if (bitmapDrawable.bitmap != null) {
            return bitmapDrawable.bitmap
        }
    }
    bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
        Bitmap.createBitmap(
            1,
            1,
            Bitmap.Config.ARGB_8888
        ) // Single color bitmap will be created of 1x1 pixel
    } else {
        Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
    }
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun Bitmap.calculateInSampleSize(
    options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        val halfHeight = height / 2
        val halfWidth = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}

 fun Bitmap.getResizedBitmap(maxSize: Int, options: BitmapFactory.Options): Int {
    val width = options.outWidth
    val height = options.outHeight


    val bitmapRatio = width.toFloat() / height.toFloat()
    return if (bitmapRatio > 1) {
        width / maxSize
    } else {
        height / maxSize
    }
}

fun Bitmap.decodeSampledBitmapFromResource(res: Resources, resId: Int,
                                               maxSize: Int): Bitmap {

    // First decode with inJustDecodeBounds=true to check dimensions
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(res, resId, options)

    // Calculate inSampleSize
    options.inSampleSize = getResizedBitmap(maxSize, options)

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeResource(res, resId, options)
}

fun Bitmap.decodeSampledBitmapFromFile(file: File,
                                               maxSize: Int): Bitmap {

    // First decode with inJustDecodeBounds=true to check dimensions
    val options = BitmapFactory.Options()
    options.inPreferredConfig = Bitmap.Config.ARGB_8888
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(file.absolutePath, options)

    // Calculate inSampleSize
    options.inSampleSize = getResizedBitmap(maxSize, options)

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false

    return BitmapFactory.decodeFile(file.absolutePath, options)
}

fun Bitmap.rotateBitMap(file : File) : Bitmap{
    val exif = ExifInterface(file.absolutePath)
    val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
    val matrix = Matrix()
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        else -> {
        }
    }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.getEncoded64ImageStringFromBitmap(): String {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 70, stream)
    val byteFormat = stream.toByteArray()
    // get the base 64 string

    return Base64.encodeToString(byteFormat, Base64.NO_WRAP)
}

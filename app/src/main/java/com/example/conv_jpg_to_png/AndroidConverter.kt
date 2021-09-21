package com.example.conv_jpg_to_png

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.conv_jpg_to_png.model.IConverter
import com.example.conv_jpg_to_png.model.Image
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

class AndroidConverter(val context: Context?) : IConverter {

    override fun convert(image: Image) = Completable.fromAction {

        context?.let { context ->
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                return@let
            }

            val bitmap = BitmapFactory.decodeByteArray(image.data, 0, image.data.size)

            val compressedBitmap = File(context.getExternalFilesDir(null), "convertedImage.png")
            FileOutputStream(compressedBitmap).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
        }

    }.subscribeOn(Schedulers.io())

}
package com.example.conv_jpg_to_png.model

import io.reactivex.rxjava3.core.Completable

interface IConverter {
    fun convert(image: Image): Completable
}
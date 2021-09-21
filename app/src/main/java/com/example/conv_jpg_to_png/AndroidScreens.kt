package com.example.conv_jpg_to_png

import ru.terrakok.cicerone.android.support.SupportAppScreen

object AndroidScreens {

    class ImagesScreen : SupportAppScreen() {

        override fun getFragment() = ImageFragment.newInstance()
    }

}
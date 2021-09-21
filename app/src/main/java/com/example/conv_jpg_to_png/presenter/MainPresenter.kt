package com.example.conv_jpg_to_png.presenter

import com.example.conv_jpg_to_png.AndroidScreens
import com.example.conv_jpg_to_png.MainView
import moxy.MvpPresenter

import ru.terrakok.cicerone.Router

class MainPresenter(val router: Router) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(AndroidScreens.ImagesScreen())
    }

    fun backClicked() {
        router.exit()
    }

}


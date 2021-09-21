package com.example.conv_jpg_to_png.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@AddToEndSingle
interface ImageView : MvpView {
    fun init()

    @OneExecution
    fun pickImage()

    fun showConvertInProgress()
    fun hideConvertInProgress()

    @OneExecution
    fun showConvertSuccess()
    @OneExecution
    fun showConvertCancel()
    @OneExecution
    fun showConvertError()
}

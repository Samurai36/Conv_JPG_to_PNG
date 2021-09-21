package com.example.conv_jpg_to_png.presenter

import com.example.conv_jpg_to_png.model.IConverter
import com.example.conv_jpg_to_png.model.Image
import com.example.conv_jpg_to_png.view.ImageView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class ImagePresenter(val schedulerMainThread: Scheduler, val router: Router, val converter: IConverter) : MvpPresenter<ImageView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun convertClick() {
        viewState.pickImage()
    }

    var conversionDisposable: Disposable? = null
    fun imageSelected(image: Image) {
        viewState.showConvertInProgress()
        conversionDisposable = converter.convert(image)
            .observeOn(schedulerMainThread)
            .subscribe({
                viewState.hideConvertInProgress()
                viewState.showConvertSuccess()
            },{
                viewState.hideConvertInProgress()
                viewState.showConvertError()
            })
    }

    fun convertCancel(){
        conversionDisposable?.dispose()
        viewState.hideConvertInProgress()
        viewState.showConvertCancel()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}

package com.example.conv_jpg_to_png

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.conv_jpg_to_png.databinding.FragmentImageBinding
import com.example.conv_jpg_to_png.model.Image
import com.example.conv_jpg_to_png.presenter.ImagePresenter
import com.example.conv_jpg_to_png.view.ImageView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_image.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ImageFragment : MvpAppCompatFragment(), ImageView, BackButtonListener  {

    companion object {
        private const val PICK_IMAGE_REQUEST_ID = 1
        fun newInstance() = ImageFragment()
    }

    private var vb: FragmentImageBinding? = null

    val presenter: ImagePresenter by moxyPresenter {
        ImagePresenter(
            AndroidSchedulers.mainThread(),
            App.instance.router,
            AndroidConverter(context)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return FragmentImageBinding.inflate(inflater, container, false).also {
            vb = it
        }.root
    }

    override fun init() {
        button_to_convert.setOnClickListener {
            presenter.convertClick()
        }
    }

    override fun backPressed() = presenter.backPressed()

    override fun pickImage() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }

        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE_REQUEST_ID
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                data?.data?.let { uri ->
                    val bytes = context?.contentResolver?.openInputStream(uri)?.buffered()
                        ?.use { it.readBytes() }
                    bytes?.let {
                        presenter.imageSelected(Image(bytes))
                    }
                }
            }
        }
    }

    var convertInProgressDialog: Dialog? = null
    override fun showConvertInProgress() {
        context?.let {
            convertInProgressDialog = AlertDialog.Builder(it)
                .setMessage("идет конвертация")
                .setNegativeButton("отменить действие") { _, _ -> presenter.convertCancel() }
                .create()
            convertInProgressDialog?.show()
        }
    }

    override fun hideConvertInProgress() {
        convertInProgressDialog?.dismiss()
    }

    override fun showConvertSuccess() {
        Toast.makeText(context, "конвертация завершена", Toast.LENGTH_SHORT).show()
    }

    override fun showConvertCancel() {
        Toast.makeText(context, "конвертация отменена", Toast.LENGTH_SHORT).show()
    }

    override fun showConvertError() {
        Toast.makeText(context, "ошибка! обратитесь к разработчику", Toast.LENGTH_SHORT).show()
    }

}
package com.sujeet.nasaimageoftheday.utils.dialogBox

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.sujeet.nasaimageoftheday.R

object DescriptionDialog {
    private var dialog: Dialog? = null

    interface DescriptionDialogCallBack {
        fun onOk()
    }

    fun showDescriptionDialog(
        context: Context,
        descriptionText: String,
        descriptionDialogCallBack: DescriptionDialogCallBack,
    ) {


        dialog = Dialog(context, R.style.Theme_Dialog)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setGravity(Gravity.CENTER)
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)
        val window = dialog!!.window
        window!!.setGravity(Gravity.CENTER)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog?.setContentView(R.layout.description_dialog)
        dialog?.show()

        val ok = dialog?.findViewById<View>(R.id.ok_btn) as LinearLayout
        val descriptionTxt = dialog?.findViewById<View>(R.id.descriptionTxt) as TextView

        descriptionTxt.text = descriptionText



        ok.setOnClickListener {
            descriptionDialogCallBack.onOk()
            dialog?.dismiss()
            dialog = null
        }



    }
}
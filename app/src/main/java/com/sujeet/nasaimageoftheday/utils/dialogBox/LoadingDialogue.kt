package com.sujeet.nasaimageoftheday.utils.dialogBox

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import com.sujeet.nasaimageoftheday.R

object LoadingDialogue {

    private var dialog: Dialog?= null

    fun showDialog(context: Context) {
        if(dialog != null) return

        dialog = Dialog(context)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setContentView(R.layout.progress_layout)
//        val animate = dialog?.findViewById(R.id.noInternetAnimation) as LottieAnimationView
        dialog?.show()
    }

    fun dismissDialog(){
        if (dialog != null){
            dialog?.dismiss()
        }
        dialog = null
    }

}
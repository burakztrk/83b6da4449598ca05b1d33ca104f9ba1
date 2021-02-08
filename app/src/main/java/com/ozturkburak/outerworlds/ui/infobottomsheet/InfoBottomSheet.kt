package com.ozturkburak.outerworlds.ui.infobottomsheet

import android.content.Context
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.ozturkburak.outerworlds.R
import com.ozturkburak.outerworlds.utils.Task

class InfoBottomSheet(
    context: Context,
    private val onClick: Task<MaterialDialog>
) {

    private val dialog = MaterialDialog(context, BottomSheet(LayoutMode.WRAP_CONTENT))
        .cornerRadius(literalDp = 16f)
        .customView(R.layout.dialog_result)

    fun show(message: String) {
        if (dialog.isShowing)
            return

        dialog.show {
            this.getCustomView().run {
                findViewById<View>(R.id.button)?.setOnClickListener {
                    onClick.executable.invoke(dialog)
                    dismiss()
                }
                findViewById<TextView>(R.id.desc)?.setText(message)
            }
            onDismiss {
                onClick.executable.invoke(this)
            }
        }
    }
}
package com.example.samesamebill

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText

class InfoDialog(context: Context) {
    private val dialog = Dialog(context)

    fun myDialog() {
        dialog.setContentView(R.layout.info_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                                    WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val groupName = dialog.findViewById<EditText>(R.id.info_group_name_dlg)
        val date = dialog.findViewById<EditText>(R.id.info_date_dlg)
        val btnCheck = dialog.findViewById<Button>(R.id.info_check_btn_dlg)
        btnCheck.setOnClickListener {
            onClickListener.onClicked(groupName.text.toString(), date.text.toString())
            dialog.dismiss()
        }
    }

    interface ButtonClickListener {
        fun onClicked(groupName: String, date: String)
    }

    private lateinit var onClickListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickListener = listener
    }
}
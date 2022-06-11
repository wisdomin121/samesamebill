package com.example.samesamebill

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import java.util.*

class ParticipantDialog(context: Context) {
    private val dialog = Dialog(context)

    fun myDialog() {
        dialog.setContentView(R.layout.participant_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setTitle("참여자")
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val names = dialog.findViewById<EditText>(R.id.participant_names_dlg)
        val btnCheck = dialog.findViewById<Button>(R.id.participant_check_btn_dlg)

        btnCheck.setOnClickListener {
            onClickedListener.onClicked(names.text.toString().split(" "))
            dialog.dismiss()
        }

    }

    interface ButtonClickListener {
        fun onClicked(names: List<String>)
    }

    private lateinit var onClickedListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }
}
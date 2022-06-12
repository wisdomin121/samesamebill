package com.example.samesamebill

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import java.util.*
import kotlin.collections.ArrayList

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

        var names = dialog.findViewById<EditText>(R.id.participant_names_dlg)
        val btnCheck = dialog.findViewById<Button>(R.id.participant_check_btn_dlg)

        btnCheck.setOnClickListener {
            val extraNames= names.text.toString().split(" ")
            val arrayListNames = ArrayList<String>()
            for(i in 0..extraNames.size-1) {
                arrayListNames.add(extraNames[i])
            }
            onClickedListener.onClicked(arrayListNames)
            dialog.dismiss()
        }

    }

    interface ButtonClickListener {
        fun onClicked(names: ArrayList<String>)
    }

    private lateinit var onClickedListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }
}
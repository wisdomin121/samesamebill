package com.example.samesamebill

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import kotlin.coroutines.coroutineContext

class DetailDialog(context: Context) {
    private val dialog = Dialog(context)

    fun myDialog(names: List<String>, payerIds: ArrayList<Int>, borrowerIds: ArrayList<Int>) {
        dialog.setContentView(R.layout.detail_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        for(i in payerIds.indices) {
            val payerId = payerIds[i]
            val name = names[i]
            val radioButton = dialog.findViewById<RadioButton>(payerId)
            radioButton.visibility = View.VISIBLE
            radioButton.text = name

            val borrowerId = borrowerIds[i]
            val checkBox = dialog.findViewById<CheckBox>(borrowerId)
            checkBox.visibility = View.VISIBLE
            checkBox.text = name
        }

        val description = dialog.findViewById<EditText>(R.id.detail_description_dlg)
        val cost = dialog.findViewById<EditText>(R.id.detail_cost_dlg)
        val btnCheck = dialog.findViewById<Button>(R.id.detail_check_btn_dlg)

        btnCheck.setOnClickListener {
            val radioGroup = dialog.findViewById<RadioGroup>(R.id.detail_payer_radio_dlg)
            val radioCheckedId = radioGroup.checkedRadioButtonId
            val radioChecked = dialog.findViewById<RadioButton>(radioCheckedId)

            val checkedBox = ArrayList<String>()
            for(id in borrowerIds) {
                val checkBox = dialog.findViewById<CheckBox>(id)
                if(checkBox.isChecked) checkedBox.add(checkBox.text.toString())
            }
            onClickedListener.onClicked(description.text.toString(), Integer.parseInt(cost.text.toString()), radioChecked.text.toString(), checkedBox)
            dialog.dismiss()
        }
    }

    //, lender: String, borrowers: List<String>
    interface ButtonClickListener {
        fun onClicked(description: String, cost: Int, payer: String, borrower: ArrayList<String>)
    }

    private lateinit var onClickedListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }
}
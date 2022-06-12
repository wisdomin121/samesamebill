package com.example.samesamebill

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.samesamebill.databinding.ActivityBillBinding

class BillActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.billGroupName.text = intent.getStringExtra("group_name")
        binding.billDate.text = intent.getStringExtra("date")
        binding.billCost.text = intent.getStringExtra("cost")

        val result = intent.getStringArrayListExtra("result")
        for(i in 0 until result!!.size) {
            var tv = TextView(this)
            tv.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            tv.text = result[i]
            binding.billListItem.addView(tv)
        }
    }
}


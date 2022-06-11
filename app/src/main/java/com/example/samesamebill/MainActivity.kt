package com.example.samesamebill

import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samesamebill.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    lateinit var gn:String
    lateinit var d:String
    lateinit var n:List<String>
    lateinit var des:String
    var c:Int by Delegates.notNull<Int>()
    lateinit var p:String
    lateinit var b:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.infoEditBtn.setOnClickListener {
            val infoDlg = InfoDialog(this)
            infoDlg.myDialog()

            infoDlg.setOnClickedListener(object: InfoDialog.ButtonClickListener {
                override fun onClicked(groupName: String, date: String) {
                    binding.infoGroupName.text = String.format(resources.getString(R.string.info_group_name_st), groupName)
                    binding.infoDate.text = String.format(resources.getString(R.string.info_date_st), date)
                    gn = groupName
                    d = date
                }
            })
        }

        binding.participantAddBtn.setOnClickListener {
            val participantDlg = ParticipantDialog(this)
            participantDlg.myDialog()

            participantDlg.setOnClickedListener(object: ParticipantDialog.ButtonClickListener {
                override fun onClicked(names: List<String>) {
                    binding.participantNames.text = names.joinToString(" / ")
                    n = names
                }
            })
        }

        val dataSet = ArrayList<List<Any>>()
        val adapter = DetailAdapter(dataSet)
        binding.detailRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.detailRecyclerView.adapter = adapter

        binding.detailAddBtn.setOnClickListener {
            val detailDlg = DetailDialog(this)
            val payerIds = ArrayList<Int>()
            val borrowerIds = ArrayList<Int>()
            for(i in n.indices) {
                val payerIdFormat = "detail_payer_id_${i+1}"
                val payerId = resources.getIdentifier(payerIdFormat, "id", applicationContext.packageName)
                payerIds.add(payerId)

                val borrowerIdFormat = "detail_borrower_id_${i+1}"
                val borrowerId = resources.getIdentifier(borrowerIdFormat, "id", applicationContext.packageName)
                borrowerIds.add(borrowerId)
            }

            detailDlg.myDialog(n, payerIds, borrowerIds)
            detailDlg.setOnClickedListener(object: DetailDialog.ButtonClickListener {
                override fun onClicked(description: String, cost: Int, payer: String, borrower: ArrayList<String>) {
                    adapter.addItem(listOf(description, cost.toString()+"원", "돈 낸 사람 : "+payer))
                    des = description
                    c = cost
                    p = payer
                    b = borrower
                }
            })
        }

        binding.calculationBtn.setOnClickListener {
            val intent:Intent = Intent(this, BillActivity::class.java)
            startActivity(intent)
        }
    }
}
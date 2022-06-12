package com.example.samesamebill

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samesamebill.databinding.ActivityMainBinding
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    lateinit var gn:String
    lateinit var d:String
    lateinit var c:String
    var n = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.N)
    fun calculate(dataSet: ArrayList<ArrayList<ArrayList<String>>>, names: ArrayList<String>): ArrayList<String> {
        var eachCosts = hashMapOf<String, Double>()
        var totalCost = 0
        for(i in 0 until names.size) {
            eachCosts.put(names[i], 0.0)
        }

        for(i in 0 until dataSet.size) {
            val cost = dataSet[i][0][1].toDouble()
            val payer = dataSet[i][0][2]
            val borrowers = dataSet[i][1]
            val cnt = 1+borrowers.size

            val eachCost = ceil(cost/cnt)
            eachCosts.replace(payer, eachCosts[payer]!!-(eachCost*(cnt-1)))
            for(j in 0 until borrowers.size) {
                eachCosts.replace(borrowers[j], eachCosts[borrowers[j]]!!+eachCost)
            }
            totalCost += cost.toInt()
        }
        Log.d("total", "${eachCosts["엄마"]}, ${eachCosts["아빠"]}, ${eachCosts["언니"]}, ${eachCosts["나"]}")

        var result = ArrayList<String>()
        for((bKey, bValue) in eachCosts) {
            if(bValue>0) {
                for((pKey, pValue) in eachCosts) {
                    if(pValue<0 && -pValue==bValue) {
                        eachCosts.replace(pKey, 0.0)
                        eachCosts.replace(bKey, 0.0)
                        result.add("${bKey}(이)가 ${pKey}에게 ${bValue.toInt()}원")
                        break
                    }
                }
            }
        }

        for((bKey,bV) in eachCosts) {
            var bValue = bV
            var plus = 0.0
            if(bValue>0) {
                for((pKey, pValue) in eachCosts) {
                    bValue += plus
                    if(pValue<0 && -pValue>bValue) {
                        result.add("${bKey}(이)가 ${pKey}에게 ${bValue.toInt()}원")
                        eachCosts.replace(pKey, pValue+bValue)
                        eachCosts.replace(bKey, 0.0)
                        break
                    }else if(pValue<0 && -pValue<bValue) {
                        result.add("${bKey}(이)가 ${pKey}에게 ${-pValue.toInt()}원")
                        eachCosts.replace(pKey, 0.0)
                        eachCosts.replace(bKey, pValue+bValue)
                    }else if(pValue<0 && -pValue==bValue) {
                        result.add("${bKey}(이)가 ${pKey}에게 ${-pValue.toInt()}원")
                        eachCosts.replace(pKey, 0.0)
                        eachCosts.replace(bKey, 0.0)
                        break
                    }
                }
            }
        }
        c = "전체 금액 : " + totalCost.toString() + "원"
        return result
    }

    @RequiresApi(Build.VERSION_CODES.N)
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
                override fun onClicked(names: ArrayList<String>) {
                    binding.participantNames.text = names.joinToString(" / ")
                    n = names
                }
            })
        }

        val dataSet = ArrayList<ArrayList<ArrayList<String>>>()
        val adapter = DetailAdapter(dataSet)
        binding.detailRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.detailRecyclerView.adapter = adapter
        binding.detailAddBtn.setOnClickListener {
            val detailDlg = DetailDialog(this)
            val payerIds = ArrayList<Int>()
            val borrowerIds = ArrayList<Int>()
            if(n.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "참여자를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
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
                        adapter.addItem(arrayListOf(description, cost.toString(), payer), borrower)
                    }
                })
            }
        }


        binding.calculationBtn.setOnClickListener {
            val ds = adapter.getItems()
            val calculateResult:ArrayList<String> = calculate(ds, n)
            val intent:Intent = Intent(this, BillActivity::class.java).apply {
                putExtra("group_name", "모임명 : "+gn)
                putExtra("date", "날짜 : "+d)
                putExtra("cost", c)
                putExtra("result", calculateResult)
            }
            startActivity(intent)
        }
    }
}

package com.nym.mov.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.nym.mov.R
import kotlinx.android.synthetic.main.activity_my_wallet_top_up.*

class MyWalletTopUpActivity : AppCompatActivity() {
    private var status: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet_top_up)



        bt_top_up.setOnClickListener {
            startActivity(Intent(this, MyWalletSuccessActivity::class.java))
        }

        if(tv_10k.isClickable) {
            tv_10k.setOnClickListener {
                if(status) {
                    deselectMoney(tv_10k)
                } else {
                    selectMoney(tv_10k)
                }
            }
        } else if(tv_25k.isClickable) {
            tv_25k.setOnClickListener {
                if(status) {
                    deselectMoney(tv_25k)
                } else {
                    selectMoney(tv_25k)
                }
            }
        } else if(tv_50k.isClickable) {
            tv_50k.setOnClickListener {
                if(status) {
                    deselectMoney(tv_50k)
                } else {
                    selectMoney(tv_50k)
                }
            }
        } else if(tv_100k.isClickable) {
            tv_100k.setOnClickListener {
                if(status) {
                    deselectMoney(tv_100k)
                } else {
                    selectMoney(tv_100k)
                }
            }
        } else if(tv_200k.isClickable) {
            tv_200k.setOnClickListener {
                if(status) {
                    deselectMoney(tv_200k)
                } else {
                    selectMoney(tv_200k)
                }
            }
        } else if(tv_500k.isClickable) {
            tv_500k.setOnClickListener {
                if(status) {
                    deselectMoney(tv_500k)
                } else {
                    selectMoney(tv_500k)
                }
            }
        } else {
        }
    }

    private fun selectMoney(textView: TextView) {
        textView.setTextColor(resources.getColor(R.color.pink))
        textView.setBackgroundResource(R.drawable.shape_line_pink)
        status = true

        bt_top_up.visibility = View.VISIBLE
    }

    private fun deselectMoney(textView: TextView) {
        textView.setTextColor(resources.getColor(R.color.blueblack))
        textView.setBackgroundResource(R.drawable.shape_line_blueblack)
        status = false

        bt_top_up.visibility = View.INVISIBLE
    }
}
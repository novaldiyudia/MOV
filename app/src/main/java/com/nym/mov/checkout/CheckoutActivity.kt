package com.nym.mov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nym.mov.R
import com.nym.mov.model.Checkout
import com.nym.mov.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {
    private var dataList = ArrayList<Checkout>()
    private var total: Int = 0
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>

        for (a in dataList.indices) {
            total += dataList[a].harga!!.toInt()
        }

        dataList.add(Checkout("Total harus dibayar", total.toString()))

        rv_checkout.layoutManager = LinearLayoutManager(this)
        rv_checkout.adapter = CheckoutAdapter(dataList) {

        }

        bt_wallet.setOnClickListener {
            startActivity(Intent(this@CheckoutActivity, CheckoutSuccessActivity::class.java))
        }
    }
}
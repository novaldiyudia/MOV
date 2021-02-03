package com.nym.mov.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nym.mov.R
import com.nym.mov.sign.signup.SignUpActivity
import com.nym.mov.wallet.adapter.WalletAdapter
import com.nym.mov.wallet.model.Wallet
import kotlinx.android.synthetic.main.activity_my_wallet.*

class MyWalletActivity : AppCompatActivity() {
    private var dataList = ArrayList<Wallet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

        dataList.add(Wallet("Avengers Return","Sabtu 12 Jan, 2020",700000.0,"0"))
        dataList.add(Wallet("Top Up","Sabtu 12 Jan, 2020",1700000.0,"1"))
        dataList.add(Wallet("Top Up","Sabtu 12 Jan, 2020",2700000.0,"1"))

        rv_transaksi.layoutManager = LinearLayoutManager(this)
        rv_transaksi.adapter = WalletAdapter(dataList) {

        }

        bt_top_up.setOnClickListener {
            startActivity(Intent(this@MyWalletActivity, MyWalletTopUpActivity::class.java))
        }
    }
}
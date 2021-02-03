package com.nym.mov.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nym.mov.R
import com.nym.mov.home.HomeActivity
import kotlinx.android.synthetic.main.activity_checkout_success.*
import kotlinx.android.synthetic.main.activity_checkout_success.bt_home
import kotlinx.android.synthetic.main.activity_my_wallet_success.*

class MyWalletSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet_success)

        bt_home.setOnClickListener {
            finishAffinity()

            startActivity(Intent(this@MyWalletSuccessActivity, HomeActivity::class.java))
        }

        bt_wallet.setOnClickListener {
            startActivity(Intent(this@MyWalletSuccessActivity, MyWalletActivity::class.java))
        }
    }
}
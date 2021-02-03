package com.nym.mov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nym.mov.R
import com.nym.mov.home.HomeActivity
import kotlinx.android.synthetic.main.activity_checkout_success.*

class CheckoutSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_success)

        bt_home.setOnClickListener {
            startActivity(Intent(this@CheckoutSuccessActivity, HomeActivity::class.java))
        }
    }
}
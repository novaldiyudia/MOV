package com.nym.mov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nym.mov.R
import com.nym.mov.model.Checkout
import com.nym.mov.model.Film
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

class PilihBangkuActivity : AppCompatActivity() {
    var statusA3: Boolean = false
    var statusA4: Boolean = false
    var total: Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)

        val data = intent.getParcelableExtra<Film>("data")

        tv_kursi.text = data?.judul

        a3.setOnClickListener {
            if(statusA3) {
                a3.setImageResource(R.drawable.ic_rectangle_empty)
                statusA3 = false
                total -= 1

                beliTiket(total)

                dataList.remove(Checkout("A3", "70000"))
            } else {
                a3.setImageResource(R.drawable.ic_rectangle_selected)
                statusA3 = true
                total += 1

                beliTiket(total)

                val data = Checkout("A3", "70000")
                dataList.add(data)
            }
        }

        a4.setOnClickListener {
            if(statusA4) {
                a4.setImageResource(R.drawable.ic_rectangle_empty)
                statusA4 = false
                total -= 1

                beliTiket(total)

                dataList.remove(Checkout("A4", "70000"))
            } else {
                a4.setImageResource(R.drawable.ic_rectangle_selected)
                statusA4 = true
                total += 1

                beliTiket(total)

                val data = Checkout("A4", "70000")
                dataList.add(data)
            }
        }

        bt_top_up.setOnClickListener {
            startActivity(Intent(this@PilihBangkuActivity, CheckoutActivity::class.java).putExtra("data", dataList))
        }
    }

    private fun beliTiket(total: Int) {
        if(total == 0) {
            bt_top_up.setText("Beli Tiket")
            bt_top_up.visibility = View.INVISIBLE
        } else {
            bt_top_up.setText("Beli Tiket("+total+")")
            bt_top_up.visibility = View.VISIBLE
        }
    }
}
package com.nym.mov.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.nym.mov.R
import com.nym.mov.dashboard.DashboardFragment
import com.nym.mov.home.setting.SettingFragment
import com.nym.mov.tiket.TiketFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentHome = DashboardFragment()
        val fragmentTiket = TiketFragment()
        val fragmentSetting = SettingFragment()

        setFragment(fragmentHome)

        iv_home.setOnClickListener {
            setFragment(fragmentHome)
            changeIcon(iv_home, R.drawable.ic_home_active)
            changeIcon(iv_ticket, R.drawable.ic_tiket)
            changeIcon(iv_profile, R.drawable.ic_profile)
        }

        iv_ticket.setOnClickListener {
            setFragment(fragmentTiket)
            changeIcon(iv_home, R.drawable.ic_home)
            changeIcon(iv_ticket, R.drawable.ic_tiket_active)
            changeIcon(iv_profile, R.drawable.ic_profile)
        }

        iv_profile.setOnClickListener {
            setFragment(fragmentSetting)
            changeIcon(iv_home, R.drawable.ic_home)
            changeIcon(iv_ticket, R.drawable.ic_tiket)
            changeIcon(iv_profile, R.drawable.ic_profile_active)
        }

    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_home, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int : Int) {
        imageView.setImageResource(int)
    }
}
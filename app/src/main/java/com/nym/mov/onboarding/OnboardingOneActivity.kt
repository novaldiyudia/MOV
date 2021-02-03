package com.nym.mov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nym.mov.R
import com.nym.mov.sign.signin.SignInActivity
import com.nym.mov.utils.Preferences
import kotlinx.android.synthetic.main.activity_onboarding_one.*

class OnboardingOneActivity : AppCompatActivity() {

    lateinit var preference:Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        preference = Preferences(this)
        if(preference.getValues("onboarding").equals("1")){
            finishAffinity()

            startActivity(Intent(this@OnboardingOneActivity, SignInActivity::class.java))
        }

        btn_lanjutkan.setOnClickListener {
            startActivity(Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java))
        }

        btn_lewati.setOnClickListener {
            preference.setValues("onboarding", "1")

            finishAffinity()

            startActivity(Intent(this@OnboardingOneActivity, SignInActivity::class.java))
        }
    }
}
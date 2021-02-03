package com.nym.mov.sign.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.nym.mov.home.HomeActivity
import com.nym.mov.R
import com.nym.mov.sign.signup.SignUpActivity
import com.nym.mov.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    lateinit var iUsername:String
    lateinit var iPassword:String

    lateinit var mDatabase:DatabaseReference
    lateinit var preference:Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preference = Preferences(this)

        preference.setValues("onboarding", "1")
        if(preference.getValues("status").equals("1")){
            finishAffinity()

            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
        }

        btn_masuk.setOnClickListener {
            iUsername = et_username.text.toString()
            iPassword = et_password.text.toString()

            if(iUsername.equals("")) {
                et_username.error = "Silahkan tulis Username Anda"
                et_username.requestFocus()
            } else if(iPassword.equals("")) {
                et_password.error = "Silahkan tulis Password Anda"
                et_password.requestFocus()
            } else {
                pushLogin(iUsername, iPassword)
            }
        }

        btn_daftar.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var user = snapshot.getValue(User::class.java)
                if(user == null) {
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan", Toast.LENGTH_LONG).show()
                } else {

                    if(user.password.equals(iPassword)) {
                        preference.setValues("nama", user.nama.toString())
                        preference.setValues("username", user.username.toString())
                        preference.setValues("url", user.url.toString())
                        preference.setValues("email", user.email.toString())
                        preference.setValues("saldo", user.saldo.toString())
                        preference.setValues("status", "1")
                        startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                    } else {
                        Toast.makeText(this@SignInActivity, "Password Anda Salah", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignInActivity, error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
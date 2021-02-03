package com.nym.mov.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import com.nym.mov.R
import com.nym.mov.sign.signin.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    lateinit var sUsername: String
    lateinit var sPassword: String
    lateinit var sNama: String
    lateinit var sEmail: String

    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        btn_lanjutkan.setOnClickListener {
            sUsername = et_username.text.toString()
            sPassword = et_password.text.toString()
            sNama = et_nama.text.toString()
            sEmail = et_email.text.toString()

            if(sUsername.equals("")) {
                et_username.error = "Silahkan isi Username Anda"
                et_username.requestFocus()
            } else if(sPassword.equals("")) {
                et_password.error = "Silahkan isi Password Anda"
                et_password.requestFocus()
            } else if(sNama.equals("")) {
                et_nama.error = "Silahkan isi Nama Anda"
                et_nama.requestFocus()
            } else if(sEmail.equals("")) {
                et_email.error = "Silahkan isi Email Anda"
                et_email.requestFocus()
            } else {
                saveUser(sUsername, sPassword, sNama, sEmail)
            }
        }
    }

    private fun saveUser(sUsername: String, sPassword: String, sNama: String, sEmail: String) {
        val user = User()
        user.email = sEmail
        user.username = sUsername
        user.nama = sNama
        user.password = sPassword

        if(sUsername != null) {
            cUsername(sUsername, user)
        }
    }

    private fun cUsername(iUsername: String, data: User) {
        mDatabaseReference.child(iUsername).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if(user == null) {
                    mDatabaseReference.child(iUsername).setValue(data)

                    startActivity(Intent(this@SignUpActivity, SignUpPhotoscreenActivity::class.java).putExtra("nama", data.nama))
                } else {
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
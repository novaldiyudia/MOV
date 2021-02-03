package com.nym.mov.sign.signup

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nym.mov.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.nym.mov.home.HomeActivity
import com.nym.mov.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_up_photoscreen.*
import java.util.*

class SignUpPhotoscreenActivity : AppCompatActivity(), PermissionListener {
    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd: Boolean = false

    lateinit var filePath: Uri
    lateinit var storage: FirebaseStorage
    lateinit var storageReferensi: StorageReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photoscreen)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReferensi = storage.getReference()

        tv_kursi.text = "Welcome,\n" + intent.getStringExtra("nama")

        iv_add_photo.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                bt_simpan.visibility = View.VISIBLE
                iv_add_photo.setImageResource(R.drawable.ic_add)
                iv_photo.setImageResource(R.drawable.user_pic)
            } else {
                Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA)
                    .withListener(this).check()
            }
        }

        bt_top_up.setOnClickListener {
            finishAffinity()

            startActivity(Intent(this@SignUpPhotoscreenActivity, HomeActivity::class.java))
        }

        bt_simpan.setOnClickListener {
            if (filePath != null) {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                var ref = storageReferensi.child("images/" + UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Uploaded", Toast.LENGTH_LONG).show()

                        ref.downloadUrl.addOnSuccessListener {
                            preferences.setValues("url", it.toString())
                        }

                        finishAffinity()
                        startActivity(
                            Intent(
                                this@SignUpPhotoscreenActivity,
                                HomeActivity::class.java
                            )
                        )
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener { taskSnapshot ->
                        var progress =
                            100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload"+progress.toInt()+" %")
                    }
            } else {

            }
        }
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                takePictureIntent -> takePictureIntent.resolveActivity(packageManager)?.also {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Anda tidak bisa menambahkan photo Profile", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {

    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tergesah? klik tombol upload nanti aja", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            var bitmap = data?.extras?.get("data") as Bitmap
            statusAdd = true

            filePath = data.getData()!!
            Glide.with(this).load(bitmap).apply(RequestOptions.circleCropTransform()).into(iv_photo)

            bt_simpan.visibility = View.VISIBLE
            iv_add_photo.setImageResource(R.drawable.ic_delete)
        }
    }
}

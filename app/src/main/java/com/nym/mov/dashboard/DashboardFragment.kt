package com.nym.mov.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.nym.mov.DetailActivity
import com.nym.mov.R
import com.nym.mov.model.Film
import com.nym.mov.utils.Preferences
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {
    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tv_kursi.setText(preferences.getValues("nama"))
        if(preferences.getValues("saldo").equals("")) {
            currency(preferences.getValues("saldo")!!.toDouble(), tv_saldo)
        }

        Glide.with(this).load(preferences.getValues("url")).apply(RequestOptions.circleCropTransform()).into(iv_photo_profile)

        rv_now_playing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_coming_soon.layoutManager = LinearLayoutManager(context)

        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for(getdataSnapshot in snapshot.children) {
                    var film = getdataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                rv_now_playing.adapter = NowPlayingAdapter(dataList) {
                    startActivity(Intent(context, DetailActivity::class.java).putExtra("data", it))
                }

                rv_coming_soon.adapter = ComingSoonAdapter(dataList) {
                    startActivity(Intent(context, DetailActivity::class.java).putExtra("data", it))
                }
            }
        })
    }

    private fun currency(harga: Double, textview: TextView) {
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textview.setText(format.format(harga))
    }
}
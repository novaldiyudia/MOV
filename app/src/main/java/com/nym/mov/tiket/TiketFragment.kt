package com.nym.mov.tiket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.nym.mov.DetailActivity
import com.nym.mov.R
import com.nym.mov.dashboard.ComingSoonAdapter
import com.nym.mov.model.Film
import com.nym.mov.utils.Preferences
import kotlinx.android.synthetic.main.fragment_tiket.*

/**
 * A simple [Fragment] subclass.
 */
class TiketFragment : Fragment() {
    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference
    private var datalist = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tiket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(context!!)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        rv_tiket.layoutManager = LinearLayoutManager(context)
        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                datalist.clear()
                for(getdataSnapshot in snapshot.children) {
                    val film = getdataSnapshot.getValue(Film::class.java)
                    datalist.add(film!!)
                }

                rv_tiket.adapter = ComingSoonAdapter(datalist) {
                    startActivity(Intent(context, TiketActivity::class.java).putExtra("data", it))
                }

                tv_total.setText("${datalist.size} Movies")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}
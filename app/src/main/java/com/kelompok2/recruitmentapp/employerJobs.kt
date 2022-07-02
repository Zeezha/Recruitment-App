package com.kelompok2.recruitmentapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompok2.recruitmentapp.Adapter.ManagementJobAdapter
import com.kelompok2.recruitmentapp.Model.Job
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kelompok2.recruitmentapp.Model.User

class employerJobs : Fragment() {

    private var recyclerViewajiry: RecyclerView? = null
    private var managementJobAdapter: ManagementJobAdapter? = null
    private var mJob: MutableList<Job>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_employer_jobs, container, false)

        recyclerViewajiry = view.findViewById(R.id.recycler_view_ajiry)
        recyclerViewajiry?.setHasFixedSize(true)
        val linearLayout = LinearLayoutManager(context)
        linearLayout.reverseLayout = true
        linearLayout.stackFromEnd = true
        recyclerViewajiry?.layoutManager = linearLayout
        mJob = ArrayList()
        managementJobAdapter = context?.let { ManagementJobAdapter(it,mJob as ArrayList<Job>,"") }
        recyclerViewajiry?.adapter = managementJobAdapter
        recyclerViewajiry?.visibility = View.VISIBLE
        retrieveJobs()

        return view
    }

    private fun retrieveJobs() {


        val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs")
        jobsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {


                mJob?.clear()

                for (snapshot in p0.children) {
                    for (data in snapshot.children){
                        val job = data.getValue(Job::class.java)
                        Log.e("Data Jobs",job.toString());
                        if (job != null)
                        {
                            mJob?.add(job)
                        }
                        managementJobAdapter?.notifyDataSetChanged()
                    }
                }
                
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}
package com.kelompok2.recruitmentapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompok2.recruitmentapp.Adapter.JobAdapter
import com.kelompok2.recruitmentapp.Model.Job
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_next.view.*


class NextFragment : Fragment() {
    private lateinit var firebaseUser: FirebaseUser
    private var mJob: MutableList<Job>? = null
    private var jobAdapter: JobAdapter? = null
    private lateinit var profileId:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_next, container, false)

        var recyclerviewmoringa: RecyclerView? = null

        recyclerviewmoringa = view.findViewById(R.id.recycler_view_moringa)
        recyclerviewmoringa?.setHasFixedSize(true)
        recyclerviewmoringa?.itemAnimator = DefaultItemAnimator()
        val linearLayout = LinearLayoutManager(context)
        linearLayout.reverseLayout = true
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        linearLayout.stackFromEnd = true
        recyclerviewmoringa?.layoutManager = linearLayout
        mJob = ArrayList()
        jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
        recyclerviewmoringa?.adapter = jobAdapter
        recyclerviewmoringa?.visibility = View.VISIBLE

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)



        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()
        }


        else if (profileId != firebaseUser.uid)
        {

        }

        view.lll.text = profileId + " jobs"



            val query = FirebaseDatabase.getInstance().getReference("Jobs").child("Building and Construction")
                .orderByChild("jobtitle")
                .startAt(profileId).endAt(profileId + "\uf8ff")


            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot)
                {
                    mJob?.clear()

                    for (snapshot in p0.children)
                    {
                        val job = snapshot.getValue(Job::class.java)
                        if (job != null)
                        {
                            mJob?.add(job)
                        }
                    }

                    jobAdapter?.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

            retrieveJobstwo()
       
//        inputsearch()




        return view
    }

    private fun retrieveJobstwo() {
        val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs").child("Engineering")
        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (view?.lll?.text.toString() == profileId)
                {
                    mJob?.clear()

                    for (snapshot in p0.children)
                    {
                        val job = snapshot.getValue(Job::class.java)
                        if (job != null)
                        {
                            mJob?.add(job)
                        }
                    }

                    jobAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


//


}
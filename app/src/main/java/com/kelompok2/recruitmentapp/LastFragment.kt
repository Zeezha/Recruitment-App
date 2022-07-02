package com.kelompok2.recruitmentapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompok2.recruitmentapp.Adapter.*
import com.kelompok2.recruitmentapp.Model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.kelompok2.recruitmentapp.Activity.LatestMessagesActivity
import kotlinx.android.synthetic.main.activity_main7.view.*
import kotlinx.android.synthetic.main.fragment_last.view.wditwhat

class LastFragment : Fragment() {
    private lateinit var firebaseUser: FirebaseUser
    private var mJob: MutableList<Job>? = null
    private var categoryAdapter: CategoryAdapter? = null
    private var searchAdapter: SearchAdapter? = null
    private var mSearchh:MutableList<Searchitem>? = null
    private var mCategory:MutableList<Category>? = null
    private var feedAdapter: FeedAdapter? = null
    private var jobAdapter: JobAdapter? = null
    private var mFeed:MutableList<Feed>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.activity_main7, container, false)

        view.optiven.setOnClickListener {
            startActivity(Intent(context, LatestMessagesActivity::class.java))
        }


        firebaseUser = FirebaseAuth.getInstance().currentUser!!


        var recyclerView: RecyclerView? = null
        var recyclerviewuon:RecyclerView? = null

        recyclerView = view.findViewById(R.id.recycler_view_home_t)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        val linearLayout = LinearLayoutManager(context)
        linearLayout.reverseLayout = true
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        linearLayout.stackFromEnd = true
        recyclerView?.layoutManager = linearLayout
        mJob = ArrayList()
        jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
        recyclerView?.adapter = jobAdapter
        recyclerView?.visibility = View.VISIBLE


        recyclerviewuon = view.findViewById(R.id.recycler_view_uonke)
        recyclerviewuon?.setHasFixedSize(true)
        recyclerviewuon?.itemAnimator = DefaultItemAnimator()
        val linearLayoutuon = LinearLayoutManager(context)
        linearLayoutuon.reverseLayout = true
        linearLayoutuon.orientation = LinearLayoutManager.VERTICAL
        linearLayoutuon.stackFromEnd = true
        recyclerviewuon?.layoutManager = linearLayoutuon
        mJob = ArrayList()
        jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
        recyclerviewuon?.adapter = jobAdapter
        recyclerviewuon?.visibility = View.VISIBLE

        var linearLayoutcategories = LinearLayoutManager(context)
        linearLayoutcategories.reverseLayout = true
        linearLayoutcategories.orientation = LinearLayoutManager.VERTICAL
        linearLayoutcategories.stackFromEnd = true
        mCategory = ArrayList()
        categoryAdapter = context?.let { CategoryAdapter(it,mCategory as ArrayList<Category>,true) }


        var linearLayoutfeed = LinearLayoutManager(context)
        linearLayoutfeed.reverseLayout = true
        linearLayoutfeed.orientation = LinearLayoutManager.VERTICAL
        linearLayoutfeed.stackFromEnd = true
        mFeed = ArrayList()
        feedAdapter = context?.let { FeedAdapter(it,mFeed as ArrayList<Feed>,true) }


        val linearLayoutsearc = LinearLayoutManager(context)
        linearLayoutsearc.reverseLayout = true
        linearLayoutsearc.orientation = LinearLayoutManager.VERTICAL
        linearLayoutsearc.stackFromEnd = true
        mSearchh = ArrayList()
        searchAdapter = context?.let { SearchAdapter(it,mSearchh as ArrayList<Searchitem>,true) }

        retrieveCategories()
        retrieveFeed()
        retrivesuggestedsearches()


        val userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<User>(User::class.java)


                    val testing = user!!.getMobile()

                    val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs")
                    jobsRef.addValueEventListener(object : ValueEventListener {
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
                                    jobAdapter?.notifyDataSetChanged()
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        return view
    }

    private fun retrivesuggestedsearches() {
        val jobsReff = FirebaseDatabase.getInstance().getReference("Searchtext").child(firebaseUser.uid)
        jobsReff.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                mSearchh?.clear()

                if (snapshot.exists()){
                    val job = snapshot.getValue(Searchitem::class.java)
                    if (job != null)
                    {
                        mSearchh?.add(job)
                    }
                    searchAdapter?.notifyDataSetChanged()
                }

            }


            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                mSearchh?.clear()

                if (snapshot.exists()){
                    val job = snapshot.getValue(Searchitem::class.java)
                    if (job != null)
                    {
                        mSearchh?.add(job)
                    }
                    searchAdapter?.notifyDataSetChanged()
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

        })
    }

    private fun retrieveCategories() {
        val categoriesRef = FirebaseDatabase.getInstance().reference.child("Categories")

        categoriesRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                mCategory?.clear()

                for (snapshot in p0.children)
                {
                    var category = snapshot.getValue(Category::class.java)

                    if (category != null)
                    {
                        mCategory!!.add(category)
                    }
                    categoryAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun retrieveFeed() {
        val feedRef = FirebaseDatabase.getInstance().reference.child("Feed")

        feedRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                mFeed?.clear()

                for (snapshot in p0.children)
                {
                    val feed = snapshot.getValue(Feed::class.java)

                    if (feed != null)
                    {
                        mFeed!!.add(feed)
                    }


                    feedAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}
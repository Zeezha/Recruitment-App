package com.kelompok2.recruitmentapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kelompok2.recruitmentapp.Model.Employer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kelompok2.recruitmentapp.Activity.SigninActivity
import com.kelompok2.recruitmentapp.Activity.ujumbeActivity
import kotlinx.android.synthetic.main.fragment_employer_profile.*
import kotlinx.android.synthetic.main.fragment_employer_profile.view.*

class employerProfile : Fragment() {
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_employer_profile, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val jobsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)

        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<Employer>(Employer::class.java)

                    webme.text = user!!.website
                    webmek.text = user!!.headquarters
                    webmefu.text = user!!.founded
                    webmemoo.text = user!!.companysize
                    webmemew.text = user!!.industry
                    webtu.text = user!!.aboutcompany

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        view.btnout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(context, MainActivity9::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }

        view.btntentangkami.setOnClickListener {
            val intent = Intent(context, ujumbeActivity::class.java)
            startActivity(intent)
        }


        return view
    }


}
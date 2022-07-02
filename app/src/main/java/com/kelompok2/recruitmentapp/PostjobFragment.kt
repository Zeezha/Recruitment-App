package com.kelompok2.recruitmentapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kelompok2.recruitmentapp.Activity.JobpostingActivity
import kotlinx.android.synthetic.main.fragment_postjob.view.*


class PostjobFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_postjob, container, false)

        view.postjob_btn.setOnClickListener {
            startActivity(Intent(context, JobpostingActivity::class.java))
        }




        return view



    }



}
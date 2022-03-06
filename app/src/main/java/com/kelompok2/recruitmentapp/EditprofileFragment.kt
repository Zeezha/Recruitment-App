package com.kelompok2.recruitmentapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_editprofile.view.*


class EditprofileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_editprofile, container, false)


        view.btn_settings.setOnClickListener {
            startActivity(Intent(context,BasicActivity::class.java))
        }

        return view
    }



}
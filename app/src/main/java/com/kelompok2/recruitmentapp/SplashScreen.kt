package com.kelompok2.recruitmentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.kelompok2.recruitmentapp.Activity.introductionactivity
import com.kelompok2.recruitmentapp.Helper.Constant
import com.kelompok2.recruitmentapp.Helper.PrefHelper
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    lateinit var prefHelper: PrefHelper
    private val SPLASH_TIME: Long = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        prefHelper = PrefHelper(this)

        Handler().postDelayed({
            if (FirebaseAuth.getInstance().currentUser == null)
            {

                startActivity(Intent(this, introductionactivity::class.java))

            }
            else {
                Log.e("PREF LEVEL",prefHelper.getString(Constant.PREF_LEVEL).toString());
                if(prefHelper.getString( Constant.PREF_LEVEL)=="Candidate"){
                    startActivity(Intent(this,HomeCandidate::class.java))
                }else if(prefHelper.getString( Constant.PREF_LEVEL )=="Employer"){
                    startActivity(Intent(this, HomeCompanyActivity::class.java))
                    if (FirebaseAuth.getInstance().currentUser == null)
                    {

                        startActivity(Intent(this, introductionactivity::class.java))

                    }
                    else {
                        Log.e("PREF LEVEL",prefHelper.getString(Constant.PREF_LEVEL).toString());
                        if(prefHelper.getString( Constant.PREF_LEVEL)=="Candidate"){
                            startActivity(Intent(this,HomeCandidate::class.java))
                        }else if(prefHelper.getString( Constant.PREF_LEVEL )=="Employer"){
                            startActivity(Intent(this, HomeCompanyActivity::class.java))
                        }
                        //            finish()
                    }}
            }
        }, SPLASH_TIME)


    }


}
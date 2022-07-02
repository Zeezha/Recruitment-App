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
        val window: Window = this@SplashScreen.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@SplashScreen,R.color.giddy)


        if (FirebaseAuth.getInstance().currentUser == null)
        {

            startActivity(Intent(this, introductionactivity::class.java))


        }
        else {
        Handler().postDelayed({
            Log.e("PREF LEVEL",prefHelper.getString(Constant.PREF_LEVEL).toString());
            if(prefHelper.getString( Constant.PREF_LEVEL)=="Candidate"){
                startActivity(Intent(this,HomeCandidate::class.java))
            }else if(prefHelper.getString( Constant.PREF_LEVEL )=="Employer"){
                startActivity(Intent(this, HomeCompanyActivity::class.java))
            }
//            finish()
        }, SPLASH_TIME)
        }


    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser == null)
        {
            val intent = Intent(this@SplashScreen, introductionactivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }


}
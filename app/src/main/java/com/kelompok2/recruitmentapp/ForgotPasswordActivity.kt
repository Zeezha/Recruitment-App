package com.kelompok2.recruitmentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kelompok2.recruitmentapp.Activity.SigninActivity
import com.kelompok2.recruitmentapp.Activity.SignupCompanyActivity
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val mAuth:FirebaseAuth = FirebaseAuth.getInstance()

        btn_back.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }

        btnlogin_forgot.setOnClickListener {
            val email = email_text_two_forgot.text.toString()

            when{
                TextUtils.isEmpty(email) -> Toast.makeText(this,"Email is required", Toast.LENGTH_LONG).show()

                else ->{
                    mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(this){task ->
                            if (task.isSuccessful)
                            {
                                Toast.makeText(this,"Tautan reset telah dikirim ke Email Anda",Toast.LENGTH_LONG).show()
                            }
                            else{
                                val message = task.exception!!.toString()
                                Toast.makeText(this, "Error: $message",Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        }

    }
}
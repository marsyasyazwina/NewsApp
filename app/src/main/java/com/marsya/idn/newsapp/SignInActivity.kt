package com.marsya.idn.newsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.marsya.idn.newsapp.databinding.ActivityMainBinding
import com.marsya.idn.newsapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var signinBinding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signinBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(signinBinding.root)
        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()

        signinBinding.btnSignIn.setOnClickListener (this)
        signinBinding.tvForgot.setOnClickListener (this)
        signinBinding.tvSignUp.setOnClickListener (this)

    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btnSignIn -> signInEmailPasword()
            R.id.tvSignUp -> startActivity(SignupActivity.getLaunchService(this))
            R.id.tvForgot -> startActivity(ForgotPaswordActivity.getLaunchService(this))
        }

    }

    private fun signInEmailPasword() {
        val email = signinBinding.etEmail.text.toString()
        val pasword = signinBinding.etPasword.text.toString()

        if (email.isEmpty() || pasword.isEmpty()){
            Toast.makeText(this,"isi dengan lengkap",Toast.LENGTH_SHORT).show()

        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pasword).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                startActivity(MainActivity.getLaunchService(this))
                return@addOnCompleteListener
            }else {
                Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
            }
            }.addOnFailureListener {
                Toast.makeText(this, "Check your internet", Toast.LENGTH_SHORT).show()
            }

            }

    override fun onStart() {
        super.onStart()
        val userStatus = FirebaseAuth.getInstance().currentUser
        if (userStatus != null){
            startActivity(MainActivity.getLaunchService(this))
        }
    }
    companion object{
        fun getLaunchService(from : Context)= Intent(from,
            SignInActivity::class.java).apply{
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
        }


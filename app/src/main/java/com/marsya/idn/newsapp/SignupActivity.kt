package com.marsya.idn.newsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.marsya.idn.newsapp.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUser: DatabaseReference
    private var firebaseUserId: String = ""
    private lateinit var signupBinding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(signupBinding.root)
        mAuth = FirebaseAuth.getInstance()
        signupBinding.btnSignup.setOnClickListener(this)
    }

    companion object {

        fun getLaunchService(from: Context) = Intent(from, SignupActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btnSignup -> signupUser()
        }
    }

    private fun signupUser() {
        val fullName: String = signupBinding.etNameSignup.text.toString()
        val email: String = signupBinding.etEmailSignup.text.toString()
        val password: String = signupBinding.etPaswordSignup.text.toString()
        val confirmPasword: String = signupBinding.etConfirmPaswordSignup.text.toString()
        if (fullName == "") {
            Toast.makeText(this, "Not Empty", Toast.LENGTH_SHORT).show()
        } else if (email == "") {
            Toast.makeText(this, "Not Empty", Toast.LENGTH_SHORT).show()
        } else if (password == "") {
            Toast.makeText(this, "Not Empty", Toast.LENGTH_SHORT).show()
        } else if (confirmPasword == "") {
            Toast.makeText(this, "Not Empty", Toast.LENGTH_SHORT).show()
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { it ->
                if (it.isSuccessful){
                    firebaseUserId = mAuth.currentUser!!.uid
                    refUser = FirebaseDatabase.getInstance().reference.child("Users")
                        .child(firebaseUserId)
                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserId
                    userHashMap["fullname"] = fullName
                    userHashMap["email"] = email
                    userHashMap["linkedIn"] = ""
                    userHashMap["Instagram"] = ""
                    userHashMap["medium"] = ""
                    userHashMap["photo"] = ""

                    refUser.updateChildren(userHashMap).addOnCompleteListener {
                        if ((it.isSuccessful)){
                            startActivity((Intent(MainActivity.getLaunchService(this))))
                            finish()
                        }else{
                            Toast.makeText(this, "Register failed" + it.exception!!.message.toString(),
                            Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }
            }
        }
    }
}




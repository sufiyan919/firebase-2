package com.example.firebase21



import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity(), View.OnClickListener {
    //defining view objects
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    private var buttonSignup: Button? = null
    private var progressDialog: ProgressDialog? = null



    //defining firebaseauth object
    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance()

        //initializing views
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
        buttonSignup = findViewById<View>(R.id.buttonSignup) as Button
        progressDialog = ProgressDialog(this)

        //attaching listener to button
        buttonSignup!!.setOnClickListener(this)
    }

    private fun registerUser() {

        //getting email and password from edit texts
        val email = editTextEmail!!.text.toString().trim { it <= ' ' }
        val password = editTextPassword!!.text.toString().trim { it <= ' ' }

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show()
            return
        }

        //if the email and password are not empty
        //displaying a progress dialog
        progressDialog!!.setMessage("Registering Please Wait...")
        progressDialog!!.show()

        //creating a new user
        firebaseAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this,
                OnCompleteListener { task -> //checking if success
                    if (task.isSuccessful) {
                        //display some message here
                        Toast.makeText(this@MainActivity,
                            "Successfully registered",
                            Toast.LENGTH_LONG).show()
                    } else {
                        //display some message here
                        Toast.makeText(this@MainActivity, "Registration Error", Toast.LENGTH_LONG)
                            .show()
                    }
                    progressDialog!!.dismiss()
                })
    }

    override fun onClick(view: View) {
        //calling register method on click
        registerUser()
    }
}
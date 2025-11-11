package com.example.papertraderapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Connect XML views to Kotlin variables
        val email = findViewById<EditText>(R.id.signupEmail)
        val password = findViewById<EditText>(R.id.signupPassword)
        val signupBtn = findViewById<Button>(R.id.signupBtn)
        val toLogin = findViewById<TextView>(R.id.toLogin)

        // When user presses “Sign Up” button
        signupBtn.setOnClickListener {
            val userEmail = email.text.toString().trim()     // get typed email
            val userPass = password.text.toString().trim()   // get typed password

            // Make sure both fields are filled out
            if (userEmail.isNotEmpty() && userPass.isNotEmpty()) {

                // Create new Firebase account with email & password
                auth.createUserWithEmailAndPassword(userEmail, userPass)
                    .addOnSuccessListener { result ->
                        // Firebase gives us a unique user ID for each account
                        val userId = result.user?.uid ?: return@addOnSuccessListener

                        // Create a Firestore document for the user with starting balance
                        val userData = hashMapOf(
                            "email" to userEmail,
                            "balance" to 20000.0,                 // starting demo money
                            "createdAt" to FieldValue.serverTimestamp()
                        )

                        // Save user data under collection “users” → document (userId)
                        db.collection("users").document(userId).set(userData)

                        Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show()

                        // After sign-up, go to login page
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        // If something fails (weak password, bad email, etc.)
                        Toast.makeText(this, "Sign Up failed: ${it.message}", Toast.LENGTH_SHORT).show()
                    }

            } else {
                Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // “Already have an account? Login” → navigate back to login screen
        toLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
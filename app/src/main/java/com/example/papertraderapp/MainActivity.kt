package com.example.papertraderapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // ✅ Handle edge-to-edge layout safely
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Initialize Firebase
        FirebaseApp.initializeApp(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        /*
        ────────────────────────────────────────────────────────────────
        🧪 Crashlytics Test Code (commented out for now)
        ────────────────────────────────────────────────────────────────
        // Get button from layout and attach listener
        val crashButton = findViewById<Button>(R.id.testCrashButton)
        crashButton.setBackgroundColor(Color.parseColor("#00C896"))
        crashButton.setTextColor(Color.WHITE)

        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        // (Optional) Programmatically add another test button for confirmation
        val crashButton2 = Button(this).apply {
            text = "Test Crash (Programmatic)"
            setBackgroundColor(Color.parseColor("#00C896"))
            setTextColor(Color.WHITE)
            setOnClickListener { throw RuntimeException("Test Crash") }
        }
        addContentView(
            crashButton2,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        ────────────────────────────────────────────────────────────────
        */
    }
}
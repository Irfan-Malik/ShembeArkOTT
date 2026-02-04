//package com.tara.tv.ui
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.main.taratv.R
//import com.tara.tv.presentation.home.HomeFragment
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        // ...existing code...
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // ...existing code...
//        // Host fragment defined in layout; navigation setup if you use it.
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, HomeFragment())
//                .commit()
//        }
//    }
//}

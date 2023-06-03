package com.example.bankingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Splash Screen
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val bottomNavigation :BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navController=findNavController(R.id.fragmentContainerView)
        bottomNavigation.setupWithNavController(navController)

    }

}
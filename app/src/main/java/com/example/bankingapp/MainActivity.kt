package com.example.bankingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.bankingapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Splash Screen
        installSplashScreen()

        //DataBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Default Fragment
        loadFragment(CustomersFragment())

        //Navigation Logic
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.customers_fragment -> loadFragment(CustomersFragment())
                R.id.transactions_fragment -> loadFragment(TransactionsFragment())
                R.id.about_fragment -> loadFragment(AboutFragment())
                else ->{
                    false
                }
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
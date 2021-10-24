package com.example.testtaskr.ui
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testtaskr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bdn: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdn = ActivityMainBinding.inflate(layoutInflater)
        val view = bdn.root
        setContentView(view)
    }

}
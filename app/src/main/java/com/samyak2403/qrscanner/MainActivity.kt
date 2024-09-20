/*
 * Created by Samyak Kamble on 9/19/24, 10:25 PM
 *  Copyright (c) 2024 . All rights reserved.
 *  Last modified 9/19/24, 9:53 PM
 */

package com.samyak2403.qrscanner

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.samyak2403.qrscanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}
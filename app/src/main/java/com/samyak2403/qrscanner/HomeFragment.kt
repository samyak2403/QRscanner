/*
 * Created by Samyak Kamble on 9/19/24, 10:24 PM
 *  Copyright (c) 2024 . All rights reserved.
 *  Last modified 9/19/24, 10:24 PM
 */

package com.samyak2403.qrscanner

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.BuildConfig

import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.samyak2403.qrscanner.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var codeScanner: CodeScanner? = null
    private val TAG = "HomeFragment"

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Initialize CodeScanner
        codeScanner = context?.let { CodeScanner(it, binding.scannerView) }

        codeScanner?.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false
        }

        codeScanner?.decodeCallback = DecodeCallback {
            activity?.runOnUiThread {
                Log.d(TAG, "Result: ${it.text}")
                Log.d(TAG, "format: ${it.barcodeFormat.name}")
                Log.d(TAG, "rawbytes: ${it.rawBytes.toString()}")
                vibratePhone()
                findNavController().navigate(
                    R.id.action_homeFragment_to_resultFragment,
                    bundleOf("result" to it.text)
                )
            }
        }
        codeScanner?.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            activity?.runOnUiThread {
                Toast.makeText(
                    context, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
                vibratePhone()
            }
        }

        binding.scannerView.setOnClickListener {
            codeScanner?.startPreview()
        }


        // Display app version from your app's BuildConfig
//        binding.versionName.text = "version: ${BuildConfig.VERSION_NAME}"
        val packageInfo = context?.packageManager?.getPackageInfo(context?.packageName ?: "", 0)
        val versionName = packageInfo?.versionName
        binding.versionName.text = "Version: $versionName"




        // Safely access and display app version from BuildConfig


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        codeScanner?.startPreview()
    }

    override fun onPause() {
        codeScanner?.releaseResources()
        super.onPause()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun vibratePhone() {
        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE))
    }
}

/*
 * Created by Samyak Kamble on 9/19/24, 10:24 PM
 *  Copyright (c) 2024 . All rights reserved.
 *  Last modified 9/19/24, 10:24 PM
 */

package com.samyak2403.qrscanner

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.samyak2403.qrscanner.databinding.FragmentResultBinding
import java.io.File
import java.io.FileWriter


class ResultFragment : Fragment() {

    lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(inflater, container, false)

        arguments?.get("result")?.toString()?.let {
            when {
                it.startsWith("tel:") -> initTeleView(it)
                it.startsWith("http") || it.startsWith("www") -> initWebView(it)
                else -> initPlainView(it)
            }
        }

        return binding.root
    }

    //-------------------
    private fun initTeleView(result: String) {
        binding.phoneLayout.visibility = View.VISIBLE
        binding.phoneNumber.text = result.removePrefix("tel:")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(result: String) {
        binding.webLayout.visibility = View.VISIBLE
        binding.webLink.text = result

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {}
        }
        binding.webView.loadUrl(result)
    }

    private fun initPlainView(result: String) {
        binding.plainLayout.visibility = View.VISIBLE
        binding.plainText.text = result
    }

    private fun saveContact(result: String) {
        getVcardToOpen(result)?.let {
            val i = Intent()
            i.action = Intent.ACTION_VIEW
            i.setDataAndType(
                FileProvider.getUriForFile(
                    requireContext(),
                    "${requireContext().packageName}.provider", // Use app's package name
                    it
                ), "text/x-vcard"
            )
            startActivity(i)
        }
    }

    private fun getVcardToOpen(cardString: String): File? {
        val cacheFolder = File(context?.cacheDir, "qrscanner")
        var file: File? = null
        try {
            cacheFolder.mkdirs()
            file = File(cacheFolder, "card.vcf")
            val fw = FileWriter(file)
            fw.write(cardString)
            fw.close()
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
        return file
    }
}

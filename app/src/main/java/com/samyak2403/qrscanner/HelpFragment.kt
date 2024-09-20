/*
 * Created by Samyak Kamble on 9/19/24, 10:24 PM
 *  Copyright (c) 2024 . All rights reserved.
 *  Last modified 9/19/24, 10:24 PM
 */

package com.samyak2403.qrscanner


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class HelpFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false)
    }


}
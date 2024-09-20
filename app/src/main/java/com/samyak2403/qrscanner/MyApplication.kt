/*
 * Created by Samyak Kamble on 9/19/24, 10:23 PM
 *  Copyright (c) 2024 . All rights reserved.
 *  Last modified 9/19/24, 10:23 PM
 */

package com.samyak2403.qrscanner

import android.app.Application
import com.google.android.material.color.DynamicColors

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
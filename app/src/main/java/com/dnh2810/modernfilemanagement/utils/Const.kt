package com.dnh2810.modernfilemanagement.utils

import android.content.res.Resources

object Const {
    fun getDensity(): Float {
        return Resources.getSystem().displayMetrics.density
    }
    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }
    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}
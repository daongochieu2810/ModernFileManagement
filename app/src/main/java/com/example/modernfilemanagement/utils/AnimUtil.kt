package com.example.modernfilemanagement.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

object AnimUtil {

    fun scaleView(v: View, startScale: Float, endScale: Float) {
        val anim: Animation = ScaleAnimation(
            startScale, endScale,
            startScale, endScale,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        anim.fillAfter = false
        anim.duration = 200
        v.startAnimation(anim)
    }
}
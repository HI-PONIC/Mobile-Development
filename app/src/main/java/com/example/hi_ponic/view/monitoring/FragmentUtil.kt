package com.example.hi_ponic.view.monitoring

import android.view.View

object ViewHeightUtils {
    fun measureViewHeight(view: View): Int {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        return view.measuredHeight
    }
}
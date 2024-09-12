package com.example.kt_caclulator_app

import android.view.View
import android.view.ViewGroup

fun ViewGroup.findViewsWithTag(tag: String): List<View> {
    val views = mutableListOf<View>()
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        if (child is ViewGroup) {
            views.addAll(child.findViewsWithTag(tag))
        }
        if (tag == child.tag) {
            views.add(child)
        }
    }
    return views
}
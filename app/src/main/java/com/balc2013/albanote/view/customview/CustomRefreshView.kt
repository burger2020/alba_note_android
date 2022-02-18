package com.balc2013.albanote.view.customview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.balc2013.albanote.R

class CustomRefreshView(context: Context, var onRefresh: () -> Unit) : LinearLayout(context) {
    init {
        val inflaterService = Context.LAYOUT_INFLATER_SERVICE
        val inflater = context.getSystemService(inflaterService) as LayoutInflater
        val v = inflater.inflate(R.layout.custom_layout_refresh_view, this, false)
        addView(v)

        val refreshBtn = findViewById<View>(R.id.iv_refresh_btn)
        refreshBtn.setOnClickListener {
            onRefresh.invoke()
        }
    }
}
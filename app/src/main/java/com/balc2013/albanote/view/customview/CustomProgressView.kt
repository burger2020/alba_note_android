package com.balc2013.albanote.view.customview

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.balc2013.albanote.R

class CustomProgressView(context: Context) : LinearLayout(context) {
    init {
        val inflaterService = Context.LAYOUT_INFLATER_SERVICE
        val inflater = context.getSystemService(inflaterService) as LayoutInflater
        val v = inflater.inflate(R.layout.custom_layout_progress_view, this, false)
        // 터추 통과 제거
        v.setOnTouchListener { p0, p1 ->
            return@setOnTouchListener true
        }

//        Glide.with(this).load(R.drawable.loading).into(v.findViewById(R.id.iv_refresh_btn))
        addView(v)
    }
}
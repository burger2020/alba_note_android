package com.balc2013.albanote.etc.bindingadapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.balc2013.albanote.R


object LayoutBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["setLayoutTouch"])
    fun setLayoutTouch(view: View, bool: Boolean) {
        val color = ContextCompat.getColor(view.context, R.color.white)
        view.setBackgroundColor(color)
        setOnlyLayoutTouch(view, bool)
    }

    @JvmStatic
    @BindingAdapter(value = ["setOnlyLayoutTouch"])
    fun setOnlyLayoutTouch(view: View, bool: Boolean) {
        view.setOnTouchListener { p0, p1 ->
            return@setOnTouchListener true
        }
    }

//    @JvmStatic
//    @BindingAdapter("setLabelAfterEllipsis")
//    fun setLabelAfterEllipsis(textView: TextView, maxLines: Int) {
////        if (textView.layout == null || textView.layout.getEllipsisCount(maxLines - 1) == 0
////        ) {
////            return  // Nothing to do
////        }
//        val viewTreeObserver = ViewTreeObserver.OnDrawListener {
//            val start = textView.layout.getLineStart(0)
//            val end = textView.layout.getLineEnd(textView.lineCount - 1)
//            val displayed = textView.text.toString().substring(start, end)
//            val displayedWidth = getTextWidth(displayed, textView.textSize)
//            val strLabel = "더보기"
//            val ellipsis = "..."
//            val suffix = ellipsis + strLabel
//            var textWidth: Int
//            var newText = displayed
//            textWidth = getTextWidth(newText + suffix, textView.textSize)
//            while (textWidth > displayedWidth) {
//                newText = newText.substring(0, newText.length - 1).trim { it <= ' ' }
//                textWidth = getTextWidth(newText + suffix, textView.textSize)
//            }
//            textView.text = newText + suffix
//        }
//    }

//    private fun getTextWidth(text: String, textSize: Float): Int {
//        val bounds = Rect()
//        val paint = Paint()
//        paint.textSize = textSize
//        paint.getTextBounds(text, 0, text.length, bounds)
//        return ceil(bounds.width().toDouble()).toInt()
//    }
}
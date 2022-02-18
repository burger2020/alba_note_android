package com.balc2013.albanote.etc

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.*
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt

object Util {
    fun makeToast(context: Context, s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    //키보드 visible
    fun isToggleSoftKeyView(view: View, visible: Int) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        when (visible) {
            VISIBLE -> imm?.toggleSoftInput(SHOW_FORCED, HIDE_IMPLICIT_ONLY)
            INVISIBLE, GONE -> imm?.toggleSoftInput(HIDE_IMPLICIT_ONLY, 0)
        }
    }
    fun isSoftKeyView(view: View, visible:Int){
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        when(visible){
            VISIBLE -> imm?.showSoftInput(view, 1)
            GONE -> imm?.hideSoftInputFromWindow(view.windowToken, 0)
            INVISIBLE -> imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun getTrueTime(): Date {
        val format = "yyyy-MM-dd HH:mm:ss:SSS"
        val sdf = SimpleDateFormat(format)
        sdf.timeZone = TimeZone.getTimeZone("UNIX")
        val utcTime = sdf.format(Date())
        val dateFormat = SimpleDateFormat(format)
        val date = dateFormat.parse(utcTime)
        date.time -= 1000 * 60 * 60 * 3
        return date
    }

    fun Float?.roundValue() = ((this ?: 0f) * 10).roundToInt() / 10f

    //등록 시간
    fun regTime(regTime: Long): String {
        val trueTime = getTrueTime()
        val postTime = Calendar.getInstance()
        postTime.timeInMillis = regTime
        val nowTime = Calendar.getInstance()
        nowTime.timeInMillis = trueTime.time
        val diffTime = nowTime.timeInMillis - postTime.timeInMillis
        val difYear = abs(postTime[Calendar.YEAR] - nowTime[Calendar.YEAR])
        val difMonth = abs(postTime[Calendar.MONTH] - nowTime[Calendar.MONTH])
        val difDate = abs(postTime[Calendar.DATE] - nowTime[Calendar.DATE])
        val difHour = abs(postTime[Calendar.HOUR] - nowTime[Calendar.HOUR])
        val difMin = abs(postTime[Calendar.MINUTE] - nowTime[Calendar.MINUTE])
        return when {
            difYear >= 1 -> "${postTime.get(Calendar.YEAR) % 100}. ${postTime.get(Calendar.MONTH) + 1}. ${
                postTime.get(
                    Calendar.DAY_OF_MONTH
                )
            }"
            difDate > 10 || difMonth >= 1 ->
                "${postTime.get(Calendar.MONTH) + 1}월 ${postTime.get(Calendar.DAY_OF_MONTH)}일"
            difDate in 1..9 -> "${difDate}일 전"
            difHour in 1..23 -> "${abs(postTime[Calendar.HOUR] - nowTime[Calendar.HOUR])}시간 전"
            difMin in 1..59 -> "${abs(postTime[Calendar.MINUTE] - nowTime[Calendar.MINUTE])}분 전"
            difMin == 0 -> "${abs(postTime[Calendar.SECOND] - nowTime[Calendar.SECOND])}초 전"
            else -> "??"
        }
    }

    //문자 전체가 공백인지 확인
    fun checkBlankAll(t: String): Boolean = t == "" || t.matches(Regex("\\s+"))


    fun dpToPx(dp: Float, context: Context): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics
        )
    }
}
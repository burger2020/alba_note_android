package com.balc2013.albanote.etc

import android.content.Context
import android.content.SharedPreferences
import com.balc2013.albanote.domain.model.Member
import com.balc2013.albanote.domain.model.MemberTokenData
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

//todo 데이터 암호화 ㄲ
class LocalDBManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val pref: SharedPreferences =
        context.getSharedPreferences("setting", Context.MODE_PRIVATE)
    private val edit: SharedPreferences.Editor = pref.edit()
    private val gson = Gson()

    companion object {
        private val IS_SHOW_VERSION_UPDATE_DIALOG: (version: String) -> String =
            { "IS_SHOW_VERSION_UPDATE_DIALOG_${it}" }
        const val MY_INFO = "MY_INFO" //내 정보 오브젝트
        const val MY_MESSAGE_RECEIVE_STATUS = "MY_MESSAGE_RECEIVE_STATUS" //내 메시지 수신 상태 오브젝트
        private const val MY_TOKEN_INFO = "MY_TOKEN_INFO" //내 정보 오브젝트
        const val MY_PHOTO_SIGNATURE = "MY_PHOTO_SIGNATURE"

        private const val NOTIFICATION_VIEW_LAST_ACCESSED_DATE =
            "NOTIFICATION_VIEW_LAST_ACCESSED_DATE" // 알림 뷰 마지막 접속 시간


        private const val INIT_DATA_BY_SERVER = "INIT_DATA_BY_SERVER"
        private const val MY_CHATTING_ROOM_ID_LIST = "MY_CHATTING_ROOM_ID_LIST" // 내 참여 채팅방
        private val CHATTING_ROOM_NOTIFY: (id: Long) -> String =
            { "CHATTING_ROOM_NOTIFY${it}" } // 내 참여 채팅방

    }

    //todo 채팅 아이디 받는 방식 바꿔야할듯,, 요청 2번 ㄲ (채팅, 커뮤니티 서비스 가각)
//    fun setLogoutStatus() {
//        getChattingRoomIds().forEach {
//            edit.remove(CHATTING_ROOM_NOTIFY(it)).commit()
//        }
//        edit.remove(INIT_DATA_BY_SERVER)
//            .remove(MY_PHOTO_SIGNATURE + getMyId())
//            .remove(MY_INFO)
//            .remove(MY_TOKEN_INFO)
//            .remove(MY_CHATTING_ROOM_ID_LIST)
//            .commit()
//    }

    fun hasInitLocalData(): Boolean {
        return pref.getBoolean(INIT_DATA_BY_SERVER, false)
    }

        fun setMyInfo(myInfo: Member) {
        val json = gson.toJson(myInfo)
        edit.putString(MY_INFO, json).commit()
    }
//
//    fun setMyNickname(nickname: String) {
//        val myInfo = getMyInfo().also {
//            it!!.nickname = nickname
//        }
//        setMyInfo(myInfo!!)
//    }
//
//    fun setMyImageUrl(path: String?): Member {
//        val myInfo = getMyInfo().also {
//            it!!.imageUrl = path
//        }
//        setMyInfo(myInfo!!)
//        return myInfo
//    }
//
//    fun setMyAuthenticationInfo(date: Long) {
//        val myInfo = getMyInfo().also {
//            it!!.isAuthentication = true
//            it.authenticationDate = date
//        }
//        setMyInfo(myInfo!!)
//    }
//
    fun setMyToken(myTokenData: MemberTokenData) {
        val myToken = getMyTokenInfo() ?: myTokenData
        myToken.accessToken = myTokenData.accessToken
        myToken.refreshToken = myTokenData.refreshToken ?: myToken.refreshToken
        val tokenJson = gson.toJson(myToken)
        edit.putString(MY_TOKEN_INFO, tokenJson).commit()
    }

    //
//    fun setMyPhotoSignature() {
//        val id = getMyInfo()?.id ?: return
//        edit.putInt(MY_PHOTO_SIGNATURE + id, System.currentTimeMillis().toInt()).commit()
//    }
//
//    fun isShowVersionUpdateDialog(version: String): Boolean {
//        val isShowVersionUpdateDialog =
//            pref.getBoolean(IS_SHOW_VERSION_UPDATE_DIALOG(version), false)
//        if (!isShowVersionUpdateDialog) edit.putBoolean(
//            IS_SHOW_VERSION_UPDATE_DIALOG(version),
//            true
//        ).commit()
//        return isShowVersionUpdateDialog
//    }
//
//
//    fun getMyPhotoSignature() {
//        val id = getMyInfo()?.id ?: return
//        pref.getInt(MY_PHOTO_SIGNATURE + id, System.currentTimeMillis().toInt())
//    }
//
    fun getMyInfo(): Member? {
        val json = pref.getString(MY_INFO, null)
        return gson.fromJson(json, Member::class.java)
    }

    //
//    fun singOut() {
//        edit.remove(MY_INFO)
//    }
//
    fun getMyTokenInfo(): MemberTokenData? {
        val json = pref.getString(MY_TOKEN_INFO, null)
        return gson.fromJson(json, MemberTokenData::class.java)
    }

    //
    fun getMyUUID() = getMyInfo()?.uuid ?: ""
    fun getMyId() = getMyInfo()?.id
    fun getMyPwd() = getMyInfo()?.pwd

    //    fun getMyMessageReceiveStatus(): MemberTermsDTO = getMyInfo()!!.memberTerms
    fun getMyAccessToken(): String = "Bearer " + (getMyTokenInfo()?.accessToken ?: "")
    fun getMyRefreshToken(): String = "Bearer " + (getMyTokenInfo()?.refreshToken ?: "")
//
//
//    // 채팅방 이름 저장, 조회, 삭제
//    fun setChattingRoomIds(id: Long) {
//        val ids = getChattingRoomIds()
//        ids.remove(id)
//        ids.add(id)
//        val jsonArray = JSONArray()
//        for (i in 0 until ids.size) {
//            jsonArray.put(ids[i])
//        }
//        edit.putString(MY_CHATTING_ROOM_ID_LIST, jsonArray.toString()).commit()
//    }
//
//    fun setChattingRoomIds(id: List<Long>) {
//        val ids = getChattingRoomIds().toMutableSet()
//        ids.addAll(id)
//        val jsonArray = JSONArray()
//        for (i in 0 until ids.size) {
//            jsonArray.put(ids.toList()[i])
//        }
//        edit.putString(MY_CHATTING_ROOM_ID_LIST, jsonArray.toString()).commit()
//        edit.putBoolean(INIT_DATA_BY_SERVER, true).commit()
//    }
//
//    fun getChattingRoomIds(): MutableList<Long> {
//        val json = pref.getString(MY_CHATTING_ROOM_ID_LIST, null) ?: return mutableListOf()
//        val jsonArray = JSONArray(json)
//        val ids = mutableListOf<Long>()
//        for (i in 0 until jsonArray.length()) {
//            ids.add(jsonArray.optLong(i))
//        }
//        return ids
//    }
//
//    fun removeChattingRoomId(id: Long) {
//        val ids = getChattingRoomIds()
//        ids.remove(id)
//        val jsonArray = JSONArray()
//        for (i in 0 until ids.size) {
//            jsonArray.put(ids[i])
//        }
//        edit.putString(MY_CHATTING_ROOM_ID_LIST, jsonArray.toString()).commit()
//    }
//
//    fun setChattingRoomNotify(chattingRoomId: Long, bool: Boolean) {
//        edit.putBoolean(CHATTING_ROOM_NOTIFY(chattingRoomId), bool).commit()
//    }
//
//    fun isChattingRoomNotify(chattingRoomId: Long): Boolean {
//        return pref.getBoolean(CHATTING_ROOM_NOTIFY(chattingRoomId), true)
//    }
//
//    /** 알림 페이지 최근 접속 시간 체크**/
//    fun setNotificationViewLastAccessedDate() {
//        edit.putLong(NOTIFICATION_VIEW_LAST_ACCESSED_DATE, getCurrentTime()).commit()
//    }
//
//    fun getNotificationViewLastAccessedDate(): Long {
//        return pref.getLong(NOTIFICATION_VIEW_LAST_ACCESSED_DATE, 0)
//    }
}
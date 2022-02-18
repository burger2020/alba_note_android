package com.balc2013.albanote.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.balc2013.albanote.R
import com.balc2013.albanote.domain.model.fcm.FcmDataType
import com.balc2013.albanote.etc.LocalDBManager
import com.balc2013.albanote.repository.MemberRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class HitupFcmListenerService : FirebaseMessagingService() {

    @Inject
    lateinit var repository: MemberRepository

    @Inject
    lateinit var localDBManager: LocalDBManager

    companion object {
        const val FCM_BROADCAST = "FCM_BROADCAST"
        const val FCM_RESPONSE_JSON = "FCM_RESPONSE_JSON"
        const val FCM_RESPONSE_TYPE = "FCM_RESPONSE_TYPE"

        const val ACTIVITY_TO_ACTIVITY_BROADCAST = "ACTIVITY_TO_ACTIVITY_BROADCAST"
        const val TO_ALL_ACTIVITY_BROADCAST = "TO_ALL_ACTIVITY_BROADCAST"
    }

    // foreground fcm
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isNotEmpty()) {
            val dataType = remoteMessage.data["type"]

            when (FcmDataType.valueOf(dataType.toString())) {
//                FCM_NEW_CHATTING_ROOM ->
//                    broadcastMessage(FCM_NEW_CHATTING_ROOM, remoteMessage)
//                FCM_NEW_MESSAGE_BY_CHATTING_ROOM_ID ->
//                    broadcastMessage(FCM_NEW_MESSAGE_BY_CHATTING_ROOM_ID, remoteMessage)
//                FCM_NEW_POST_LIKE, FCM_NEW_REPLY_LIKE, FCM_NEW_NESTED_REPLY_LIKE, FCM_NEW_POST_REPLY, FCM_NEW_REPLY_NESTED_REPLY -> {
//                    broadcastMessage(FCM_NEW_MESSAGE_TO_LOG, null)
//                    val json = remoteMessage.data["object"].toString()
//                    val dto = FcmCommunityNewActionDTO(JSONObject(json))
//                    sendNotification(
//                        remoteMessage,
//                        (200000 + (dto.postId ?: 1L) * 1000 + (dto.replyId ?: 0L)).toInt()
//                    )
//                }
//                FCM_NEW_NOTICE, FCM_NEW_MARKETING_NOTICE -> {
//                    broadcastMessage(FCM_NEW_NOTICE, remoteMessage)
//                    sendNotification(remoteMessage, System.currentTimeMillis().toInt())
//                }
//                FCM_MEMBER_LEVEL_CHANGE -> {
//                    val info = localDBManager.getMyInfo() ?: return
//                    info.level = MemberLevelType.valueOf(
//                        remoteMessage.data["object"].toString().replace("\"", "")
//                    )
//                    localDBManager.setMyInfo(info)
//                    val intent = Intent(ACTIVITY_TO_ACTIVITY_BROADCAST)
//                    intent.putExtra(TO_ACTIVITY_RESULT_CODE, INTENT_RESULT_CODE_LEVEL_CHANGE)
//                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
//                }
//                FCM_MEMBER_BLIND -> {
//                    // 멤버 블라인드 상태 -> 팝업 및 종료
//                    val intent = Intent(TO_ALL_ACTIVITY_BROADCAST)
//                    intent.putExtra(TO_ACTIVITY_RESULT_CODE, INTENT_RESULT_CODE_BLIND)
//                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
//                    val intent2 = Intent(ACTIVITY_TO_ACTIVITY_BROADCAST)
//                    intent2.putExtra(TO_ACTIVITY_RESULT_CODE, INTENT_RESULT_CODE_BLIND)
//                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent2)
//                }
            }
        }
    }

    // 앱 실행 중 메시지 왓을 시
    private fun broadcastMessage(dataType: FcmDataType, message: RemoteMessage?) {
        val intent = Intent(FCM_BROADCAST)
        if (message != null) intent.putExtra(FCM_RESPONSE_JSON, message)
        intent.putExtra(FCM_RESPONSE_TYPE, dataType)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    // 푸시 띄우기
    @SuppressLint("CheckResult", "UseCompatLoadingForDrawables")
    private fun sendNotification(
        message: RemoteMessage,
        notifyId: Int = (System.currentTimeMillis() / 10000).toInt()
    ) {
        if (message.notification == null) return

        val channelId = "channel"

        val title = message.notification?.title
        val contents = message.notification?.body
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL)
        //펜딩 인텐트
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setColor(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_900))
            .setContentText(contents)
            .setAutoCancel(true)
            .setContentIntent(setPendingIntent(message.data))
//            .setLargeIcon(icon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setSound(defaultSoundUri)
            .setVibrate(longArrayOf(300, 300, 300))
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)

        val notifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val notifyManager = NotificationManagerCompat.from(this)

        // android O 버전부터는 채널 등록 필수
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "hitup_android"
            val channel = NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH)
            notifyManager.createNotificationChannel(channel)
        }

        notifyManager.notify(notifyId, notificationBuilder.build())
    }

    private fun setPendingIntent(data: MutableMap<String, String>): PendingIntent? {
        val resultIntent = getIntent(data)

        return TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent!!)

            PendingIntent.getActivity(
                applicationContext,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }

    // 타입에 맞는 인텐트 값 넣어서 반환
    // FcmDataProcessActivity.class -> setNotificationIntent() 에도 intent 작성
    private fun getIntent(data: MutableMap<String, String>): Intent? {
        val type = FcmDataType.valueOf(data["type"].toString())
        val obj = data["object"].toString()
        return when (type) {
//            FCM_NEW_MESSAGE_BY_CHATTING_ROOM_ID -> {
//                // 메시지 도착한 채팅방 이동
//                Intent(this, ChattingPageContainerView::class.java).also {
//                    val newMessageDTO = convertToObject(obj, FcmNewChattingMessageDTO::class.java)
//                    it.putExtra(INTENT_DATA_CHATTING_ROOM_ID, newMessageDTO.chattingRoomId)
//                    it.putExtra(INTENT_DATA_IS_BROADCAST_NOT_READ_COUNT_INIT, true)
//                }
//            }
//            // 게시물 좋아요 + 댓글 관련이면 댓글페이지로 이동
//            FCM_NEW_POST_LIKE, FCM_NEW_REPLY_LIKE, FCM_NEW_NESTED_REPLY_LIKE, FCM_NEW_POST_REPLY, FCM_NEW_REPLY_NESTED_REPLY -> {
//                Intent(this, CommunityPostMainView::class.java).also {
//                    val dto = convertToObject(obj, FcmCommunityNewActionDTO::class.java)
//                    it.putExtra(INTENT_DATA_POST_ID, dto.postId)
//                    it.putExtra(INTENT_DATA_REPLY_ID, dto.replyId)
//                }
//            }
//            FCM_NEW_NOTICE, FCM_NEW_MARKETING_NOTICE -> {
//                Intent(this, MyOptionView::class.java).also {
//                    val dto = convertToObject(obj, InformResponseDTO::class.java)
//                    it.putExtra(MyOptionView.INTENT_DATA_DETAIL_INFORM, dto)
//                    it.putExtra(INTENT_DATA_OPTION_TYPE, INFORM_VIEW)
//                }
//            }
            else -> null
        }
    }

    private fun getJsonStringFromMap(map: Map<String, Any>): String {
        val jsonObject = JSONObject()
        for ((key, value) in map) {
            jsonObject.put(key, value)
        }
        return jsonObject.toString()
    }

    fun <T> convertToObject(data: Map<String, String>, responseType: Class<T>): T {
        val mapper = ObjectMapper()
        return mapper.convertValue(data, responseType)
    }

    private fun <T> convertToObject(data: String, responseType: Class<T>): T {
        val mapper = ObjectMapper()
        return mapper.readValue(data, responseType)
    }

    //캐시 삭제 및 처음 시작 시 발급,
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        if (repository.accessToken != "Bearer ") {
            CoroutineScope(Dispatchers.IO).launch {
                repository.postFcmToken(token)
            }
        }
    }
}
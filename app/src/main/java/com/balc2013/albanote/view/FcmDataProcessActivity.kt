package com.balc2013.albanote.view

import com.balc2013.albanote.view.BaseActivity

abstract class FcmDataProcessActivity : BaseActivity() {
//    val vm: MainViewModel by viewModels()
//    val homeVm: HomeViewModel by viewModels()
//    val chattingVm: ChattingRoomListViewModel by viewModels()
//    val equipVm: EquipmentMainViewModel by viewModels()
//    val communityVm: CommunityMainViewModel by viewModels()
//    val myVm: MyMainViewModel by viewModels()
//
//    // fcm 메세지 클릭 후 전달된 데이터 처리
//    override fun setView() {
//        setDynamicLinkInit()
//        val bundle = intent.extras
//        if (bundle?.get("data") != null) {
//            foregroundNotificationEvent()
//        } else if (bundle != null) {
//            backgroundNotificationEvent(bundle)
//        }
//    }
//
//    /**
//     * 다이나믹 링크
//     * */
//    private var oldDeeplink = 0L
//    private fun setDynamicLinkInit() {
//        Firebase.dynamicLinks.getDynamicLink(intent)
//            .addOnSuccessListener { linkData: PendingDynamicLinkData? ->
//                if (linkData == null) return@addOnSuccessListener
//                // 같은 클릭 링크가 1초내로 요청 한 번 더 오거나 링크 클릭 시간이 현재보다 30초 이상 전 일 경우 return
//                if (oldDeeplink + 500 > linkData.clickTimestamp || linkData.clickTimestamp < GlobalApplication.getCurrentTime() - 1000 * 10) return@addOnSuccessListener
//                val deepLink: Uri? = linkData.link
//                Log.d("deep_query", "" + deepLink?.host) // www.hitup.com
//                Log.d("deep_query", "" + deepLink?.path) // /postDetail
//                Log.d("deep_query", "" + deepLink?.query) // id=1
//                Log.d("deep_query", "" + deepLink?.scheme) // https
//                when {
//                    // 게시물 공유
//                    deepLink.toString().contains(AppConfig.DEEP_LINK_PATH_POST_DETAIL) -> {
//                        val postId = deepLink!!.getQueryParameter(AppConfig.DEEP_LINK_QUERY_POST_ID)
//                        val intent = Intent(this, CommunityPostMainView::class.java)
//                        intent.putExtra(INTENT_DATA_POST_ID, postId?.toLong())
//                        startActivity(intent)
//                    }
//                    // 낚시터 날씨 공유
//                    deepLink.toString().contains(AppConfig.DEEP_LINK_PATH_FISHING_DETAIL) -> {
//                        val fishingId =
//                            deepLink!!.getQueryParameter(AppConfig.DEEP_LINK_QUERY_FISHING_ID)
//                        val intent = Intent(this, FishingPlaceDetailView::class.java)
//                        intent.putExtra(INTENT_DATA_SELECT_FISHING_PLACE_ID, fishingId)
//                        startActivity(intent)
//                    }
//                    // 대회 개설
//                    deepLink.toString().contains(AppConfig.DEEP_LINK_PATH_CONTEST_CREATE) -> {
//                        val linkId = deepLink!!.getQueryParameter(AppConfig.DEEP_LINK_QUERY_LINK_ID)
//                        val intent = Intent(this, ContestLinkView::class.java)
//                        intent.putExtra(INTENT_DATA_CONTEST_LINK_TYPE, CREATE_CONTEST)
//                        intent.putExtra(INTENT_DATA_CONTEST_LINK_ID, linkId)
//                        startActivity(intent)
//                    }
//                    // 대회 팀 초대
//                    deepLink.toString().contains(AppConfig.DEEP_LINK_PATH_CONTEST_TEAM_INVITE) -> {
//                        val linkId = deepLink!!.getQueryParameter(AppConfig.DEEP_LINK_QUERY_LINK_ID)
//                        val contestId = // 초대 팀 id
//                            deepLink.getQueryParameter(AppConfig.DEEP_LINK_QUERY_CONTEST_ID)
//                        val intent = Intent(this, ContestLinkView::class.java)
//                        intent.putExtra(INTENT_DATA_CONTEST_LINK_TYPE, TEAM_INVITE)
//                        intent.putExtra(INTENT_DATA_CONTEST_LINK_ID, linkId)
//                        intent.putExtra(INTENT_DATA_INVITE_CONTEST_ID, contestId)
//                        startActivity(intent)
//                    }
//                    // 대회 양도
//                    deepLink.toString().contains(AppConfig.DEEP_LINK_PATH_CONTEST_TRANSFER) -> {
//                        val linkId = deepLink!!.getQueryParameter(AppConfig.DEEP_LINK_QUERY_LINK_ID)
//                        val transferorId = // 양도인 id
//                            deepLink.getQueryParameter(DEEP_LINK_QUERY_TRANSFEROR_MEMBER_ID)
//                        val intent = Intent(this, ContestLinkView::class.java)
//                        intent.putExtra(INTENT_DATA_CONTEST_LINK_TYPE, APPLICATION_TRANSFER)
//                        intent.putExtra(INTENT_DATA_CONTEST_LINK_ID, linkId)
//                        intent.putExtra(INTENT_DATA_CONTEST_TRANSFEROR_ID, transferorId)
//                        startActivity(intent)
//                    }
//                    // 대회 정보 링크 공유
//                    deepLink.toString().contains(AppConfig.DEEP_LINK_PATH_CONTEST_APPLICATION) -> {
//                        val contestId =
//                            deepLink!!.getQueryParameter(AppConfig.DEEP_LINK_QUERY_CONTEST_ID)
//                        val intent = Intent(this, ContestInfoDetailView::class.java)
//                        intent.putExtra(INTENT_DATA_CONTEST_ID, contestId?.toLong())
//                        startActivity(intent)
//                    }
//                }
//                oldDeeplink = linkData.clickTimestamp
//            }.addOnFailureListener { e ->
//                Log.w("TAG", "getDynamicLink:onFailure", e)
//            }
//    }
//
//    /**
//     * foreground fcm 클릭 인텐트
//     * */
//    private fun foregroundNotificationEvent() {
//        val mapper = ObjectMapper()
//        val json = intent.extras?.getString("data") ?: return
//        val map = mapper.readValue(json, Map::class.javaObjectType)
//        setNotificationIntent(map)
//    }
//
//    /**
//     * background fcm 클릭 인텐트
//     * */
//    private fun backgroundNotificationEvent(bundle: Bundle) {
//        val keySet = mutableMapOf<String, String>()
//        bundle.keySet().forEach {
//            keySet[it] = bundle[it].toString()
//        }
//        setNotificationIntent(keySet)
//    }
//
//
//    /**
//     * fcm 클릭 인텐트
//     * */
//    // HitupFcmListenerService.class -> getIntent() 에도 intent 작성
//    private fun setNotificationIntent(map: Map<*, *>) {
//        if (map["type"] == null) return
//        val type = FcmDataType.valueOf(map["type"].toString())
//        val obj = map["object"].toString()
//
//        when (type) {
//            FCM_NEW_MESSAGE_BY_CHATTING_ROOM_ID -> { // 새로운 채팅 -> 해당 채팅방 이동
//                val newChattingMessage = convertToObject(obj, FcmNewChattingMessageDTO::class.java)
//                // 현재 채팅방일 경우 무시
//                if (newChattingMessage.chattingRoomId == chattingVm.currentConnectChattingRoomId) return
//                val intent = Intent(this, ChattingPageContainerView::class.java)
//                intent.putExtra(INTENT_DATA_CHATTING_ROOM_ID, newChattingMessage.chattingRoomId)
//                startActivity(intent)
//            }
//            // 게시물 좋아요 + 댓글 관련이면 댓글페이지로 이동
//            FCM_NEW_POST_LIKE, FCM_NEW_REPLY_LIKE, FCM_NEW_NESTED_REPLY_LIKE, FCM_NEW_POST_REPLY, FCM_NEW_REPLY_NESTED_REPLY -> {
//                val intent = Intent(this, CommunityPostMainView::class.java).also {
//                    val dto = convertToObject(obj, FcmCommunityNewActionDTO::class.java)
//                    it.putExtra(INTENT_DATA_POST_ID, dto.postId)
//                    it.putExtra(CommunityPostMainView.INTENT_DATA_REPLY_ID, dto.replyId)
//                }
//                startActivity(intent)
//            }
//            // 공지 페이지 이동
//            FCM_NEW_MARKETING_NOTICE, FCM_NEW_NOTICE -> {
//                val intent = Intent(this, MyOptionView::class.java).also {
//                    val dto = convertToObject(obj, InformResponseDTO::class.java)
//                    it.putExtra(INTENT_DATA_DETAIL_INFORM, dto)
//                    it.putExtra(INTENT_DATA_OPTION_TYPE, INFORM_VIEW)
//                }
//                startActivity(intent)
//            }
//            else -> {
//            }
//        }
//    }
//
//    private fun <T> convertToObject(data: String, responseType: Class<T>): T {
//        val mapper = ObjectMapper()
//        return mapper.readValue(data, responseType)
//    }
//
//    private val mMessageReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            if (intent != null) {
//                val type = intent.getSerializableExtra(FCM_RESPONSE_TYPE) as FcmDataType
//                // 새로운 알림 (쌓이는 알림) 표시
//                if (type == FCM_NEW_MESSAGE_TO_LOG) vm.hasUnreadNotification.value = true
//
//                val remoteMessage =
//                    intent.getParcelableExtra<RemoteMessage>(FCM_RESPONSE_JSON) ?: return
//                val data = remoteMessage.data["object"].toString()
//                when (type) {
//                    FCM_NEW_CHATTING_ROOM -> {
//                        // 새로운 채팅방 생성 및 참여됨
//                        val message = FcmNewChattingMessageDTO(JSONObject(data))
//                        vm.addChattingRoomList(message.chattingRoomId)
//                        chattingVm.setNewMessageWhenDisconnect(message)
//                    }
//                    FCM_NEW_MESSAGE_BY_CHATTING_ROOM_ID -> {
//                        // 해당 채팅방의 새로운 메시지 수신
//                        val message = FcmNewChattingMessageDTO(JSONObject(data))
//                        chattingVm.setNewMessageWhenDisconnect(message)
//                        // 접속중인 채팅방이 아니고 내가 보낸 메시지가 아니고 수신 동의 상태일 경우 노티 생성
//                        if (message.chattingRoomId != chattingVm.currentConnectChattingRoomId && message.newMessage.sendMemberId != vm.myInfo?.id && vm.myInfo?.memberTerms?.chattingNotify == true) {
//                            val notifyId =
//                                AppConfig.notification_chatting_room_notify_id(message.chattingRoomId.toInt())
//                            sendNotification(remoteMessage, notifyId)
//                        }
//                    }
//                    FCM_NEW_NOTICE, FCM_NEW_MARKETING_NOTICE -> {
//                        try {
//                            vm.hasUnreadNotification.value = true
//                            // 새로운 공지 -> 홈화면 공지 사항 최신화
//                            val inform = InformResponseDTO(JSONObject(data))
//                            homeVm.newInform.value = inform
//                        } catch (e: Exception) {
//
//                        }
//                    }
//                    else -> {
//                    }
//                }
//            }
//        }
//    }
//
//    private val mActivityToActivityPassingData = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            if (intent != null) {
//                when (intent.getIntExtra(TO_ACTIVITY_RESULT_CODE, -1)) {
//                    INTENT_RESULT_INFORM_READ -> {
//                        vm.hasUnreadInform.value = false
//                    }
//                    INTENT_RESULT_CODE_CHATTING_ROOM -> {
//                        // 채팅방 접속 종료
//                        val chattingRoomId =
//                            intent.getLongExtra(INTENT_RESULT_DATA_CHATTING_ROOM_ID, -1)
//                        val content = intent.getStringExtra(INTENT_RESULT_DATA_LAST_MESSAGE_CONTENT)
//                        val createdDate =
//                            intent.getLongExtra(INTENT_RESULT_DATA_LAST_MESSAGE_CREATED_DATE, -1)
//                        val lastMessage =
//                            if (content == null || createdDate == -1L) null
//                            else ChattingRoomLastMessageDTO(content, createdDate)
//                        chattingVm.setResultForFcmClickChattingRoom(chattingRoomId, lastMessage)
//                    }
//                    INTENT_RESULT_CODE_LEAVE_CHATTING_ROOM -> {
//                        // 채팅방 나감
//                        val chattingRoomId =
//                            intent.getLongExtra(INTENT_DATA_LEAVE_CHATTING_ROOM_ID, -1)
//                        chattingVm.setRemoveChattingRoomForActivityResult(chattingRoomId)
//                    }
//                    INTENT_RESULT_CODE_CHANGE_CHATTING_ROOM_ID -> {
//                        // 채팅방 입장 -> 현재 접속중인 채팅방 id 변경
//                        val chattingRoomId = intent.getLongExtra(INTENT_DATA_CHATTING_ROOM_ID, -1)
//                        chattingVm.currentConnectChattingRoomId = chattingRoomId
//                    }
//                    INTENT_RESULT_CODE_CHANGE_CHATTING_MESSAGE_RECEIVE_STATUS -> {
//                        // 채팅 알림 수신 상태 변경
//                        val status = intent.getBooleanExtra(
//                            INTENT_DATA_CHATTING_MESSAGE_RECEIVE_STATUS, false
//                        )
//                        vm.myInfo?.memberTerms?.chattingNotify = status
//                    }
//                    INTENT_RESULT_CODE_EXPULSION_CHATTING_ROOM -> {
//                        // 채팅방 추방됨
//                        val chattingRoomId = intent.getLongExtra(INTENT_DATA_CHATTING_ROOM_ID, -1)
//                        chattingVm.removeChattingRoomIdToLocal(chattingRoomId)
//                    }
//                    INTENT_RESULT_CODE_JOIN_NEW_CHATTING_ROOM -> {
//                        val chattingRoomId = intent.getLongExtra(INTENT_DATA_CHATTING_ROOM_ID, -1)
//                        chattingVm.getChattingRoomsByChattingRoomIds(listOf(chattingRoomId))
//                    }
//                    INTENT_RESULT_CODE_LEVEL_CHANGE -> {
//                        //멤버 레벨 변경
//                        equipVm.isLevelChange.value = true
//                        myVm.isLevelChange.value = true
//                    }
//                    INTENT_RESULT_CODE_BLIND -> {
//                        vm.localDb.setLogoutStatus()
//                    }
//                }
//            }
//        }
//    }
//
//
//    // 푸시 띄우기
//    @SuppressLint("CheckResult", "UseCompatLoadingForDrawables")
//    fun sendNotification(
//        message: RemoteMessage,
//        notifyId: Int = (System.currentTimeMillis() / 10000).toInt()
//    ) {
//        val title = message.notification?.title
//        val contents = message.notification?.body
//        val channelId = "channel"
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL)
//
//        //펜딩 인텐트
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.ic_stat_top_app_icon)
//            .setContentTitle(title)
//            .setColor(ContextCompat.getColor(this, R.color.color_hitup_blue))
//            .setContentText(contents)
//            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
//            .setAutoCancel(true)
//            .setContentIntent(setPendingIntent(message.data))
////            .setLargeIcon(icon)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
////            .setSound(defaultSoundUri)
//            .setVibrate(longArrayOf(300, 300, 300))
//            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
//        val notifyManager = NotificationManagerCompat.from(this)
//
//        // android O 버전부터는 채널 등록 필수
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "hitup_android"
//            val channel = NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH)
//            notifyManager.createNotificationChannel(channel)
//        }
//
//        notifyManager.notify(notifyId, notificationBuilder.build())
//    }
//
//    private fun setPendingIntent(data: MutableMap<String, String>): PendingIntent? {
//        val resultIntent = getIntent(data)
//
//        return TaskStackBuilder.create(this).run {
//            addNextIntentWithParentStack(resultIntent!!)
//
//            PendingIntent.getActivity(
//                applicationContext,
//                0,
//                resultIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//            )
//        }
//    }
//
//    // 타입에 맞는 인텐트 값 넣어서 반환
//    private fun getIntent(data: MutableMap<String, String>): Intent? {
//        val type = FcmDataType.valueOf(data["type"].toString())
//        val obj = data["object"].toString()
//        return when (type) {
//            FCM_NEW_MESSAGE_BY_CHATTING_ROOM_ID -> { // 메시지 도착한 채팅방 이동
//                val newMessageDTO = convertToObject(obj, FcmNewChattingMessageDTO::class.java)
//                if (newMessageDTO.chattingRoomId == chattingVm.currentConnectChattingRoomId) return null
//                Intent(this, ChattingPageContainerView::class.java).also {
//                    it.putExtra(INTENT_DATA_CHATTING_ROOM_ID, newMessageDTO.chattingRoomId)
//                    it.putExtra(
//                        ChattingPageContainerView.INTENT_DATA_IS_BROADCAST_NOT_READ_COUNT_INIT,
//                        true
//                    )
//                }
//            }
//            else -> null
//        }
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mActivityToActivityPassingData)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mActivityToActivityPassingData)
//        LocalBroadcastManager.getInstance(this).registerReceiver(
//            mMessageReceiver, IntentFilter(FCM_BROADCAST)
//        )
//        LocalBroadcastManager.getInstance(this).registerReceiver(
//            mActivityToActivityPassingData, IntentFilter(ACTIVITY_TO_ACTIVITY_BROADCAST)
//        )
//    }

    companion object {
        const val TO_ACTIVITY_RESULT_CODE = "BROADCAST_TO_ACTIVITY_RESULT_CODE"
    }
}
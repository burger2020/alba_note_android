package com.balc2013.albanote.domain.model.fcm
enum class FcmDataType {
    // member-service
    // 새로운 공지, 새로운 마케팅 공지, 멤버 레벨 변경
    FCM_NEW_NOTICE, FCM_NEW_MARKETING_NOTICE, FCM_MEMBER_LEVEL_CHANGE, FCM_MEMBER_BLIND,

    // community-service
    // 내 게시물에 좋아요
    FCM_NEW_POST_LIKE,

    // reply-service
    // 내 댓글에 좋아요, 내 대댓글에 좋아요, 내 게시물에 댓글, 내 게시물에 대댓글, 내 댓글에 대댓글
    FCM_NEW_REPLY_LIKE, FCM_NEW_NESTED_REPLY_LIKE, FCM_NEW_POST_REPLY, FCM_NEW_REPLY_NESTED_REPLY,

    // chatting-service
    // 새로운 채팅방 개설됨(push x), 채팅방에 새로운 메세지, ???
    FCM_NEW_CHATTING_ROOM, FCM_NEW_MESSAGE_BY_CHATTING_ROOM_ID, FCM_CHATTING_ROOM_NOT_READ_COUNT_INIT,

    // 알림 쌓이는 메세지 -> 알림창 버튼 알림 옴 표시
    FCM_NEW_MESSAGE_TO_LOG,

    NEW_MESSAGE_BY_CHATTING_ROOM_ID
}
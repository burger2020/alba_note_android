package com.balc2013.albanote.etc

object AppConfig {
    const val SERVER_URL = "http://192.168.0.13:8000/" // my local
    const val WS_SERVER_URL = "ws://192.168.0.13:8000/chatting-service/websocket/hitup"

    // api 실패 시 재시도 텀
    const val API_RECONNECT_TIME = 300L
}
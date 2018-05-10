package com.townwang.town.daemon

import android.annotation.TargetApi
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

/**
 * Description:   方法5：粘性服务与捆绑进程
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class NotificationLiveService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {}

    override fun onNotificationRemoved(sbn: StatusBarNotification) {}
}
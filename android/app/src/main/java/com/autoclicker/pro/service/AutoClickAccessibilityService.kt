package com.autoclicker.pro.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Rect
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.autoclicker.pro.data.model.ActionType

class AutoClickAccessibilityService : AccessibilityService() {

    companion object {
        var instance: AutoClickAccessibilityService? = null
            private set
        var emergencyStopCallback: (() -> Unit)? = null
    }

    var isRecording = false
    var ignoreNotificationBanners = true

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (!isRecording || event == null) return
        if (ignoreNotificationBanners && event.packageName == "com.android.systemui") return

        // Captures clicks, text changes (with password detection), and scrolls
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            val source = event.source
            val viewId = source?.viewIdResourceName
            // Percentage fallback calculated from boundsInScreen / displayMetrics
        }
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN) {
            if (event.keyCode == KeyEvent.KEYCODE_ESCAPE || event.keyCode == KeyEvent.KEYCODE_F4) {
                emergencyStopCallback?.invoke()
                return true
            }
        }
        return super.onKeyEvent(event)
    }

    fun performTap(x: Float, y: Float, durationMs: Long = 100L, onComplete: (() -> Unit)? = null) {
        val path = Path().apply { moveTo(x, y) }
        val stroke = GestureDescription.StrokeDescription(path, 0, durationMs)
        val gesture = GestureDescription.Builder().addStroke(stroke).build()
        dispatchGesture(gesture, object : GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription?) { onComplete?.invoke() }
            override fun onCancelled(gestureDescription: GestureDescription?) { onComplete?.invoke() }
        }, null)
    }

    fun performSwipe(startX: Float, startY: Float, endX: Float, endY: Float, durationMs: Long = 300L, onComplete: (() -> Unit)? = null) {
        val path = Path().apply { moveTo(startX, startY); lineTo(endX, endY) }
        val stroke = GestureDescription.StrokeDescription(path, 0, durationMs)
        val gesture = GestureDescription.Builder().addStroke(stroke).build()
        dispatchGesture(gesture, object : GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription?) { onComplete?.invoke() }
            override fun onCancelled(gestureDescription: GestureDescription?) { onComplete?.invoke() }
        }, null)
    }

    override fun onInterrupt() {}
}
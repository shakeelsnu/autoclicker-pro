package com.autoclicker.pro.service

import android.app.Service
import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Rect
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView

class FloatingOverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var layoutParams: WindowManager.LayoutParams
    private var overlayView: FrameLayout? = null

    override fun onBind(intent: android.content.Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else WindowManager.LayoutParams.TYPE_PHONE

        layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 40
            y = 200
        }
    }

    /**
     * Auto-Evasion: checks if next action touches overlay, flips screen side
     */
    fun evadeTouchIfNeeded(touchX: Float, touchY: Float) {
        val view = overlayView ?: return
        val rect = Rect(layoutParams.x, layoutParams.y, layoutParams.x + view.width, layoutParams.y + view.height)
        rect.inset(-40, -40)
        if (rect.contains(touchX.toInt(), touchY.toInt())) {
            val screenWidth = resources.displayMetrics.widthPixels
            layoutParams.x = if (layoutParams.x < screenWidth / 2) screenWidth - view.width - 40 else 40
            windowManager.updateViewLayout(overlayView, layoutParams)
        }
    }
}
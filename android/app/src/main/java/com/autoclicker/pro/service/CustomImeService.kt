package com.autoclicker.pro.service

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent

class CustomImeService : InputMethodService() {

    companion object {
        var instance: CustomImeService? = null
            private set

        fun injectSpecialKey(keyCode: Int, modifiers: Int = 0) {
            val ic = instance?.currentInputConnection ?: return
            ic.sendKeyEvent(KeyEvent(0, 0, KeyEvent.ACTION_DOWN, keyCode, 0, modifiers))
            ic.sendKeyEvent(KeyEvent(0, 0, KeyEvent.ACTION_UP, keyCode, 0, modifiers))
        }

        fun injectText(text: String) {
            instance?.currentInputConnection?.commitText(text, 1)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}
package com.townwang.town.daemon

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager

import com.townwang.town.R

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/4/13 13:57
 * Version 1.0
 * Params:
 * Description:  开启一个像素的Activity
 */
class SinglePixelActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_singlepixel)
        val window = window
        //放在左上角
        window.setGravity(Gravity.START or Gravity.TOP)
        val attributes = window.attributes
        //宽高设计为1个像素
        attributes.width = 1
        attributes.height = 1
        //起始坐标
        attributes.x = 0
        attributes.y = 0
        window.attributes = attributes

        ScreenManager.getInstance(this).setActivity(this)
    }

    companion object {

        val TAG = SinglePixelActivity::class.java.simpleName

        fun actionToSinglePixelActivity(pContext: Context) {
            val intent = Intent(pContext, SinglePixelActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            pContext.startActivity(intent)
        }
    }
}
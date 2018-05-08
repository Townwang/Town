package com.townwang.town.mvp.widget

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.townwang.town.R

/**
 * @author Town
 * @created at 2018/5/3 15:50
 * @Last Modified by: Town
 * @Last Modified time: 2018/5/3 15:50
 * @Remarks 自定义加载弹框
 */

object LoadingView {
    private var isShowLiding = false
    /**
     * 创建加载中弹框
     */
    fun showLoading(context: Context, msg: String): Dialog? {
            val inflater = LayoutInflater.from(context)
            val v = inflater.inflate(R.layout.dialog_loading, null)// 得到加载view
            val layout = v.findViewById<LinearLayout>(R.id.dialog_loading_view)// 加载布局
            val tipTextView = v.findViewById<TextView>(R.id.tipTextView)// 提示文字
            tipTextView.text = msg// 设置加载信息
          return  createDialog(layout,context)
    }

    /**
     * 关闭dialog
     */
    fun closeDialog(mDialogUtils: Dialog?) {
        if (mDialogUtils != null && isShowLiding) {
            mDialogUtils.dismiss()
            isShowLiding = false
        }
    }

    private fun createDialog(view: View,context: Context):Dialog?{
        if (!isShowLiding) {
            val loadingDialog = Dialog(context, R.style.MyDialogStyle)// 创建自定义样式dialog
            loadingDialog.setCancelable(true) // 是否可以按“返回键”消失
            loadingDialog.setCanceledOnTouchOutside(false) // 点击加载框以外的区域
            loadingDialog.setContentView(view, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT))// 设置布局
            //封装Dialog方法
            val window = loadingDialog.window
            val lp = window.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.setGravity(Gravity.CENTER)
            window.attributes = lp
            window.setWindowAnimations(R.style.PopWindowAnimStyle)
            loadingDialog.show()
            isShowLiding = true
            return loadingDialog
        }else{
            isShowLiding = false
            return null
        }
    }
}

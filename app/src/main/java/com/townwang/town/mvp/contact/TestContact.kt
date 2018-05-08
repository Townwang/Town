package com.townwang.town.mvp.contact

import com.townwang.town.base.BasePresenter
import com.townwang.town.base.BaseView

/**
 * @author Town
 * @created at 2018/4/4 17:19
 * @Last Modified by: Town
 * @Last Modified time: 2018/4/4 17:19
 * @Remarks  Contact
 */

interface TestContact {
    /**
     * 视图接口
     */
    interface view:BaseView{
        /**
         * 设置文字
         */
        fun setTest(s: String)
    }

    /**
     * 逻辑接口
     */
    interface presenter:BasePresenter {
        /**
         * 得到一些东东
         */
        fun getViewText()
    }
}

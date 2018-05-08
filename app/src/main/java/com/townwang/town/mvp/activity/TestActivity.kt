package com.townwang.town.mvp.activity

import android.os.Bundle
import android.view.View

import com.townwang.town.R
import com.townwang.town.base.baseImpl.BaseActivity
import com.townwang.town.mvp.contact.TestContact
import com.townwang.town.mvp.presenter.TestPresenter
import kotlinx.android.synthetic.main.test_main.*

/**
 * @author Town
 * @created at 2018/5/7 12:01
 * @Last Modified by: Town
 * @Last Modified time: 2018/5/7 12:01
 * @Remarks 测试视图
 */

class TestActivity : BaseActivity<TestContact.presenter>(), TestContact.view, View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn -> presenter.getViewText()
        }
    }

    override fun setTest(s: String) {
        text_view.text = s
    }

    override fun initPresenter(): TestContact.presenter {
        return TestPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_main)
        btn.setOnClickListener(this)
    }


}

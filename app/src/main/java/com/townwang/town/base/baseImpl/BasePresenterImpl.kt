package com.townwang.town.base.baseImpl

import com.townwang.town.base.BasePresenter
import com.townwang.town.base.BaseView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author Town
 * @created at 2018/5/7 15:12
 * @Last Modified by: Town
 * @Last Modified time: 2018/5/7 15:12
 * @Remarks 逻辑基类
 */
abstract class BasePresenterImpl<V : BaseView>(protected var view: V?) : BasePresenter {

    override fun detach() {
        this.view = null
        unDisposable()
    }

    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private var mCompositeDisposable: CompositeDisposable? = null
    /**
     * 将Disposable添加 {订阅}
     *
     */
    override fun addDisposable(subscription: Disposable) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable?.isDisposed !!) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(subscription)
    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    override fun unDisposable() {
        mCompositeDisposable?.dispose()
    }


}
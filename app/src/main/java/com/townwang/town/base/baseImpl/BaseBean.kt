package com.townwang.town.base.baseImpl

import java.io.Serializable

/**
 * @author Town
 * @created at 2018/3/8 12:00
 * @Last Modified by: Town
 * @Last Modified time: 2018/3/8 12:00
 * @Remarks  TODO 请求结果基础bean；仅用于判断操作是否成功
 */
//{"code":"1000",
// "msg":"成功",
// "data":
// {"name":town,
// "blog":"townwang.com",
// "wechat":"wechatTown",
// "email":"android@towmnwang.com"}}
open class BaseBean<T>(var code:String,var msg:String,var data:T) : Serializable
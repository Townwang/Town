package com.townwang.town.retroft

/**
 * @author Town
 * @created at 2018/3/8 12:00
 * @Last Modified by: Town
 * @Last Modified time: 2018/3/8 12:00
 * @Remarks  TODO 自定义exception
 */
class ApiException(message: String) : RuntimeException(Throwable(message))

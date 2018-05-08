package com.townwang.town.utils

import android.text.TextUtils

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * @author Town
 * @created at 2017/12/15 10:34
 * @Last Modified by: Town
 * @Last Modified time: 2017/12/15 10:34
 * @Remarks Log统一管理类
 */
object Log {
    //是否需要打印bug  true 打印 fasle 不打印
    var isDebug = true
    //打印Tag
    private val TAG = "Town====>"

    private val SUBSTRINGLENG = 4
    private val DELDATE = "}\r\n"
    private var currentTime: Long = 0 //打印耗时时间
    private var parseResponse: JSONObject? = null//最终被解析的JSON对象
    private var isdelCb: Boolean = false//是否删除多余的JSONObject(默认的JSONArry会被嵌套了一个JSONObject)
    private val josnBuilder = StringBuilder()//最终输出的数据容器

    // 下面四个是默认tag的函数
    fun i(msg: String) {
        if (isDebug)
            android.util.Log.i(TAG, msg)
    }

    fun d(msg: String) {
        if (isDebug)
            android.util.Log.d(TAG, msg)
    }

    fun e(msg: String) {
        if (isDebug)
            android.util.Log.e(TAG, msg)
    }

    fun v(msg: String) {
        if (isDebug)
            android.util.Log.v(TAG, msg)
    }

    // 下面是传入自定义tag的函数
    fun i(tag: String, msg: String) {
        if (isDebug)
            android.util.Log.i(tag, msg)
    }

    fun d(tag: String, msg: String) {
        if (isDebug)
            android.util.Log.i(tag, msg)
    }

    fun e(tag: String, msg: String) {
        if (isDebug)
            android.util.Log.i(tag, msg)
    }

    fun v(tag: String, msg: String) {
        if (isDebug)
            android.util.Log.i(tag, msg)
    }

    /**
     * Json格式化输出
     *
     * @param tag 标签
     * @param message 内容
     */
    fun json(tag: String, message: String) {
        var myJsonObject: JSONObject? = null
        try {
            myJsonObject = JSONObject(message)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        if (isDebug)
            outputFormatJson(tag, myJsonObject)
    }

    /***
     * 功能：线程同步,输出日志
     * @param response  必传
     * @param TagName 输出台标签名：必传。
     */
    fun outputFormatJson(TagName: String, response: Any?) {
        synchronized(Log::class.java) {
            try {
                currentTime = System.currentTimeMillis()
                if (response is JSONArray) {
                    isdelCb = true
                    val tempJson = JSONObject()
                    parseResponse = tempJson.putOpt("", response)
                } else {
                    isdelCb = false
                    parseResponse = response as JSONObject?
                }
                synchronized(Log::class.java) {
                    outputFormatJson(TagName, parseResponse, isdelCb)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
    }

    /***
     * 功能：
     */
    private fun outputFormatJson(TagName: String, response: JSONObject?, isdelCb: Boolean) {
        startaparse(TagName, response, isdelCb)
    }

    /**
     * 功能 ：添加外层花括号
     *
     * @param isdelCb :对JSONArry去掉外层手动添加的JSONObject
     */
    private fun startaparse(TagName: String, response: JSONObject?, isdelCb: Boolean) {
        if (checkParams(TagName, response)) {
            return
        }
        josnBuilder.setLength(0)
        appendSb("=======================================$TagName===========================================\n\n\n", false)
        appendSb("{", false)
        prinfrmatJson(TagName, response!!)
        appendSb("}", false)
        appendSb("\n\n\n=====================================" + " 耗时" + (System.currentTimeMillis() - currentTime) + "毫秒======================================", false)
        if (isdelCb) {
            josnBuilder.delete(josnBuilder.indexOf("{"), josnBuilder.indexOf("["))
            josnBuilder.delete(josnBuilder.lastIndexOf(DELDATE), josnBuilder.lastIndexOf(DELDATE) + SUBSTRINGLENG)
        }
        logOut(TagName, josnBuilder.toString())
    }

    /**
     * 功能：检查参数是否异常：
     *
     * @param TagName  必填
     * @param response 必填
     */
    private fun checkParams(TagName: String, response: JSONObject?): Boolean {
        return null == response || TextUtils.isEmpty(response.toString()) || TextUtils.isEmpty(TagName)
    }

    /**
     * 功能：遍历所有子json对象,并对孩子进行递归操作
     * 对JSONobject,JSONArray,Object。进行区分判断。
     */
    private fun prinfrmatJson(TagName: String, response: JSONObject) {
        try {
            val jsonobject = response.keys()
            while (jsonobject.hasNext()) {

                val key = jsonobject.next()

                if (response.get(key) is JSONObject) {

                    appendSb("\"" + key + "\"" + ":{", false)

                    prinfrmatJson(TagName, response.get(key) as JSONObject)

                    val isendValue = jsonobject.hasNext()//判断是否还有下一个元素

                    appendSb("  }", isendValue)

                } else if (response.get(key) is JSONArray) {

                    appendSb("\"" + key + "\"" + ":[", false)

                    itemrArray(TagName, response.get(key) as JSONArray)

                    val isendValue = jsonobject.hasNext()//判断是否还有下一个元素

                    appendSb(" " + " ]", isendValue)

                } else if (response.get(key) is Any) {

                    val isendValue = jsonobject.hasNext()//判断是否还有下一个元素
                    getTypeData(response, key, !isendValue)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    /**
     * 功能：对基本类型进行转换（String,其他的类型其实不用处理）
     * 说明：对null,进行非空处理，对字符串进行添加 "" 操作
     */
    private fun getTypeData(response: JSONObject, key: String, isEndValue: Boolean) {
        try {
            if (response.get(key) is Int) {
                val value = response.get(key) as Int
                appendSb("\t" + "\"" + key + "\"" + ":" + value + "", !isEndValue)

            } else if (response.get(key) is String || null == response.get(key) || TextUtils.equals("null", response.get(key).toString())) {

                if (response.get(key) is String) {

                    val value = response.get(key) as String
                    appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue)
                } else if (TextUtils.equals("null", response.get(key).toString())) {
                    appendSb("\t" + "\"" + key + "\"" + ":" + null, !isEndValue)

                } else {
                    val value = response.get(key) as String
                    appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue)
                }
            } else if (response.get(key) is Float) {
                val value = response.get(key) as Float

                appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue)

            } else if (response.get(key) is Double) {

                val value = response.get(key) as Double

                appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue)

            } else if (response.get(key) is Boolean) {

                val value = response.get(key) as Boolean

                appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue)

            } else if (response.get(key) is Char) {

                val value = response.get(key) as Char

                appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue)

            } else if (response.get(key) is Long) {

                val value = response.get(key) as Long

                appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (es: Exception) {
            es.printStackTrace()
        }

    }

    /**
     * 功能：对JSONArray进行遍历
     *
     * @param TagName
     * @param response;
     */
    private fun itemrArray(TagName: String, response: JSONArray) {
        try {
            for (i in 0 until response.length()) {
                if (response.get(i) is JSONObject) {
                    appendSb("{", false)
                    prinfrmatJson(TagName, response.get(i) as JSONObject)
                    appendSb("  }", response.length() > i + 1)

                } else if (response.get(i) is JSONArray) {
                    itemrArray(TagName, response.get(i) as JSONArray)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    /**
     * 功能：添加数据，以及逗号，换行。
     *
     * @param addComma 逗号。
     */
    private fun appendSb(append: String, addComma: Boolean) {
        josnBuilder.append(append)
        if (addComma) {
            josnBuilder.append(",")
        }
        josnBuilder.append("\r\n")
    }

    /**
     * 功能： LOG输出长度有限制。（需分层输出）
     *
     * @param tag：
     * @param content
     * @parac max:通过测试不建议修改数据值，修改成4000,会丢失数据。
     */
    private fun logOut(tag: String, content: String) {
        @Suppress("NAME_SHADOWING")
        var content = content
        val max = 3900
        val length = content.length.toLong()
        if (length < max || length == max.toLong()) {
            android.util.Log.i(TAG + tag, content)
        } else {
            while (content.length > max) {
                val logContent = content.substring(0, max)
                content = content.replace(logContent, "")
                android.util.Log.i(TAG + tag, logContent)
            }
            android.util.Log.i(TAG + tag, content)
        }
    }
}
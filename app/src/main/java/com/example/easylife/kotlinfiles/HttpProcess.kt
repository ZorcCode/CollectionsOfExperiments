package com.example.easylife.kotlinfiles

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class HttpProcess(private var url: String) {

    fun makeRequest(callback: Callback,params: String?,method: String){
         val parameters = params?.let { JSONObject(it) }
        Thread {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build()
            val requestBody = parameters.toString().toRequestBody()
            val request = Request.Builder()
                .method(method, requestBody)
                .header("Content-Type","application/json")
                .url(url)
                .build()
            okHttpClient.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    var err = e
                    if (Objects.requireNonNull(e.message).equals("Chain validation failed",
                            ignoreCase = true))
                        err = IOException("Please set correct system date and try again!")
                    callback.onError(err)
                }
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val body = response.body
                        if (body != null) {
                            try {
                                callback.onResponse(body.string())
                            } catch (e: JSONException) {
                                callback.onError(IOException("Invalid response format! " + e.message))
                            }
                        } else {
                            callback.onError(IOException("Null body"))
                        }
                    } else {
                        callback.onError(IOException("Server Error! " + response.code))
                    }
                }
            })
        }.start()
    }


//    fun post(
//        callback: Callback,
//        params: Map<String?, String?>?,
//        files: Map<String?, Tuple<String, File?, CountingFileRequestBody.ProgressListener?>>?
//    ) {
//        val builder: Builder = Builder().setType(MultipartBody.FORM)
//        if (params != null) {
//            for ((key, value) in params) {
//                builder.addFormDataPart(key, value)
//            }
//        }
//        if (files != null) {
//            for ((key, value) in files) {
//                builder.addFormDataPart(
//                    key, value.a,
//                    CountingFileRequestBody(value.b, getMimeType(value.a), value.c)
//                )
//            }
//        }
//        Thread {
//            val client: OkHttpClient = Builder()
//                .connectTimeout(1, TimeUnit.MINUTES)
//                .writeTimeout(1, TimeUnit.MINUTES)
//                .readTimeout(1, TimeUnit.MINUTES)
//                .build()
//            val request: Request = Builder().url(url).post(builder.build()).build()
//            client.newCall(request).enqueue(object : okhttp3.Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    callback.onError(e)
//                }
//
//                @Throws(IOException::class)
//                override fun onResponse(call: Call, response: Response) {
//                    if (response.isSuccessful) {
//                        val body = response.body
//                        if (body != null) {
//                            val res = body.string()
//                            try {
//                                if (res == "unauthorized access!") {
//                                    callback.onError(IOException("unauthorized access!"))
//                                    return
//                                }
//                                val `object` = JSONObject(res)
//                                callback.onResponse(`object`)
//                            } catch (e: JSONException) {
//                                callback.onError(IOException("Invalid response format! " + e.message + " => " + res))
//                            }
//                        } else {
//                            callback.onError(IOException("Null body"))
//                        }
//                    } else {
//                        callback.onError(IOException("Server Error! " + response.code))
//                    }
//                }
//            })
//        }.start()
//    }
//
//    fun getMimeType(url: String): String? {
//        var type: String? = null
//        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
//        if (extension != null) {
//            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
//        }
//        if (type == null) {
//            if (url.contains(".")) {
//                return url.substring(url.lastIndexOf(".") + 1)
//            }
//        }
//        return type
//    }

    fun post(params: String?,callback: Callback) {
        makeRequest(callback,params,"POST")
    }

    interface Callback {
        fun onError(exception: IOException?)

        @Throws(JSONException::class)
        fun onResponse(response: String?)
    }
}
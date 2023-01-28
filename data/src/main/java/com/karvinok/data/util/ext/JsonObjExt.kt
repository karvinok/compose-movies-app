package com.karvinok.data.util.ext

import com.google.gson.JsonObject
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

inline fun <T> JSONObject.openString(
        key: String?,
        valueGetter: (String) -> T
) = if(key == null) null else {
    if (has(key)) valueGetter(getString(key)) else null
}

inline fun <T> JSONObject.openObject(
        key: String?,
        valueGetter: (JSONObject) -> T
) = if(key == null) null else {
    if (has(key)) valueGetter(getJSONObject(key)) else null
}

fun requestBody(function: JsonObject.() -> Unit): RequestBody {
    return JsonObject().apply(function).toString().toRequestBody()
}
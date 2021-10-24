package com.example.testtaskr.data.remote
import android.util.Log
import com.example.testtaskr.utils.Resource
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResults(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                Log.i("BaseDataSource", body.toString())
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()} ")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
    private fun <T> error(message: String): Resource<T> {
        Log.d("BaseDataSource", message)
        return Resource.error("Network call has failed because of next reason: $message")
    }

}
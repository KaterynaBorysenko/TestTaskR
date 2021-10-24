package com.example.testtaskr.data.repository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.testtaskr.utils.Resource
import kotlinx.coroutines.Dispatchers

suspend fun <T> performGetOperation(
    networkCall: suspend () -> Resource<T>
): Resource<T> {
    var response: Resource<T> = Resource.loading(null)
    return when (response.status) {
        Resource.Status.SUCCESS -> {
            response
        }
        Resource.Status.ERROR -> {
            Resource.error(response.message!!)
        }
        Resource.Status.LOADING -> {
            response = networkCall.invoke()
            return response
        }
    }

}
fun <T> performGetLiveDataOperation(
    networkCall: suspend () -> Resource<T>): LiveData<Resource<T>> {
    return liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            emit(responseStatus)

            Log.i("DataAccessStrategy", responseStatus.status.name)
            Log.i("DataAccessStrategy", responseStatus.data.toString())

        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }
}
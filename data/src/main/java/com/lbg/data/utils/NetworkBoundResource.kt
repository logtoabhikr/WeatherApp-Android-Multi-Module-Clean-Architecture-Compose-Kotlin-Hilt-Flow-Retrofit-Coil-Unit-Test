package com.lbg.data.utils

import com.google.gson.JsonParser
import com.lbg.domain.utils.Constants
import com.lbg.domain.utils.DispatcherProvider
import com.lbg.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class NetworkBoundResource @Inject constructor(private val dispatcherProvider: DispatcherProvider) {


    suspend fun <ResultType> downloadData(api: suspend () -> Response<ResultType>): Flow<Result<ResultType>> {
        return withContext(dispatcherProvider.io) {
            flow {
                emit(Result.Loading)
                val response: Response<ResultType> = api()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(Result.Success(data = it))
                    } ?: emit(Result.Error(message = Constants.UNKNOWN_ERROR, code = 0))
                } else {
                    emit(
                        Result.Error(
                            message = parserErrorBody(response.errorBody()),
                            code = response.code()
                        )
                    )
                }

            }.catch { error ->
                emit(Result.Error(message = message(error), code = code(error)))
            }
        }
    }

    private fun parserErrorBody(response: ResponseBody?): String {
        return response?.let {
            val errorMessage =
                JsonParser.parseString(it.string()).asJsonObject[Constants.MESSAGE_PARAMS].asString
            errorMessage.ifEmpty { Constants.SOMETHING_ERROR }
            errorMessage
        } ?: Constants.UNKNOWN_ERROR
    }

    private fun message(throwable: Throwable?): String {
        when (throwable) {
            is SocketTimeoutException -> return Constants.CONNECTION_TIMEOUT
            is IOException -> return Constants.NETWORK_ERROR
            is HttpException -> return try {
                val errorJsonString = throwable.response()?.errorBody()?.string()
                val errorMessage =
                    JsonParser.parseString(errorJsonString).asJsonObject[Constants.MESSAGE_PARAMS].asString
                errorMessage.ifEmpty { Constants.SOMETHING_ERROR }
            } catch (e: Exception) {
                Constants.UNKNOWN_ERROR
            }
        }
        return Constants.UNKNOWN_ERROR
    }

    private fun code(throwable: Throwable?): Int {
        return if (throwable is HttpException) (throwable).code()
        else 0
    }
}

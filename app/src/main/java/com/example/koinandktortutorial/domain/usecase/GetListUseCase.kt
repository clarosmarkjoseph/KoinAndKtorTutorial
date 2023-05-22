package com.example.koinandktortutorial.domain.usecase

import android.util.Log
import com.example.koinandktortutorial.data.dto.UserInfo
import com.example.koinandktortutorial.domain.NetworkResponse
import com.example.koinandktortutorial.domain.repository.GetListRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetListUseCase(private val getListRepository: GetListRepository) {

    suspend fun getList(): Flow<NetworkResponse<List<UserInfo>>> = flow {
        emit(NetworkResponse.Loading())
        try {
            val data = getListRepository.getList()
            if (data.data != null) {
                emit(NetworkResponse.Success(data.data))
                Log.e("data getList: ", Gson().toJson(data))
            } else {
                emit(NetworkResponse.Error("network Error"))
            }
        } catch (e: HttpException) {
            emit(NetworkResponse.Error(e.localizedMessage ?: "An unexpected error occured."))
        } catch (e: IOException) {
            emit(NetworkResponse.Error("Couldn't reach to server. Please check your internet"))
        }
    }

}
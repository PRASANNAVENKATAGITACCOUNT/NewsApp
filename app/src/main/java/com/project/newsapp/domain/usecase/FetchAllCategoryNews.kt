package com.project.newsapp.domain.usecase

import com.project.newsapp.data.repository_impl.LocalRepositorySource
import com.project.newsapp.data.repository_impl.RemoteRepositorySource
import com.project.newsapp.domain.data.NewsDataState
import com.project.newsapp.domain.network.NetworkConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchAllCategoryNews@Inject constructor(
    val remoteRepository: RemoteRepositorySource,
    val localRepositorySource: LocalRepositorySource,
    private val networkConnectivityObserver: NetworkConnection,
) {

    operator fun invoke() : Flow<NewsDataState> = flow {
        networkConnectivityObserver.isConnected().collect { networkStatus ->
            emit(NewsDataState(loading = true, data = null,error=null))
            val newsData= if(networkStatus){
                remoteRepository.getNewsFromAllCategory()
            }else{
                localRepositorySource.getNewsFromAllCategory()
            }
            val errorMessage = if(newsData==null) "No Data Found"
            else if(newsData.status!=null && newsData.status == "error")
                newsData.message?:"No Error Message Found"
            else
                ""
            when {
                newsData?.status?.lowercase()=="ok"->{
                    emit(NewsDataState(loading = false, data = newsData,error=null))
                }
                errorMessage.isNotEmpty() -> {
                    emit(NewsDataState(loading = false, data = null,error=errorMessage))
                }
            }
        }

    }.flowOn(Dispatchers.IO)
}
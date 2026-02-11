package com.project.newsapp.domain.usecase

import com.project.newsapp.data.repository_impl.LocalRepositorySource
import com.project.newsapp.data.repository_impl.RemoteRepositorySource
import com.project.newsapp.domain.data.NewsQueryState
import com.project.newsapp.domain.network.NetworkConnection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchNewsOnQuery @Inject constructor(
    private val remoteRepositorySource: RemoteRepositorySource,
    private val localRepositorySource: LocalRepositorySource,
    private val networkConnectivityObserver: NetworkConnection
) {

    operator fun invoke(query: String): Flow<NewsQueryState> = flow{
        networkConnectivityObserver.isConnected().collect { networkStatus->
            emit(NewsQueryState(loading = true, data = null,error=null))
            val newsData= if(networkStatus){
                remoteRepositorySource.getNewsOnQuery(query)
            }else{
                localRepositorySource.getNewsOnQuery(query)
            }
            val errorMessage = if(newsData==null) "No Data Found"
            else if(newsData.status!=null && newsData.status == "error")
                newsData.message?:"No Error Message Found"
            else
                ""
            when {
                errorMessage.isNotEmpty() -> {
                    emit(NewsQueryState(loading = false, data = null,error=errorMessage))
                }
                newsData?.status=="ok"->{
                    emit(NewsQueryState(loading = false, data = newsData,error=null))
                }
            }
        }

    }
}
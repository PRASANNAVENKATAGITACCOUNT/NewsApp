package com.project.newsapp.domain.usecase

import com.project.newsapp.data.repository_impl.RemoteRepositorySource
import com.project.newsapp.domain.Repository
import com.project.newsapp.domain.data.NewsDataState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchTopHeadlines @Inject constructor(val repository: RemoteRepositorySource)  {

    operator fun invoke(countryCode: String): Flow<NewsDataState> = flow {
        emit(NewsDataState(loading = true, data = null,error=null))
        val newsData=repository.getTopHeadings(countryCode)
        val errorMessage = if(newsData==null) "No Data Found"
        else if(newsData.status!=null && newsData.status == "error")
            newsData.message?:"No Error Message Found"
        else
            ""
        when {
            errorMessage.isNotEmpty() -> {
                emit(NewsDataState(loading = false, data = null,error=errorMessage))
            }
            newsData?.status=="ok"->{
                emit(NewsDataState(loading = false, data = newsData,error=null))
            }
        }

    }

}
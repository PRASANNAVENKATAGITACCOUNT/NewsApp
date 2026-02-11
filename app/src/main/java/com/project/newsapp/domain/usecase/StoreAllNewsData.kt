package com.project.newsapp.domain.usecase

import com.project.newsapp.data.repository_impl.LocalRepositorySource
import com.project.newsapp.domain.data.NewsData
import com.project.newsapp.domain.data.NewsDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreAllNewsData @Inject constructor( val localRepositorySource: LocalRepositorySource) {

    operator fun invoke(data: NewsData): Flow<String> = flow {
        val message = localRepositorySource.storeNewsDataLocally(data)
        emit(message)
    }
}
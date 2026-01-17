package com.project.newsapp.domain.network

import kotlinx.coroutines.flow.Flow

interface NetworkConnection {

    fun isConnected() : Flow<Boolean>

}
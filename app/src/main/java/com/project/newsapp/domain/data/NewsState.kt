package com.project.newsapp.domain.data

data class NewsDataState(
    val loading: Boolean = false,
    val data: NewsData?= null,
    val error: String?=null
)


/*
*
sealed class Resource<T> (val data : T?=null, val message:String?=null) {
    class Success<T>(data:T): Resource<T>(data)
    class Error<T>(message: String,data:T?=null): Resource<T>(data,message)
    class Loading<T>(data:T?=null):Resource<T>(data)
}
* */
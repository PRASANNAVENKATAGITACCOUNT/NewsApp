package com.project.newsapp.presentation.screens.home_screen

import androidx.compose.runtime.mutableStateOf


data class FollowingTopic(
    var id: Long = System.currentTimeMillis(),
    var followingTopic: String="",
    val isSelected: Boolean=false
)


fun getFollowingTopics() : List<FollowingTopic>{
    val list = mutableListOf<FollowingTopic>()
    val arr = arrayOf<String>("Jobs","Business","Technology","Mobile")
    for(i in 0..<arr.size){
        list.add(FollowingTopic(followingTopic = arr[i]))
    }
    return list.toList()
}
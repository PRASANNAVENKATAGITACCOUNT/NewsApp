package com.project.newsapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SourceEntity(
    @PrimaryKey(autoGenerate = true)
    val sourceId: Long=0L,
    val name: String?
)

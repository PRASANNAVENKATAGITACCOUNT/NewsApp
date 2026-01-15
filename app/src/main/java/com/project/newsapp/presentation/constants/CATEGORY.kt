package com.project.newsapp.presentation.constants

sealed class CATEGORY(val route: String) {
    // business entertainment general health science sports technology
    object TOP_HEADLINES : CATEGORY("Top Headlines")
    object BUSINESS : CATEGORY("business")
    object ENTERTAINMENT : CATEGORY("entertainment")
    object GENERAL : CATEGORY("general")
    object HEALTH : CATEGORY("health")
    object SCIENCE : CATEGORY("science")
    object TECHNOLOGY : CATEGORY("technology")

    companion object{
        fun getAllCategories() : List<CATEGORY>{
            return listOf(
                TOP_HEADLINES, BUSINESS, ENTERTAINMENT, GENERAL,
                HEALTH, SCIENCE, TECHNOLOGY
            )
        }
    }


}
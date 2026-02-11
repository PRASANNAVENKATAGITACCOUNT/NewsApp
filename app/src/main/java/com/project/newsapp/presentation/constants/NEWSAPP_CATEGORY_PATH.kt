package com.project.newsapp.presentation.constants

sealed class NEWSAPP_CATEGORY_PATH(val route: String) {
    // business entertainment general health science sports technology
    object TOP_HEADLINES : NEWSAPP_CATEGORY_PATH("Top Headlines")
    object BUSINESS : NEWSAPP_CATEGORY_PATH("business")
    object ENTERTAINMENT : NEWSAPP_CATEGORY_PATH("entertainment")
    object GENERAL : NEWSAPP_CATEGORY_PATH("general")
    object HEALTH : NEWSAPP_CATEGORY_PATH("health")
    object SCIENCE : NEWSAPP_CATEGORY_PATH("science")
    object TECHNOLOGY : NEWSAPP_CATEGORY_PATH("technology")

    object SHOW_ARTICLE_SCREEN : NEWSAPP_CATEGORY_PATH("")

    companion object{


        fun getAllCategories() : List<NEWSAPP_CATEGORY_PATH>{
            return listOf(
                BUSINESS, ENTERTAINMENT, GENERAL,
                HEALTH, SCIENCE, TECHNOLOGY
            )
        }
    }


}

const val NEWS_TOP_HEADLINES="top-headlines"
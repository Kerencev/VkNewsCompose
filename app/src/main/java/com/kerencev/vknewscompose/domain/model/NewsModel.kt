package com.kerencev.vknewscompose.domain.model

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.kerencev.vknewscompose.extensions.getParcelableNew
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsModel(
    val id: Int,
    val name: String,
    val postTime: Long,
    val text: String,
    val viewsCount: Int,
    val sharesCount: Int,
    val commentsCount: Int,
    val likesCount: Int
) : Parcelable {

    companion object {

        val NavigationType: NavType<NewsModel> = object : NavType<NewsModel>(false) {
            override fun get(bundle: Bundle, key: String): NewsModel? {
                return bundle.getParcelableNew(key, NewsModel::class.java)
            }

            override fun parseValue(value: String): NewsModel {
                return Gson().fromJson(value, NewsModel::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: NewsModel) {
                bundle.putParcelable(key, value)
            }

        }

    }

}

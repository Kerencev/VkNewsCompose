package com.kerencev.vknewscompose.domain.entities

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.kerencev.vknewscompose.presentation.utils.extensions.getParcelableNew
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsModel(
    val id: Long,
    val communityId: Long,
    val communityName: String,
    val postTime: String,
    val communityImageUrl: String?,
    val contentText: String,
    val contentImageUrl: String?,
    val viewsCount: Int,
    val sharesCount: Int,
    val commentsCount: Int,
    val likesCount: Int,
    val isLiked: Boolean
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

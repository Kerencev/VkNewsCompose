package com.kerencev.vknewscompose.presentation.model

import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.kerencev.vknewscompose.presentation.extensions.getParcelableNew
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class NewsModelUi(
    val id: Long,
    val communityId: Long,
    val communityName: String,
    val postTime: String,
    val communityImageUrl: String?,
    val contentText: String,
    val imageContent: List<ImageContentModelUi>,
    val viewsCount: Int,
    val sharesCount: Int,
    val commentsCount: Int,
    val likesCount: Int,
    val isLiked: Boolean
) : Parcelable {

    companion object {

        val NavigationType: NavType<NewsModelUi> = object : NavType<NewsModelUi>(false) {
            override fun get(bundle: Bundle, key: String): NewsModelUi? {
                return bundle.getParcelableNew(key, NewsModelUi::class.java)
            }

            override fun parseValue(value: String): NewsModelUi {
                return Gson().fromJson(value, NewsModelUi::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: NewsModelUi) {
                bundle.putParcelable(key, value)
            }

        }

    }
}

@Parcelize
data class ImageContentModelUi(
    val id: Long,
    val url: String,
    val height: Int,
    val width: Int,
) : Parcelable

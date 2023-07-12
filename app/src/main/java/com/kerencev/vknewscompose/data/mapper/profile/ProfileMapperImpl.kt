package com.kerencev.vknewscompose.data.mapper.profile

import com.kerencev.vknewscompose.data.dto.profile.ProfileDto
import com.kerencev.vknewscompose.data.dto.profile.ProfilePhotosDto
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import javax.inject.Inject

class ProfileMapperImpl @Inject constructor() : ProfileMapper {

    override fun mapToEntity(dto: ProfileDto): ProfileModel {
        return ProfileModel(
            id = dto.id.toString(),
            name = dto.firstName.orEmpty(),
            lastName = dto.lastName.orEmpty(),
            city = dto.city?.title,
            universityName = dto.universityName,
            avatarUrl = dto.avatarUrl,
            friendsCount = dto.counters?.friends ?: 0
        )
    }

    override fun mapToEntity(dto: ProfilePhotosDto): List<PhotoModel> {
        return dto.items?.map {
            PhotoModel(
                id = it.id ?: 0,
                date = it.date,
                lat = it.lat,
                long = it.long,
                url = it.sizes?.lastOrNull()?.url.orEmpty(),
                text = it.text.orEmpty(),
                likes = it.likes?.count ?: 0,
                reposts = it.reposts?.count ?: 0
            )
        } ?: emptyList()
    }

}
package com.kerencev.vknewscompose.data.mapper.profile

import com.kerencev.vknewscompose.data.dto.profile.ProfileDto
import com.kerencev.vknewscompose.data.dto.profile.ProfilePhotosDto
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.ProfileModel

interface ProfileMapper {

    fun mapToEntity(dto: ProfileDto): ProfileModel

    fun mapToEntity(dto: ProfilePhotosDto): List<PhotoModel>
}
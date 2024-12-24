package com.fakhrirasyids.stasave.core.domain.usecase

import android.content.Context
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.core.domain.repository.StasaveRepository
import com.fakhrirasyids.stasave.core.utils.helper.Result
import kotlinx.coroutines.flow.Flow

interface AllMediaUseCase {
    suspend fun getWhatsappMedias(context: Context): Flow<Result<List<MediaModel>>>
}

internal class AllMediaInteractor(
    private val stasaveRepository: StasaveRepository
) : AllMediaUseCase {
    override suspend fun getWhatsappMedias(context: Context) =
        stasaveRepository.getWhatsappMedias(context)
}
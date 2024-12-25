package com.fakhrirasyids.stasave.core.domain.usecase

import com.fakhrirasyids.stasave.core.domain.repository.StasaveRepository
import kotlinx.coroutines.flow.Flow

interface WhatsappUriUseCase {
    suspend fun getWhatsappUri(): Flow<String>
    suspend fun saveWhatsappUri(whatsappUri: String)
}

internal class WhatsappUriInteractor(
    private val stasaveRepository: StasaveRepository
) : WhatsappUriUseCase {
    override suspend fun getWhatsappUri() =
        stasaveRepository.getWhatsappUri()

    override suspend fun saveWhatsappUri(whatsappUri: String) {
        stasaveRepository.saveWhatsappUri(whatsappUri)
    }
}
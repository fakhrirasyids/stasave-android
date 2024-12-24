package com.fakhrirasyids.stasave.core.domain.usecase

import com.fakhrirasyids.stasave.core.domain.repository.StasaveRepository

interface WhatsappUriUseCase {
    suspend fun saveWhatsappUri(whatsappUri: String)
}

internal class WhatsappUriInteractor(
    private val stasaveRepository: StasaveRepository
) : WhatsappUriUseCase {
    override suspend fun saveWhatsappUri(whatsappUri: String) {
        stasaveRepository.saveWhatsappUri(whatsappUri)
    }
}
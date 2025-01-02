package com.fakhrirasyids.stasave.platform.ui.screens.mediapreview

import androidx.lifecycle.ViewModel
import com.fakhrirasyids.stasave.core.domain.usecase.AllMediaUseCase
import com.fakhrirasyids.stasave.core.domain.usecase.SavedMediaUseCase

class MediaPreviewViewModel(
    private val allMediaUseCase: AllMediaUseCase,
    private val savedMediaUseCase: SavedMediaUseCase,
) : ViewModel() {

}
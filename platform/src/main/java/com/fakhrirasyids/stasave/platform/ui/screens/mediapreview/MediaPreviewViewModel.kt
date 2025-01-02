package com.fakhrirasyids.stasave.platform.ui.screens.mediapreview

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.core.domain.usecase.SavedMediaUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.fakhrirasyids.stasave.core.utils.helper.Result

class MediaPreviewViewModel(
    private val savedMediaUseCase: SavedMediaUseCase,
) : ViewModel() {
    private val _isSuccessful = mutableStateOf(false)
    val isSuccessful: State<Boolean> = _isSuccessful

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    fun saveMedia(context: Context, mediaModel: MediaModel) {
        viewModelScope.launch(Dispatchers.IO) {
            savedMediaUseCase.insertMedia(context, mediaModel).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.value = true
                        _errorMessage.value = ""
                        _isSuccessful.value = false
                    }

                    is Result.Success -> {
                        _isLoading.value = false
                        _errorMessage.value = ""
                        _isSuccessful.value = true
                    }

                    is Result.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.error
                        _isSuccessful.value = false
                    }
                }
            }
        }
    }

    fun deleteMedia(context: Context, mediaModel: MediaModel) {
        viewModelScope.launch(Dispatchers.IO) {
            savedMediaUseCase.deleteMedia(context, mediaModel).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.value = true
                        _errorMessage.value = ""
                        _isSuccessful.value = false
                    }

                    is Result.Success -> {
                        _isLoading.value = false
                        _errorMessage.value = ""
                        _isSuccessful.value = true
                    }

                    is Result.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.error
                        _isSuccessful.value = false
                    }
                }
            }
        }
    }
}
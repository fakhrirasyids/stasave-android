package com.fakhrirasyids.stasave.platform.ui.screens.main.saved

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.core.domain.usecase.SavedMediaUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.fakhrirasyids.stasave.core.utils.helper.Result

class SavedViewModel(
    private val savedMediaUseCase: SavedMediaUseCase,
) : ViewModel() {
    private val _savedImageMedias = mutableStateOf(listOf<MediaModel>())
    val savedImageMedias: State<List<MediaModel>> = _savedImageMedias

    private val _savedVideoMedias = mutableStateOf(listOf<MediaModel>())
    val savedVideoMedias: State<List<MediaModel>> = _savedVideoMedias

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    fun getAllSavedImages(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            savedMediaUseCase.getAllImageMedia(context).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.value = true
                        _errorMessage.value = ""
                    }

                    is Result.Success -> {
                        _isLoading.value = false
                        _savedImageMedias.value = result.data
                    }

                    is Result.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.error
                    }
                }
            }
        }
    }

    fun getAllSavedVideos(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            savedMediaUseCase.getAllVideoMedia(context).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.value = true
                        _errorMessage.value = ""
                    }

                    is Result.Success -> {
                        _isLoading.value = false
                        _savedVideoMedias.value = result.data
                    }

                    is Result.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.error
                    }
                }
            }
        }
    }
}
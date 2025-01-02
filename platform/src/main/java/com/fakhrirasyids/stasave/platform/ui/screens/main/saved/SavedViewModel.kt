package com.fakhrirasyids.stasave.platform.ui.screens.main.saved

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.core.domain.usecase.AllMediaUseCase
import com.fakhrirasyids.stasave.core.domain.usecase.WhatsappUriUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.fakhrirasyids.stasave.core.utils.helper.Result
import com.fakhrirasyids.stasave.core.utils.enums.MediaType

class SavedViewModel(
    private val allMediaUseCase: AllMediaUseCase,
    private val whatsappUriUseCase: WhatsappUriUseCase,
) : ViewModel() {
    private val _whatsappUri = mutableStateOf("")
    val whatsappUri: State<String> = _whatsappUri

    private val _whatsappImageMedias = mutableStateOf(listOf<MediaModel>())
    val whatsappImageMedias: State<List<MediaModel>> = _whatsappImageMedias

    private val _whatsappVideoMedias = mutableStateOf(listOf<MediaModel>())
    val whatsappVideoMedias: State<List<MediaModel>> = _whatsappVideoMedias

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    init {
        getWhatsappSavedFolderUri()
    }

    fun getAllWhatsappImages(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            allMediaUseCase.getWhatsappMedias(context).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.value = true
                        _errorMessage.value = ""
                    }

                    is Result.Success -> {
                        _isLoading.value = false

                        val whatsappMedias = result.data

                        _whatsappImageMedias.value = whatsappMedias.filter {
                            it.fileType == MediaType.IMAGE.name.lowercase()
                        }

                        _whatsappVideoMedias.value = whatsappMedias.filter {
                            it.fileType == MediaType.VIDEO.name.lowercase()
                        }
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.error
                    }
                }
            }
        }
    }

    fun saveWhatsappSavedFolderUri(whatsappUri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            whatsappUriUseCase.saveWhatsappUri(whatsappUri = whatsappUri)
        }
    }

    private fun getWhatsappSavedFolderUri() {
        viewModelScope.launch {
            whatsappUriUseCase.getWhatsappUri().collect {
                _whatsappUri.value = it
            }
        }
    }
}
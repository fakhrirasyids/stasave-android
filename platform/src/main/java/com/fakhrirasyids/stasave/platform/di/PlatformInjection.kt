package com.fakhrirasyids.stasave.platform.di

import com.fakhrirasyids.stasave.platform.ui.screens.main.home.HomeViewModel
import com.fakhrirasyids.stasave.platform.ui.screens.mediapreview.MediaPreviewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { MediaPreviewViewModel(get()) }
}
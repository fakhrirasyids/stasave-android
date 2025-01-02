package com.fakhrirasyids.stasave.platform.ui.navigation

import com.fakhrirasyids.stasave.platform.utils.enums.PlatformScreen

sealed class Screen(val route: String) {
    data object Splash : Screen(PlatformScreen.SPLASH.name.lowercase())
    data object Main : Screen(PlatformScreen.MAIN.name.lowercase())
    data object MediaPreview :
        Screen("${PlatformScreen.MEDIA_PREVIEW.name.lowercase()}/{$EXTRA_MEDIA_PREVIEW_SELECTED_ITEM_INDEX}/{$EXTRA_MEDIA_PREVIEW_TYPE_NAME}/{$EXTRA_MEDIA_PREVIEW_LIST}/{$EXTRA_MEDIA_IS_FROM_SAVED}") {
        fun createRoute(
            selectedItemIndex: Int,
            mediaTypeName: String,
            mediaList: String,
            isFromSaved: Boolean
        ) =
            "${PlatformScreen.MEDIA_PREVIEW.name.lowercase()}/$selectedItemIndex/$mediaTypeName/$mediaList/$isFromSaved"
    }

    companion object {
        const val EXTRA_MEDIA_PREVIEW_SELECTED_ITEM_INDEX = "mediaSelectedIndex"
        const val EXTRA_MEDIA_PREVIEW_TYPE_NAME = "mediaTypeName"
        const val EXTRA_MEDIA_PREVIEW_LIST = "mediaList"
        const val EXTRA_MEDIA_IS_FROM_SAVED = "isFromSaved"
    }
}
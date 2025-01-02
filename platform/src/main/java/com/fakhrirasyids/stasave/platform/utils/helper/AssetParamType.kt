package com.fakhrirasyids.stasave.platform.utils.helper

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AssetParamType : NavType<ArrayList<MediaModel>>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): ArrayList<MediaModel>? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelableArrayList(key, MediaModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelableArrayList(key)
        }

    override fun parseValue(value: String): ArrayList<MediaModel> {
        val listType = object : TypeToken<ArrayList<MediaModel>>() {}.type
        return Gson().fromJson(value, listType)
    }

    override fun put(bundle: Bundle, key: String, value: ArrayList<MediaModel>) {
        bundle.putParcelableArrayList(key, value)
    }
}
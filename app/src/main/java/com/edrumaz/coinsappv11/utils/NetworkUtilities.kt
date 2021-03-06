package com.edrumaz.coinsappv11.utils

import android.net.Uri
import java.net.URL

class NetworkUtilities {

    companion object {
        const val BASE_URL = "http://192.168.1.10:3000/"
        const val PATH_COIN = "coin"
        const val TOKEN = "AS"

        /**
         * Get by ID URL Maker
         */
        fun buildURL(id: String) = URL(
            Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(PATH_COIN)
                .appendPath(id)
                .build().toString()
        )

        /**
         *  Get all Coins URL Maker
         */
        fun buildURL() = URL(
            Uri.parse(BASE_URL)
                .buildUpon()
                .build().toString()
        )

        fun getHTTPResult(url: URL) = url.readText()
    }
}
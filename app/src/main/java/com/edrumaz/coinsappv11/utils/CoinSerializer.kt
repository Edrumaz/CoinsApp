package com.edrumaz.coinsappv11.utils

import com.edrumaz.coinsappv11.models.Coin
import org.json.JSONArray
import org.json.JSONObject

class CoinSerializer {

    companion object {

        fun parseCoins(coinsText: String): List<Coin> {
            val coinsJSON = JSONArray(coinsText)
            return MutableList(coinsJSON.length() - 1) {
                parseCoin(coinsJSON[it].toString())
            }
        }

        fun parseCoin(coinText: String): Coin {
            val coinJSON = JSONObject(coinText)

           return with(coinJSON) {
                Coin(getString("name"),getString("country"), getString("symbol"))
            }
        }

    }
}
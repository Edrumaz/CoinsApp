package com.edrumaz.coinsappv11

// https://codelabs.developers.google.com/codelabs/kotlin-android-training-data-binding-basics/#2

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.edrumaz.coinsappv11.adapter.CoinAdapter
import com.edrumaz.coinsappv11.models.Coin
import com.edrumaz.coinsappv11.utils.CoinSerializer
import com.edrumaz.coinsappv11.utils.NetworkUtilities
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import com.edrumaz.coinsappv11.utils.ActivityMainBinding
import androidx.databinding.DataBindingUtil

class MainActivity : AppCompatActivity() {

    lateinit var viewAdapter: CoinAdapter
    lateinit var viewManager: LinearLayoutManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = CoinAdapter(listOf<Coin>()) {
            Snackbar.make(rv_coin,
                "CLick en " + it.name,
                Snackbar.LENGTH_SHORT)
                .show()
        }

        rv_coin.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }

        CoinsFetch().execute()
    }


    inner class CoinsFetch : AsyncTask<Unit, Unit, List<Coin>>() {

        override fun doInBackground(vararg params: Unit?): List<Coin> {
            val url = NetworkUtilities.buildURL()
            val resultString = NetworkUtilities.getHTTPResult(url)

            val resultJSON = JSONObject(resultString)

            return if (resultJSON.getBoolean("success")) {
                CoinSerializer.parseCoins(
                    resultJSON.getJSONArray("docs").toString()
                )
            } else {
                listOf<Coin>()
            }
        }

        override fun onPostExecute(result: List<Coin>) {
            if (result.isNotEmpty()) {
                viewAdapter.setData(result)
            } else {
                Snackbar.make(rv_coin,
                    "No se pudo obtener datos",
                    Snackbar.LENGTH_SHORT).show()
            }
        }

    }
}

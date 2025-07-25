package wolt.assignment.demo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import wolt.assignment.demo.adapters.RestaurantAdapter
import wolt.assignment.demo.api.ApiClient
import wolt.assignment.demo.api.RestaurantApi
import wolt.assignment.demo.databinding.ActivityMainBinding
import wolt.assignment.demo.models.RestaurantModel
import wolt.assignment.demo.utils.Commons.Companion.isInternetAvailable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var restaurantAdapter: RestaurantAdapter? = null
    private var restaurantModel: RestaurantModel? = null
    private var currentLocationIndex = 0
    private lateinit var locationUpdater: Runnable

    val coordinates = listOf(
        Pair(60.169418, 24.931618),
        Pair(60.169818, 24.932906),
        Pair(60.170005, 24.935105),
        Pair(60.169108, 24.936210),
        Pair(60.168355, 24.934869),
        Pair(60.167560, 24.932562),
        Pair(60.168254, 24.931532),
        Pair(60.169012, 24.930341),
        Pair(60.170085, 24.929569)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        setRestaurantAdapter()
        if (isInternetAvailable(this)) {
            callAPI(latitude = coordinates[0].first, longitude = coordinates[0].second, true)
            startLocationUpdates()
        } else {
            showNoInternetDialog()
        }
    }

    private fun startLocationUpdates() {
        locationUpdater = object : Runnable {
            override fun run() {
                val currentLocation = coordinates[currentLocationIndex]
                updateLocation(currentLocation.first, currentLocation.second)
                currentLocationIndex = (currentLocationIndex + 1) % coordinates.size
                binding.rvRestrorant.postDelayed(this, 10000)
            }
        }
        binding.rvRestrorant.post(locationUpdater)
    }

    private fun updateLocation(latitude: Double, longitude: Double) {
        if (isInternetAvailable(this)) {
            callAPI(latitude, longitude, false)
        } else {
            showNoInternetDialog()
        }
    }

    private fun callAPI(latitude: Double, longitude: Double,fromFirstTime:Boolean) {
        if (fromFirstTime){
            binding.shimmerRes.root.startShimmer()
            binding.shimmerRes.root.visibility = View.VISIBLE
        }
        val restaurantApi = ApiClient.retrofitInstance.create(RestaurantApi::class.java)
        val call = restaurantApi.getRestaurants(lat = latitude, lon = longitude)
        call.enqueue(object : Callback<RestaurantModel> {
            override fun onResponse(call: Call<RestaurantModel>, response: Response<RestaurantModel>) {
                if (response.isSuccessful) {
                    val restaurantResponse = response.body()
                    restaurantModel = restaurantResponse
                    binding.shimmerRes.root.stopShimmer()
                    binding.shimmerRes.root.visibility = View.GONE
                    val items = restaurantResponse?.sections?.get(1)?.items?.take(15) ?: emptyList()
                    restaurantAdapter?.submitData(items.toMutableList())
                } else {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_SHORT).show()
                        Log.e("API Error", "Response code: ${response.code()}")
                    }
                }
            }
            override fun onFailure(call: Call<RestaurantModel>, t: Throwable) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                    Log.e("API Error", "Failed to fetch data", t)
                }

            }
        })
    }

    private fun setRestaurantAdapter() {
        restaurantAdapter = RestaurantAdapter(this) { position, isFavorite ->
            val venueId = restaurantModel?.sections?.get(1)?.items?.get(position)?.venue?.id ?: return@RestaurantAdapter
            saveFavoriteState(venueId, isFavorite)
        }
        binding.rvRestrorant.apply {
            adapter = restaurantAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun saveFavoriteState(id: String, isFavorite: Boolean) {
        val sharedPref = getSharedPreferences("Favorites", MODE_PRIVATE)
        sharedPref.edit().putBoolean(id, isFavorite).apply()
    }



    private fun showNoInternetDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.str_no_internet_connection))
            .setMessage(getString(R.string.str_please_check_your_internet_connection_and_try_again))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.str_retry)) { _, _ ->
                if (isInternetAvailable(this)) {
                    callAPI(latitude = coordinates[0].first, longitude = coordinates[0].second, true)
                } else {
                    showNoInternetDialog()
                }
            }
            .setNegativeButton(getString(R.string.str_close)) { _, _ ->
                finish()
            }
            .create()
        alertDialog.show()
    }

}



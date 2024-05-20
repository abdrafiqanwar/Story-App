package com.example.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.app.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.app.databinding.ActivityMapsBinding
import com.example.app.di.Result
import com.example.app.viewmodel.MapsViewModel
import com.example.app.viewmodel.ViewModelFactory
import com.google.android.gms.maps.model.LatLngBounds

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val viewModel by viewModels<MapsViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.getStoriesWithLocation().observe(this){
            if (it != null) {
                when (it) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        it.data.forEach { data ->
                            val latLng = LatLng(data.lat!!, data.lon!!)
                            mMap.addMarker(
                                MarkerOptions()
                                    .position(latLng)
                                    .title(data.name)
                                    .snippet(data.description)
                            )
                        }
                    }
                    is Result.Error -> {

                    }
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        addManyMarker()
    }

    data class TourismPlace(
        val name: String,
        val latitude: Double,
        val longitude: Double
    )

    private fun addManyMarker() {
        val tourismPlace = listOf(
            TourismPlace("Floating Market Lembang", -6.8168954,107.6151046),
            TourismPlace("The Great Asia Africa", -6.8331128,107.6048483),
            TourismPlace("Rabbit Town", -6.8668408,107.608081),
            TourismPlace("Alun-Alun Kota Bandung", -6.9218518,107.6025294),
            TourismPlace("Orchid Forest Cikole", -6.780725, 107.637409),
        )
        tourismPlace.forEach { tourism ->
            val latLng = LatLng(tourism.latitude, tourism.longitude)
            mMap.addMarker(MarkerOptions().position(latLng).title(tourism.name))
            boundsBuilder.include(latLng)
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }
}
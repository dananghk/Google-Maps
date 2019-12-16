package com.dananghk.googlemaps

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.dananghk.googlemaps.R
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val TAG = MapsActivity::class.java.simpleName
    private val REQUEST_LOCATION_PERMISSION = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        // Add a marker in Sydney and move the camera
        val uty = LatLng(-7.747033,110.355398)
        mMap.addMarker(MarkerOptions().position(uty).title("Universitas Teknologi Yogyakarta"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uty, 17.0f))
        val Palagan = LatLng (-7.7456334, 110.37330172)
        mMap.addMarker(MarkerOptions().position(Palagan).title("Kontrakan Palagan").snippet("Indonesia, Kota Yogyakarta, DIY, 55581"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Palagan, 17.0f))
        // Add a marker in Pantai Selatan and move the camera
        val Parangtritis = LatLng (-8.023215, 110.329596)
        mMap.addMarker(MarkerOptions().position(Parangtritis).title("Pantai Selatan").snippet("Indonesia, Kota Bantul, DIY, 55772"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Parangtritis, 17.0f))
        // Add a marker in Kraton Jogjakarta and move the camera
        val Kraton = LatLng (-7.805139, 110.364228)
        mMap. addMarker(MarkerOptions().position(Kraton).title("Kraton Yogyakarta").snippet("Indonesia, Panembahan Kota Yogyakarta, DIY, 55131") )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Kraton, 17.0f))
        // Add a marker in Tugu Jogja
        val Tugu = LatLng (-7.782830, 110.367120)
        mMap.addMarker(MarkerOptions().position(Tugu).title("Tugu Yogyakarta").snippet("Indonesia, Kota Yogyakarta, DIY, 55233"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Tugu, 17.0f))

        setPoiClick(mMap)
        setMapStyle(mMap)
        enableMyLocation()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.maps_option, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.normal_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


    // Allows map styling and theming to be customized.
    private fun setMapStyle(map: GoogleMap) {
        try {

            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_style
                )
            )

            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }


    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            mMap.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker.showInfoWindow()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
}

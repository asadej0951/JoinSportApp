package com.wewillapp.masterproject.utils.googleMap

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.wewillapp.masterproject.R
import java.util.*
import javax.inject.Inject

class MapUtils @Inject constructor() {
    private var mMapZoomLevel = 15f
    private var mLocationManager: LocationManager? = null

    fun mapAddMarker(mMap: GoogleMap, mLatitude: Double, mLongitude: Double) {
        val icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)
        mMap.addMarker(
            MarkerOptions()
                .position(LatLng(mLatitude, mLongitude))
                .icon(icon)
        )
    }

    fun mapSetCameraUpdateFactory(mMap: GoogleMap, mLatitude: Double?, mLongitude: Double?) {
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(mLatitude ?: 0.0, mLongitude ?: 0.0), mMapZoomLevel
            )
        )
    }

    fun mapSetAnimateCameraUpdateFactory(mMap: GoogleMap, mLatitude: Double?, mLongitude: Double?) {
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(mLatitude ?: 0.0, mLongitude ?: 0.0), mMapZoomLevel
            )
        )
    }


    @SuppressLint("MissingPermission")
    fun mapGetLocationCurrent(context: Context): Location? {
        mLocationManager =
            context.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = mLocationManager!!.getProviders(true)
        var bestLocation: Location? = null
        for (provider in providers) {
            val l = mLocationManager!!.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                // Found best last known location: %s", l);
                bestLocation = l
            }
        }
        return bestLocation
    }

    fun getLocaltionAddress(context: Context, mCurrentLat: Double, mCurrentLng: Double): String {
        var mLocation = ""
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>

        try {
            addresses = geocoder.getFromLocation(mCurrentLat, mCurrentLng, 1)
            if (addresses.isNotEmpty() && addresses[0].getAddressLine(0) != null) {
                mLocation = addresses[0].getAddressLine(0)
            }
        } catch (ex: Exception) {
            mLocation = "ไม่พบตำแหน่ง"
        }
        return mLocation
    }

    fun formatNumbers(context: Context, distance: Double): String {
        var unit = context.resources.getString(R.string.location_m)
        var mDistance = distance
        // 1 m = 1000 mm
        if (mDistance < 1) {
            mDistance *= 0.001
            unit = context.resources.getString(R.string.location_mm)
            // 1000 m = 1 km
        } else if (mDistance > 1000) {
            mDistance /= 1000
            unit = context.resources.getString(R.string.location_km)
        }
        return String.format("%4.2f%s", mDistance, " $unit")
    }
}
package com.example.buse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var dbRef: DatabaseReference
    private var busMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        dbRef = FirebaseDatabase.getInstance().getReference("busLocation")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lat = snapshot.child("latitude").getValue(Double::class.java)
                val lon = snapshot.child("longitude").getValue(Double::class.java)

                if (lat != null && lon != null) {
                    val position = LatLng(lat, lon)
                    busMarker?.remove()
                    busMarker = mMap.addMarker(MarkerOptions().position(position).title("Bus Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15f))
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}

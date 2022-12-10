package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mx.edu.utez.dealc.R
import mx.edu.utez.dealc.databinding.ActivityMapsBinding
import mx.edu.utez.dealc.utils.LocationLiveData

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var  categoryServiceId: String
    lateinit var categoryServiceName: String
    lateinit var categoryServiceIcon: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryServiceId = intent.getStringExtra("categoryServiceId")!!
        categoryServiceIcon = intent.getStringExtra("categoryServiceIcon")!!
        categoryServiceName = intent.getStringExtra("categoryServiceName")!!

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.buttonSelectLocation.setOnClickListener {
            var intent = Intent(this, ConfirmationActivity::class.java)
            intent.putExtra("categoryServiceId", categoryServiceId)
            intent.putExtra("categoryServiceName",categoryServiceName)
            intent.putExtra("categoryServiceIcon", categoryServiceIcon)
            startActivity(intent)
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

        // Add a marker in Sydney and move the camera
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun initService() {
        LocationLiveData(this).observe(this) {
            Log.d("MapsLog", "Localización real: ${it.latitude}, ${it.longitude}")

            val sydney = LatLng(-34.0, 151.0)

            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(utez))
        }
    }
}
package mx.edu.utez.dealc.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import mx.edu.utez.dealc.R
import mx.edu.utez.dealc.databinding.ActivityMapsBinding
import mx.edu.utez.dealc.model.Location
import mx.edu.utez.dealc.model.ServiceProviderRequest
import mx.edu.utez.dealc.utils.LocationLiveData
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var serviceProviderRequest: ServiceProviderRequest
    var selectedLOcationLat: Double = 0.0
    var selectedLOcationLon: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serviceProviderRequest = ServiceProviderRequest.fromMap((intent.getSerializableExtra("serviceProviderRequest") as HashMap<String, Any?>).toMap())

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.buttonSelectLocation.setOnClickListener {

            serviceProviderRequest.locationClient = Location(selectedLOcationLat, selectedLOcationLon).toMap()

            println("3.- Camino service $serviceProviderRequest")

            var intent = Intent(this, ConfirmationActivity::class.java).putExtra("serviceProviderRequest", HashMap(serviceProviderRequest.toMap()))
            startActivity(intent)
        }

        // Add a marker in your own location and move the camera
        initService()
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

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        getMarkerLocation()
        mMap.isMyLocationEnabled = true
    }

    fun initService() {
        var zoom = true
        var realDirection: LatLng
        LocationLiveData(this).observe(this) {
            try { mMap.clear() }catch (e: Exception){ }
            println("MapsLog Localización real: ${it.latitude}, ${it.longitude}")
            realDirection = LatLng(it.latitude, it.longitude)
            if (zoom){
                mMap.moveCamera(CameraUpdateFactory.newLatLng(realDirection))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(realDirection, 13f))
            }
            zoom = false
        }
    }

    fun getMarkerLocation() {
        mMap.setOnCameraIdleListener {
            var lat = mMap.cameraPosition.target.latitude
            var lon = mMap.cameraPosition.target.longitude
            println("MapsLog marker $lat $lon")
            selectedLOcationLat = lat
            selectedLOcationLon = lon
        }
    }
}
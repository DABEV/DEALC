package mx.edu.utez.dealc.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import mx.edu.utez.dealc.MainCoreApplication
import mx.edu.utez.dealc.R
import mx.edu.utez.dealc.databinding.ActivityRouteServiceBinding

class RouteServiceActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityRouteServiceBinding
    private val shared = MainCoreApplication.shared
    lateinit var serviceId: String
    private var providerLat: Double = 0.0
    private var providerLon: Double = 0.0
    private var clientLat: Double = 0.0
    private var clientLon: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        serviceId = intent.getStringExtra("serviceId")!!
        binding = ActivityRouteServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        getClientLocation()
        println("ROUTE: sevice $serviceId")
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
        mMap.isMyLocationEnabled = true
    }

    fun getProviderRoute() {
        var cliente = LatLng(clientLat,clientLon)
        println("ROUTE: clientexist $clientLat $clientLon")
        var polylineOptions = PolylineOptions()
        FirebaseDatabase.getInstance()
            .getReference("LocationRequest")
            .child("location${serviceId}")
            .child("pointsProvider")
            .get()
            .addOnSuccessListener {
                println("ROUTE: provider success")
                println("ROUTE: providerxist ${it.exists()}")
                if(it.exists()){
                    for (item in it.children){
                        providerLat = item.child("latitude").value.toString().toDouble()
                        providerLon = item.child("longitude").value.toString().toDouble()

                        var provider = LatLng(providerLat, providerLon)
                        polylineOptions.add(provider)

                        mMap.addCircle(CircleOptions()
                            .center(provider)
                            .radius(20.0)
                            .strokeColor(Color.RED)
                            .fillColor(Color.RED))

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(provider))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(provider, 15f))

                    }
                    polylineOptions.width(10f)
                    polylineOptions.color(ContextCompat.getColor(this, R.color.dark_green))

                    var polyline = mMap.addPolyline(polylineOptions)
                    // Dale un estilo a la linea
                    var patron = listOf(
                        Dot(), Gap(10f), Dash(30f), Gap(10f)
                    )
                    polyline.pattern = patron
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "No se puedo obtener la ruta", Toast.LENGTH_SHORT).show()
            }
    }

    fun getClientLocation() {
        println("ROUTE: getClient")
        FirebaseFirestore.getInstance()
            .collection("ServiceProviderRequest")
            .document(serviceId)
            .get()
            .addOnSuccessListener {
                if(it.exists()){
                    clientLat = it["locationClient.latitude"].toString().toDouble()
                    clientLon = it["locationClient.longitude"].toString().toDouble()

                    println("ROUTE: client ${clientLat} ${clientLon}")
                    var clienteLocation = LatLng(clientLat,clientLon)
                    mMap.addMarker(MarkerOptions().position(clienteLocation).title("Destino"))
                    getProviderRoute()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "No se puedo obtener la ruta", Toast.LENGTH_SHORT).show()
            }
    }
}
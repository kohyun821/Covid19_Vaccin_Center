package com.example.covid19_vaccin_center.map.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.covid19_vaccin_center.BuildConfig
import com.example.covid19_vaccin_center.map.viewmodel.MapViewModel
import com.example.covid19_vaccin_center.map.viewmodel.MapViewModelFactory
import com.example.covid19_vaccin_center.R
import com.example.covid19_vaccin_center.Splash.data.entity.Vaccine
import com.example.covid19_vaccin_center.Splash.data.repository.VaccineRepository
import com.example.covid19_vaccin_center.databinding.ActivityMapBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.naver.maps.map.CameraAnimation

class MapActivity : FragmentActivity(),OnMapReadyCallback {
    private lateinit var viewModel: MapViewModel
    private lateinit var binding: ActivityMapBinding

    val Vaccine_List = arrayListOf<Vaccine>()
    private lateinit var naverMap: NaverMap

    lateinit var floatingActionButton: FloatingActionButton

    //선택 되었는지?
    private var selectedMarker: Marker? = null

    private val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private lateinit var locationSource: FusedLocationSource


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = VaccineRepository(this)
        val viewModelFactory = MapViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        floatingActionButton = findViewById(R.id.fab_tracking)


        setupMapFragment()
        //네이버 지도 API키 입력
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.CLIENT_ID)

        floatingActionButton.setOnClickListener {
            checkLocationPermission()
        }

    }

    private fun enableLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
            naverMap.addOnLocationChangeListener { location ->
                val latLng = LatLng(location.latitude, location.longitude)
                naverMap.moveCamera(CameraUpdate.scrollTo(latLng).animate(CameraAnimation.Fly))
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                enableLocation()
            } else {
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            enableLocation()
        }
    }


    //프라그먼트를 이용하여 네이버 지도 생성
    private fun setupMapFragment() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }


    private fun getData() {
        viewModel.getAllVaccines().observe(this, Observer { vaccines ->
            Vaccine_List.clear()
            Vaccine_List.addAll(vaccines)

            Vaccine_List.forEach { vaccine ->
                val marker = Marker().apply {
                    position = LatLng(vaccine.lat.toDouble(), vaccine.lng.toDouble())
                    icon = MarkerIcons.BLACK
                    iconTintColor = getMarkerColor(vaccine.centerType)
                    map = naverMap
                    //여러개 겹치면 합쳐지게
                    isHideCollidedMarkers = true
                    width = 50
                    height = 80
                }

                //정보창 안보이게~
                binding.tvLocation.visibility = View.GONE
                marker.tag = vaccine
                marker.setOnClickListener {
                    val clieckedVaccine = it.tag as Vaccine

                    if (selectedMarker == it) {
                        if (binding.tvLocation.isVisible) {
                            binding.tvLocation.visibility = View.GONE
                            marker.iconTintColor = getMarkerColor(vaccine.centerType)
                            selectedMarker = null
                        } else {
                            viewModel.updateLocationInfo(clieckedVaccine)
                            binding.tvLocation.visibility = View.VISIBLE
                        }
                    } else {
                        resetSelectedMarkerColor()
                        viewModel.updateLocationInfo(clieckedVaccine)
                        selectedMarker = marker
                        marker.iconTintColor = Color.GREEN
                        naverMap.moveCamera(CameraUpdate.scrollTo(marker.position))
                        binding.tvLocation.visibility = View.VISIBLE
                    }
                    true
                }
            }
        })
    }

    private fun resetSelectedMarkerColor() {
        selectedMarker?.iconTintColor = getMarkerColor((selectedMarker?.tag as? Vaccine)?.centerType ?: "")
    }


    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.None
        getData()
    }

    private fun getMarkerColor(centerType: String): Int {
        return when (centerType) {
            "중앙/권역" -> Color.RED
            else -> Color.BLUE
        }
    }

}
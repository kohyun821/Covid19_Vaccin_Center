package com.example.covid19_vaccin_center.map.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons

class MapActivity : FragmentActivity(),OnMapReadyCallback {
    private lateinit var viewModel: MapViewModel
    private lateinit var binding: ActivityMapBinding

    val Vaccine_List = arrayListOf<Vaccine>()
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    lateinit var floatingActionButton: FloatingActionButton
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_map)

        val repository = VaccineRepository(this)
        val viewModelFactory = MapViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        floatingActionButton = findViewById(R.id.fab_tracking)

        //프라그먼트를 이용하여 네이버 지도 생성
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        //네이버 지도 API키 입력
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.CLIENT_ID)

        mapFragment.getMapAsync(this)

        //위치 퍼미션 확인
        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        floatingActionButton.setOnClickListener{
        }

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

                marker.tag = vaccine
                marker.setOnClickListener {
                    val clieckedVaccine = it.tag as Vaccine
                    viewModel.updateLocationInfo(clieckedVaccine)
                    naverMap.moveCamera(CameraUpdate.scrollTo(marker.position))
                    true
                }
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {
        Log.d("Vaccine","onMapReady!!")
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        getData()
    }

    private fun getMarkerColor(centerType: String): Int {
        return when (centerType) {
            "중앙/권역" -> Color.RED
            else -> Color.BLUE
        }
    }

}
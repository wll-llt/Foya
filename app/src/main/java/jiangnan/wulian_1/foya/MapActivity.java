package jiangnan.wulian_1.foya;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

public class MapActivity extends AppCompatActivity implements AMapLocationListener {
    
    private MapView mapView;
    private AMap aMap;
    private AMapLocationClient locationClient;
    
    private Button btnMyLocation;
    private Button btnBack;
    
    private static final int PERMISSION_REQUEST_CODE = 1001;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        initViews();
        initMap(savedInstanceState);
        initLocation();
        setupListeners();
    }
    
    private void initViews() {
        mapView = findViewById(R.id.map_view);
        btnMyLocation = findViewById(R.id.btn_my_location);
        btnBack = findViewById(R.id.btn_back);
    }
    
    private void initMap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        
        // 设置地图基本配置
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        aMap.getUiSettings().setZoomControlsEnabled(true);
        aMap.getUiSettings().setCompassEnabled(true);
        aMap.getUiSettings().setScaleControlsEnabled(true);
        
        // 设置定位样式
        MyLocationStyle myLocationStyle = new MyLocationStyle()
                .myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)
                .interval(2000)
                .strokeColor(Color.argb(0, 0, 0, 0))
                .radiusFillColor(Color.argb(0, 0, 0, 0));
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
    }
    
    private void initLocation() {
        try {
            AMapLocationClient.updatePrivacyShow(this, true, true);
            AMapLocationClient.updatePrivacyAgree(this, true);
            locationClient = new AMapLocationClient(getApplicationContext());
            locationClient.setLocationListener(this);
            
            AMapLocationClientOption option = new AMapLocationClientOption();
            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            option.setOnceLocation(true);
            locationClient.setLocationOption(option);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setupListeners() {
        btnMyLocation.setOnClickListener(v -> {
            if (checkPermissions()) {
                locationClient.startLocation();
            } else {
                requestPermissions();
            }
        });
        
        btnBack.setOnClickListener(v -> finish());
    }
    
    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, 
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 
                PERMISSION_REQUEST_CODE);
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationClient.startLocation();
            } else {
                Toast.makeText(this, "需要位置权限才能使用定位功能", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            // 定位成功
            LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            aMap.moveCamera(com.amap.api.maps.CameraUpdateFactory.newLatLngZoom(latLng, 15));
            
            // 添加当前位置标记
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title("当前位置")
                    .snippet(aMapLocation.getAddress())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            aMap.addMarker(markerOptions);
            
            Toast.makeText(this, "定位成功: " + aMapLocation.getAddress(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "定位失败: " + aMapLocation.getErrorInfo(), Toast.LENGTH_SHORT).show();
        }
        locationClient.stopLocation();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (locationClient != null) {
            locationClient.onDestroy();
        }
    }
    
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
} 
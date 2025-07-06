package jiangnan.wulian_1.foya.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import jiangnan.wulian_1.foya.R;
import jiangnan.wulian_1.foya.SimpleMapActivity;

public class SimpleMapFragment extends Fragment {

    private MapViewModel mapViewModel;
    private WebView webView;
    private Button btnFullScreen;
    private TextView tvLocationInfo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_simple_map, container, false);
        
        initViews(root);
        setupWebView();
        setupListeners();
        
        return root;
    }
    
    private void initViews(View root) {
        webView = root.findViewById(R.id.web_view);
        btnFullScreen = root.findViewById(R.id.btn_full_screen);
        tvLocationInfo = root.findViewById(R.id.tv_location_info);
    }
    
    private void setupWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        
        // 加载高德地图网页版
        String mapUrl = "https://uri.amap.com/marker?position=116.397428,39.90923&name=当前位置&src=myapp&coordinate=gaode&callnative=0";
        webView.loadUrl(mapUrl);
        
        tvLocationInfo.setText("正在加载地图...");
    }
    
    private void setupListeners() {
        btnFullScreen.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SimpleMapActivity.class);
            startActivity(intent);
        });
    }
    
    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }
    
    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
} 
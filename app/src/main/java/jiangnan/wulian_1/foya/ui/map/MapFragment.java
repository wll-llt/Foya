package jiangnan.wulian_1.foya.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.google.android.material.button.MaterialButton;

import jiangnan.wulian_1.foya.R;
import jiangnan.wulian_1.foya.databinding.FragmentMapBinding;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    private MapView mapView;
    private AMap aMap;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        // 设置按钮点击事件
        setupButtonListeners();

        return root;
    }

    private void setupButtonListeners() {
        MaterialButton btnNavigation = binding.btnNavigation;
        MaterialButton btnSearch = binding.btnSearch;

        if (btnNavigation != null) {
            btnNavigation.setOnClickListener(v -> {
                Toast.makeText(getContext(), "开始导航功能", Toast.LENGTH_SHORT).show();
                // 这里可以启动导航功能
            });
        }

        if (btnSearch != null) {
            btnSearch.setOnClickListener(v -> {
                Toast.makeText(getContext(), "搜索景点功能", Toast.LENGTH_SHORT).show();
                // 这里可以启动搜索功能
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
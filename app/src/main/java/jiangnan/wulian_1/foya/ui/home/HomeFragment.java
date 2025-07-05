package jiangnan.wulian_1.foya.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import jiangnan.wulian_1.foya.R;
import jiangnan.wulian_1.foya.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 设置功能卡片的点击事件
        setupClickListeners();

        return root;
    }

    private void setupClickListeners() {
        // 开始航拍卡片
        View startCameraCard = binding.cardStartCamera;
        if (startCameraCard != null) {
            startCameraCard.setOnClickListener(v -> {
                Toast.makeText(getContext(), "启动航拍功能", Toast.LENGTH_SHORT).show();
                // 跳转到飞行控制页面
                Navigation.findNavController(v).navigate(R.id.nav_slideshow);
            });
        }

        // 航拍图库卡片
        View galleryCard = binding.cardGallery;
        if (galleryCard != null) {
            galleryCard.setOnClickListener(v -> {
                Toast.makeText(getContext(), "查看航拍图库", Toast.LENGTH_SHORT).show();
                // 跳转到图库页面
                Navigation.findNavController(v).navigate(R.id.nav_gallery);
            });
        }

        // 飞行控制卡片
        View flightControlCard = binding.cardFlightControl;
        if (flightControlCard != null) {
            flightControlCard.setOnClickListener(v -> {
                Toast.makeText(getContext(), "进入飞行控制", Toast.LENGTH_SHORT).show();
                // 跳转到飞行控制页面
                Navigation.findNavController(v).navigate(R.id.nav_slideshow);
            });
        }

        // 景区地图卡片
        View mapCard = binding.cardMap;
        if (mapCard != null) {
            mapCard.setOnClickListener(v -> {
                Toast.makeText(getContext(), "查看景区地图", Toast.LENGTH_SHORT).show();
                // 跳转到地图页面
                Navigation.findNavController(v).navigate(R.id.nav_map);
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package jiangnan.wulian_1.foya.ui.guide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import jiangnan.wulian_1.foya.databinding.FragmentGuideBinding;

public class GuideFragment extends Fragment {

    private FragmentGuideBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GuideViewModel guideViewModel =
                new ViewModelProvider(this).get(GuideViewModel.class);

        binding = FragmentGuideBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 设置按钮点击事件
        setupButtonListeners();

        return root;
    }

    private void setupButtonListeners() {
        // 规划最佳路线按钮
        MaterialButton btnRoutePlan = binding.btnRoutePlan;
        if (btnRoutePlan != null) {
            btnRoutePlan.setOnClickListener(v -> {
                Toast.makeText(getContext(), "正在为您规划最佳游览路线...", Toast.LENGTH_SHORT).show();
                // 这里可以添加路线规划逻辑
            });
        }

        // 推荐拍照地点按钮
        MaterialButton btnPhotoSpots = binding.btnPhotoSpots;
        if (btnPhotoSpots != null) {
            btnPhotoSpots.setOnClickListener(v -> {
                Toast.makeText(getContext(), "为您推荐最佳拍照地点", Toast.LENGTH_SHORT).show();
                // 这里可以添加拍照地点推荐逻辑
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 
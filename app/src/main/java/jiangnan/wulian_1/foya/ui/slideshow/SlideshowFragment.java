package jiangnan.wulian_1.foya.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import jiangnan.wulian_1.foya.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 设置按钮点击事件
        setupButtonListeners();

        return root;
    }

    private void setupButtonListeners() {
        // 起飞按钮
        MaterialButton btnTakeoff = binding.btnTakeoff;
        if (btnTakeoff != null) {
            btnTakeoff.setOnClickListener(v -> {
                Toast.makeText(getContext(), "无人机起飞中...", Toast.LENGTH_SHORT).show();
                // 这里可以添加实际的起飞逻辑
            });
        }

        // 降落按钮
        MaterialButton btnLand = binding.btnLand;
        if (btnLand != null) {
            btnLand.setOnClickListener(v -> {
                Toast.makeText(getContext(), "无人机降落中...", Toast.LENGTH_SHORT).show();
                // 这里可以添加实际的降落逻辑
            });
        }

        // 方向控制按钮
        MaterialButton btnForward = binding.btnForward;
        if (btnForward != null) {
            btnForward.setOnClickListener(v -> {
                Toast.makeText(getContext(), "向前飞行", Toast.LENGTH_SHORT).show();
            });
        }

        MaterialButton btnLeft = binding.btnLeft;
        if (btnLeft != null) {
            btnLeft.setOnClickListener(v -> {
                Toast.makeText(getContext(), "向左飞行", Toast.LENGTH_SHORT).show();
            });
        }

        MaterialButton btnRight = binding.btnRight;
        if (btnRight != null) {
            btnRight.setOnClickListener(v -> {
                Toast.makeText(getContext(), "向右飞行", Toast.LENGTH_SHORT).show();
            });
        }

        MaterialButton btnUp = binding.btnUp;
        if (btnUp != null) {
            btnUp.setOnClickListener(v -> {
                Toast.makeText(getContext(), "向上飞行", Toast.LENGTH_SHORT).show();
            });
        }

        MaterialButton btnHover = binding.btnHover;
        if (btnHover != null) {
            btnHover.setOnClickListener(v -> {
                Toast.makeText(getContext(), "悬停模式", Toast.LENGTH_SHORT).show();
            });
        }

        MaterialButton btnDown = binding.btnDown;
        if (btnDown != null) {
            btnDown.setOnClickListener(v -> {
                Toast.makeText(getContext(), "向下飞行", Toast.LENGTH_SHORT).show();
            });
        }

        // 自动飞行按钮
        MaterialButton btnAutoFly = binding.btnAutoFly;
        if (btnAutoFly != null) {
            btnAutoFly.setOnClickListener(v -> {
                Toast.makeText(getContext(), "启动自动飞行模式", Toast.LENGTH_SHORT).show();
                // 这里可以添加自动飞行逻辑
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
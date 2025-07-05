package jiangnan.wulian_1.foya.ui.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.chip.Chip;

import java.util.List;

import jiangnan.wulian_1.foya.ImageDetailActivity;
import jiangnan.wulian_1.foya.R;
import jiangnan.wulian_1.foya.adapter.GalleryAdapter;
import jiangnan.wulian_1.foya.databinding.FragmentGalleryBinding;
import jiangnan.wulian_1.foya.model.GalleryImage;

public class GalleryFragment extends Fragment implements GalleryAdapter.OnImageClickListener {

    private FragmentGalleryBinding binding;
    private GalleryViewModel galleryViewModel;
    private GalleryAdapter galleryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupRecyclerView();
        setupCategoryChips();
        observeViewModel();

        return root;
    }

    private void setupRecyclerView() {
        galleryAdapter = new GalleryAdapter(requireContext(), null);
        binding.recyclerGallery.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.recyclerGallery.setAdapter(galleryAdapter);
        galleryAdapter.setOnImageClickListener(this);
    }

    private void setupCategoryChips() {
        // 设置分类筛选按钮点击事件
        binding.chipAll.setOnClickListener(v -> galleryViewModel.setCategory("全部"));
        binding.chipLandscape.setOnClickListener(v -> galleryViewModel.setCategory("风景"));
        binding.chipBuilding.setOnClickListener(v -> galleryViewModel.setCategory("建筑"));
        binding.chipPerson.setOnClickListener(v -> galleryViewModel.setCategory("人物"));
    }

    private void observeViewModel() {
        galleryViewModel.getImages().observe(getViewLifecycleOwner(), images -> {
            if (images != null) {
                galleryAdapter.updateData(images);
            }
        });

        galleryViewModel.getSelectedCategory().observe(getViewLifecycleOwner(), category -> {
            updateCategoryChips(category);
        });
    }

    private void updateCategoryChips(String selectedCategory) {
        // 更新分类按钮的选中状态
        binding.chipAll.setChipBackgroundColorResource(
            "全部".equals(selectedCategory) ? R.color.primary_blue : R.color.light_gray);
        binding.chipLandscape.setChipBackgroundColorResource(
            "风景".equals(selectedCategory) ? R.color.primary_blue : R.color.light_gray);
        binding.chipBuilding.setChipBackgroundColorResource(
            "建筑".equals(selectedCategory) ? R.color.primary_blue : R.color.light_gray);
        binding.chipPerson.setChipBackgroundColorResource(
            "人物".equals(selectedCategory) ? R.color.primary_blue : R.color.light_gray);
    }

    @Override
    public void onImageClick(GalleryImage image) {
        // 跳转到图片详情页面
        Intent intent = new Intent(requireContext(), ImageDetailActivity.class);
        intent.putExtra("title", image.getTitle());
        intent.putExtra("description", image.getDescription());
        intent.putExtra("imageUrl", image.getImageUrl());
        intent.putExtra("location", image.getLocation());
        intent.putExtra("date", image.getDate());
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(GalleryImage image, boolean isFavorite) {
        galleryViewModel.toggleFavorite(image.getId());
        String message = isFavorite ? "已添加到收藏" : "已取消收藏";
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareClick(GalleryImage image) {
        // 分享图片
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "分享航拍图片");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "分享一张美丽的航拍图片：" + image.getTitle() + 
                "\n拍摄地点：" + image.getLocation() + 
                "\n拍摄时间：" + image.getDate());
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    @Override
    public void onDownloadClick(GalleryImage image) {
        // 下载图片
        Toast.makeText(requireContext(), "开始下载: " + image.getTitle(), Toast.LENGTH_SHORT).show();
        // 这里可以添加实际的下载逻辑
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
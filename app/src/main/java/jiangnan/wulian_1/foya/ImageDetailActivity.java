package jiangnan.wulian_1.foya;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import jiangnan.wulian_1.foya.databinding.ActivityImageDetailBinding;
import jiangnan.wulian_1.foya.model.GalleryImage;

public class ImageDetailActivity extends AppCompatActivity {

    private ActivityImageDetailBinding binding;
    private GalleryImage image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 设置返回按钮
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("图片详情");
        }

        // 获取传递的图片数据
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            String imageUrl = intent.getStringExtra("imageUrl");
            String location = intent.getStringExtra("location");
            String date = intent.getStringExtra("date");

            image = new GalleryImage(0, title, description, imageUrl, "", location, date);
            displayImage();
        }
    }

    private void displayImage() {
        if (image != null) {
            // 设置图片信息
            binding.textTitle.setText(image.getTitle());
            binding.textDescription.setText(image.getDescription());
            binding.textLocation.setText(image.getLocation());
            binding.textDate.setText(image.getDate());

            // 加载图片
            if (image.getImageUrl() != null && !image.getImageUrl().isEmpty()) {
                if (image.getImageUrl().startsWith("gallery_image_")) {
                    // 本地drawable资源
                    int resourceId = getResources().getIdentifier(
                        image.getImageUrl(), "drawable", getPackageName());
                    if (resourceId != 0) {
                        binding.imageDetail.setImageResource(resourceId);
                    } else {
                        binding.imageDetail.setImageResource(R.drawable.ic_menu_camera);
                    }
                } else {
                    // 网络图片
                    Glide.with(this)
                            .load(image.getImageUrl())
                            .placeholder(R.drawable.ic_menu_camera)
                            .error(R.drawable.ic_menu_camera)
                            .into(binding.imageDetail);
                }
            } else {
                binding.imageDetail.setImageResource(R.drawable.ic_menu_camera);
            }

            // 设置按钮点击事件
            setupButtonListeners();
        }
    }

    private void setupButtonListeners() {
        binding.btnFavorite.setOnClickListener(v -> {
            Toast.makeText(this, "已添加到收藏", Toast.LENGTH_SHORT).show();
        });

        binding.btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "分享航拍图片");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "分享一张美丽的航拍图片：" + image.getTitle() + 
                    "\n拍摄地点：" + image.getLocation() + 
                    "\n拍摄时间：" + image.getDate());
            startActivity(Intent.createChooser(shareIntent, "分享到"));
        });

        binding.btnDownload.setOnClickListener(v -> {
            Toast.makeText(this, "开始下载: " + image.getTitle(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 
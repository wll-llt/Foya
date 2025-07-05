package jiangnan.wulian_1.foya.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import jiangnan.wulian_1.foya.R;
import jiangnan.wulian_1.foya.model.GalleryImage;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<GalleryImage> imageList;
    private Context context;
    private OnImageClickListener listener;

    public interface OnImageClickListener {
        void onImageClick(GalleryImage image);
        void onFavoriteClick(GalleryImage image, boolean isFavorite);
        void onShareClick(GalleryImage image);
        void onDownloadClick(GalleryImage image);
    }

    public GalleryAdapter(Context context, List<GalleryImage> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.listener = listener;
    }

    public void updateData(List<GalleryImage> newImageList) {
        this.imageList = newImageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery_image, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        GalleryImage image = imageList.get(position);
        holder.bind(image);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleText;
        TextView locationText;
        TextView dateText;
        MaterialButton favoriteBtn;
        MaterialButton shareBtn;
        MaterialButton downloadBtn;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_gallery);
            titleText = itemView.findViewById(R.id.text_title);
            locationText = itemView.findViewById(R.id.text_location);
            dateText = itemView.findViewById(R.id.text_date);
            favoriteBtn = itemView.findViewById(R.id.btn_favorite);
            shareBtn = itemView.findViewById(R.id.btn_share);
            downloadBtn = itemView.findViewById(R.id.btn_download);
        }

        public void bind(GalleryImage image) {
            // 设置图片
            if (image.getImageUrl() != null && !image.getImageUrl().isEmpty()) {
                // 检查是否是本地drawable资源
                if (image.getImageUrl().startsWith("gallery_image_")) {
                    // 本地drawable资源
                    int resourceId = context.getResources().getIdentifier(
                        image.getImageUrl(), "drawable", context.getPackageName());
                    if (resourceId != 0) {
                        imageView.setImageResource(resourceId);
                    } else {
                        imageView.setImageResource(R.drawable.ic_menu_camera);
                    }
                } else {
                    // 网络图片
                    Glide.with(context)
                            .load(image.getImageUrl())
                            .placeholder(R.drawable.ic_menu_camera)
                            .error(R.drawable.ic_menu_camera)
                            .centerCrop()
                            .into(imageView);
                }
            } else {
                // 使用默认图片
                imageView.setImageResource(R.drawable.ic_menu_camera);
            }

            // 设置文本信息
            titleText.setText(image.getTitle());
            locationText.setText(image.getLocation());
            dateText.setText(image.getDate());

            // 设置收藏按钮状态
            updateFavoriteButton(image.isFavorite());

            // 设置点击事件
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onImageClick(image);
                }
            });

            favoriteBtn.setOnClickListener(v -> {
                if (listener != null) {
                    boolean newFavoriteState = !image.isFavorite();
                    image.setFavorite(newFavoriteState);
                    updateFavoriteButton(newFavoriteState);
                    listener.onFavoriteClick(image, newFavoriteState);
                }
            });

            shareBtn.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onShareClick(image);
                }
            });

            downloadBtn.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDownloadClick(image);
                }
            });
        }

        private void updateFavoriteButton(boolean isFavorite) {
            if (isFavorite) {
                favoriteBtn.setIconResource(R.drawable.ic_menu_camera); // 这里应该使用心形图标
                favoriteBtn.setText("已收藏");
            } else {
                favoriteBtn.setIconResource(R.drawable.ic_menu_camera); // 这里应该使用空心心形图标
                favoriteBtn.setText("收藏");
            }
        }
    }
} 
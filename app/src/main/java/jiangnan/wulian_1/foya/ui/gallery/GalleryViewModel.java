package jiangnan.wulian_1.foya.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import jiangnan.wulian_1.foya.model.GalleryImage;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<List<GalleryImage>> images;
    private MutableLiveData<String> selectedCategory;
    private List<GalleryImage> allImages;

    public GalleryViewModel() {
        images = new MutableLiveData<>();
        selectedCategory = new MutableLiveData<>();
        selectedCategory.setValue("全部");
        
        // 初始化示例数据
        initializeSampleData();
    }

    public LiveData<List<GalleryImage>> getImages() {
        return images;
    }

    public LiveData<String> getSelectedCategory() {
        return selectedCategory;
    }

    public void setCategory(String category) {
        selectedCategory.setValue(category);
        filterImagesByCategory(category);
    }

    private void initializeSampleData() {
        allImages = new ArrayList<>();
        
        // 添加示例图片数据，使用本地drawable资源
        allImages.add(new GalleryImage(1, "西湖春景", "美丽的西湖春季风光", "gallery_image_1", "风景", "杭州西湖", "2024-03-15"));
        allImages.add(new GalleryImage(2, "雷峰塔", "夕阳下的雷峰塔", "gallery_image_2", "建筑", "杭州西湖", "2024-03-14"));
        allImages.add(new GalleryImage(3, "断桥残雪", "雪后的断桥美景", "gallery_image_3", "风景", "杭州西湖", "2024-03-13"));
        allImages.add(new GalleryImage(4, "三潭印月", "湖心亭三潭印月", "gallery_image_1", "建筑", "杭州西湖", "2024-03-12"));
        allImages.add(new GalleryImage(5, "苏堤春晓", "苏堤春晓美景", "gallery_image_2", "风景", "杭州西湖", "2024-03-11"));
        allImages.add(new GalleryImage(6, "平湖秋月", "平湖秋月夜景", "gallery_image_3", "风景", "杭州西湖", "2024-03-10"));
        allImages.add(new GalleryImage(7, "花港观鱼", "花港观鱼景区", "gallery_image_1", "风景", "杭州西湖", "2024-03-09"));
        allImages.add(new GalleryImage(8, "南屏晚钟", "南屏晚钟古寺", "gallery_image_2", "建筑", "杭州西湖", "2024-03-08"));
        allImages.add(new GalleryImage(9, "双峰插云", "双峰插云奇景", "gallery_image_3", "风景", "杭州西湖", "2024-03-07"));
        allImages.add(new GalleryImage(10, "曲院风荷", "曲院风荷荷花", "gallery_image_1", "风景", "杭州西湖", "2024-03-06"));
        
        // 添加一些网络图片示例（使用免费的占位图片服务）
        allImages.add(new GalleryImage(11, "西湖全景", "西湖全景航拍", "https://picsum.photos/400/300?random=1", "风景", "杭州西湖", "2024-03-05"));
        allImages.add(new GalleryImage(12, "雷峰塔夜景", "雷峰塔夜景航拍", "https://picsum.photos/400/300?random=2", "建筑", "杭州西湖", "2024-03-04"));
        allImages.add(new GalleryImage(13, "断桥雪景", "断桥雪景航拍", "https://picsum.photos/400/300?random=3", "风景", "杭州西湖", "2024-03-03"));
        allImages.add(new GalleryImage(14, "湖心亭", "湖心亭航拍", "https://picsum.photos/400/300?random=4", "建筑", "杭州西湖", "2024-03-02"));
        allImages.add(new GalleryImage(15, "苏堤春色", "苏堤春色航拍", "https://picsum.photos/400/300?random=5", "风景", "杭州西湖", "2024-03-01"));
        
        // 初始显示所有图片
        images.setValue(allImages);
    }

    private void filterImagesByCategory(String category) {
        if ("全部".equals(category)) {
            images.setValue(allImages);
        } else {
            List<GalleryImage> filteredImages = new ArrayList<>();
            for (GalleryImage image : allImages) {
                if (category.equals(image.getCategory())) {
                    filteredImages.add(image);
                }
            }
            images.setValue(filteredImages);
        }
    }

    public void toggleFavorite(int imageId) {
        List<GalleryImage> currentImages = images.getValue();
        if (currentImages != null) {
            for (GalleryImage image : currentImages) {
                if (image.getId() == imageId) {
                    image.setFavorite(!image.isFavorite());
                    break;
                }
            }
            images.setValue(currentImages);
        }
    }

    public List<GalleryImage> getFavoriteImages() {
        List<GalleryImage> favoriteImages = new ArrayList<>();
        for (GalleryImage image : allImages) {
            if (image.isFavorite()) {
                favoriteImages.add(image);
            }
        }
        return favoriteImages;
    }
}
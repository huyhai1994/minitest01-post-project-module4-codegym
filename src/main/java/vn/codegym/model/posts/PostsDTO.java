package vn.codegym.model.posts;

import org.springframework.web.multipart.MultipartFile;
import vn.codegym.model.category.Category;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostsDTO {
    @NotEmpty(message = "Khong duoc de trong ten bai viet")
    private String title;

    @NotEmpty(message = "Khong duoc de trong noi dung")
    private String content;

    @Size(min = 10, message = "Mo ta nen it nhat 10 ki tu")
    @Size(max = 2000, message = "Mo ta khong duoc qua 2000 ki tu")
    private String shortDescription;

    private MultipartFile imageFile;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private Category category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = this.shortDescription;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}

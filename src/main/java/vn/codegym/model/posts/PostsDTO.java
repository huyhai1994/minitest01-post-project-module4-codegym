package vn.codegym.model.posts;

import org.springframework.web.multipart.MultipartFile;
import vn.codegym.model.category.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PostsDTO {
    @NotEmpty(message = "Khong duoc de trong ten bai viet")
    private String title;

    @NotBlank(message = "Khong duoc de trong noi dung")
    private String content;

    @NotNull(message = "Khong duoc de trong")
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

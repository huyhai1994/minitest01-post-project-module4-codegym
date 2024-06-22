package vn.codegym.model.posts;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostsDTO {
    @NotEmpty(message = "Khong duoc de trong ten bai viet")
    private String title;
    @NotEmpty(message = "Khong duoc de trong noi dung")
    private String content;
    @NotEmpty(message = "Khong duoc de trong mo ta ngan")
    @Size(min = 10, message = "Mo ta nen it nhat 10 ki tu")
    @Size(max = 2000, message = "Mo ta khong duoc qua 2000 ki tu")
    private String shortDescription;

    private MultipartFile imageFile;

    public @NotEmpty(message = "Khong duoc de trong ten bai viet") String getTitle() {
        return title;
    }

    public void setTitle(@NotEmpty(message = "Khong duoc de trong ten bai viet") String title) {
        this.title = title;
    }

    public @NotEmpty(message = "Khong duoc de trong noi dung") String getContent() {
        return content;
    }

    public void setContent(@NotEmpty(message = "Khong duoc de trong noi dung") String content) {
        this.content = content;
    }

    public @NotEmpty(message = "Khong duoc de trong mo ta ngan") @Size(min = 10, message = "Mo ta nen it nhat 10 ki tu") @Size(max = 2000, message = "Mo ta khong duoc qua 2000 ki tu") String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(@NotEmpty(message = "Khong duoc de trong mo ta ngan") @Size(min = 10, message = "Mo ta nen it nhat 10 ki tu") @Size(max = 2000, message = "Mo ta khong duoc qua 2000 ki tu") String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}

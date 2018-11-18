package cn.gh.fms.param;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 郭宏
 * @date on 2018-11-18.
 */
public class UpdatePhotoParam {

    /**
     * 用户头像
     */
    private MultipartFile photoFile;

    public MultipartFile getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(MultipartFile photoFile) {
        this.photoFile = photoFile;
    }

}

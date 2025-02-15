package me.fairygel.fbook.util;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageHelper {
    public boolean isImage(MultipartFile file) {
        String type = file.getContentType();

        if (type == null) return false;

        return type.startsWith("image/");
    }

    @SneakyThrows
    public byte[] getBytes(MultipartFile file) {
        if (file != null) return file.getBytes();
        return new byte[0];
    }
}

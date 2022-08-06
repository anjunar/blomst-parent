package com.anjunar.common.rest.api;

import com.anjunar.common.filedisk.Base64Resource;
import com.anjunar.common.filedisk.FileDiskUtils;
import com.anjunar.common.filedisk.Image;

import java.time.LocalDateTime;

/**
 * @author Patrick Bittner on 01.08.17.
 */
public class ImageType {

    private String name;

    private LocalDateTime lastModified;

    private String data = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static ImageType factory(Image harddiskFile) {
        if (harddiskFile == null) {
            return new ImageType();
        }
        ImageType image = new ImageType();
        image.setName(harddiskFile.getName());
        image.setLastModified(harddiskFile.getLastModified());
        image.setData(FileDiskUtils.buildBase64(harddiskFile.getType(), harddiskFile.getSubType(), harddiskFile.getData()));
        return image;
    }

    public static Image updater(ImageType imageType, Image image) {
        if (image == null) {
            return null;
        } else {
            Base64Resource process = FileDiskUtils.extractBase64(imageType.getData());

            image.setData(process.getData());
            image.setType(process.getType());
            image.setSubType(process.getSubType());

            image.setName(imageType.getName());
            image.setLastModified(imageType.getLastModified());
        }
        return image;
    }

}

package com.anjunar.blomst.shared.users.user;

import com.anjunar.common.filedisk.Base64Resource;
import com.anjunar.common.filedisk.FileDiskUtils;
import com.anjunar.common.filedisk.Image;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.objectmapper.Converter;

public class ImageConverter implements Converter<Image, ImageType> {

    @Override
    public ImageType factory(Image harddiskFile) {
        if (harddiskFile == null) {
            return new ImageType();
        }
        ImageType image = new ImageType();
        image.setId(harddiskFile.getId());
        image.setName(harddiskFile.getName());
        image.setLastModified(harddiskFile.getLastModified());
        image.setData(FileDiskUtils.buildBase64(harddiskFile.getType(), harddiskFile.getSubType(), harddiskFile.getData()));
        return image;
    }

    @Override
    public Image updater(ImageType imageType) {
        Image image = new Image();
        Base64Resource process = FileDiskUtils.extractBase64(imageType.getData());

        image.setData(process.getData());
        image.setType(process.getType());
        image.setSubType(process.getSubType());

        image.setName(imageType.getName());
        image.setLastModified(imageType.getLastModified());
        return image;
    }
}

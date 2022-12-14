package com.anjunar.blomst.control.users.user;

import com.anjunar.common.filedisk.Base64Resource;
import com.anjunar.common.filedisk.FileDiskUtils;
import com.anjunar.common.filedisk.Image;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;

public class ImageConverter implements MapperConverterType<Image, ImageType, Object> {

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
    public Image updater(ImageType imageType, Object context) {
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

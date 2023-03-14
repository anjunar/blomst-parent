package com.anjunar.blomst.control.users.user;

import com.anjunar.common.filedisk.Base64Resource;
import com.anjunar.common.filedisk.FileDiskUtils;
import com.anjunar.common.filedisk.Media;
import com.anjunar.common.rest.api.Thumbnail;
import com.anjunar.common.rest.api.MediaType;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;
import com.google.common.base.Strings;

import java.util.Objects;

public class ImageConverter implements MapperConverterType<Media, MediaType, Object> {

    @Override
    public MediaType factory(Media harddiskFile) {
        if (harddiskFile == null) {
            return new MediaType();
        }
        MediaType image = new MediaType();
        image.setId(harddiskFile.getId());
        image.setName(harddiskFile.getName());
        image.setLastModified(harddiskFile.getLastModified());
        image.setUrl("service/shared/media/" + harddiskFile.getId().toString());

        if (Objects.nonNull(harddiskFile.getThumbnail())) {
            Thumbnail cropped = new Thumbnail();
            cropped.setId(harddiskFile.getThumbnail().getId());
            cropped.setName(harddiskFile.getThumbnail().getName());
            cropped.setLastModified(harddiskFile.getThumbnail().getLastModified());
            image.setThumbnail(cropped);
            if (harddiskFile.getType().equals("image")) {
                image.setUrl("service/shared/media/" + harddiskFile.getThumbnail().getId().toString());
            }
        }
        return image;
    }

    @Override
    public Media updater(MediaType mediaType, Object context) {
        if (! Strings.isNullOrEmpty(mediaType.getData())) {
            Media media = new Media();
            Base64Resource process = FileDiskUtils.extractBase64(mediaType.getData());

            media.setData(process.getData());
            media.setType(process.getType());
            media.setSubType(process.getSubType());

            media.setName(mediaType.getName());
            media.setLastModified(mediaType.getLastModified());

            if (Objects.nonNull(mediaType.getThumbnail()) && ! Strings.isNullOrEmpty(mediaType.getThumbnail().getData())) {
                Media cropped = new Media();
                Base64Resource processCropped = FileDiskUtils.extractBase64(mediaType.getThumbnail().getData());
                cropped.setData(processCropped.getData());
                cropped.setType(processCropped.getType());
                cropped.setSubType(processCropped.getSubType());
                cropped.setName(mediaType.getName());
                cropped.setLastModified(mediaType.getLastModified());
                media.setThumbnail(cropped);
            }
            return media;
        }
        return null;
    }
}

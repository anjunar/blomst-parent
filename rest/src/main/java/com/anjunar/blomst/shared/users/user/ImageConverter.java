package com.anjunar.blomst.shared.users.user;

import com.anjunar.common.filedisk.Media;
import com.anjunar.common.rest.api.MediaType;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;

import java.util.Objects;

public class ImageConverter implements MapperConverterType<Media, MediaType, Object> {

    @Override
    public MediaType factory(Media harddiskFile) {
        if (harddiskFile == null) {
            return new MediaType();
        }
        MediaType image = new MediaType();

        Media thumbnail = harddiskFile.getThumbnail();
        if (Objects.nonNull(thumbnail)) {
            image.setId(thumbnail.getId());
            image.setName(thumbnail.getName());
            image.setLastModified(thumbnail.getLastModified());
            image.setUrl("service/shared/media/" + thumbnail.getId().toString());
        } else {
            image.setId(harddiskFile.getId());
            image.setName(harddiskFile.getName());
            image.setLastModified(harddiskFile.getLastModified());
            image.setUrl("service/shared/media/" + harddiskFile.getId().toString());
        }
        return image;
    }

    @Override
    public Media updater(MediaType mediaType, Object context) {
        return null;
    }
}

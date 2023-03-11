package com.anjunar.blomst.shared.users.user;

import com.anjunar.common.filedisk.FileDiskUtils;
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
        if (Objects.nonNull(harddiskFile.getThumbnail())) {
            image.setId(harddiskFile.getThumbnail().getId());
            image.setName(harddiskFile.getThumbnail().getName());
            image.setLastModified(harddiskFile.getThumbnail().getLastModified());
            image.setUrl("service/shared/media/" + harddiskFile.getId().toString());
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

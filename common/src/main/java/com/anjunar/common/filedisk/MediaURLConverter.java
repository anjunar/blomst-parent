package com.anjunar.common.filedisk;

import com.anjunar.common.rest.api.MediaType;
import com.anjunar.common.rest.api.Thumbnail;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;
import com.google.common.base.Strings;

import java.util.Objects;

public class MediaURLConverter implements MapperConverterType<Media, MediaType, Object> {

    @Override
    public MediaType factory(Media hardDiskFile) {
        if (hardDiskFile == null) {
            return new MediaType();
        }
        MediaType image = new MediaType();
        image.setId(hardDiskFile.getId());
        image.setName(hardDiskFile.getName());
        image.setLastModified(hardDiskFile.getLastModified());
        image.setUrl("service/shared/media/" + hardDiskFile.getId().toString());

        Media thumbnailMedia = hardDiskFile.getThumbnail();
        if (Objects.nonNull(thumbnailMedia)) {
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setId(thumbnailMedia.getId());
            thumbnail.setName(thumbnailMedia.getName());
            thumbnail.setLastModified(thumbnailMedia.getLastModified());
            thumbnail.setUrl("service/shared/media/" + thumbnailMedia.getId().toString());
            image.setThumbnail(thumbnail);
        }
        return image;
    }

    @Override
    public Media updater(MediaType mediaType, Object context) {
        return null;
    }
}

package com.anjunar.common.filedisk;

import com.anjunar.common.rest.api.MediaType;
import com.anjunar.common.rest.api.Thumbnail;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;
import com.google.common.base.Strings;

import java.util.Objects;

public class MediaDataConverter implements MapperConverterType<Media, MediaType, Object> {

    @Override
    public MediaType factory(Media hardDiskFile) {
        if (hardDiskFile == null) {
            return new MediaType();
        }

        MediaType image = new MediaType();
        image.setId(hardDiskFile.getId());
        image.setName(hardDiskFile.getName());
        image.setLastModified(hardDiskFile.getLastModified());
        image.setData(FileDiskUtils.buildBase64(hardDiskFile.getType(), hardDiskFile.getSubType(), hardDiskFile.getData()));

        if (Objects.nonNull(hardDiskFile.getThumbnail())) {
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setId(hardDiskFile.getThumbnail().getId());
            thumbnail.setName(hardDiskFile.getThumbnail().getName());
            thumbnail.setLastModified(hardDiskFile.getThumbnail().getLastModified());
            thumbnail.setData(FileDiskUtils.buildBase64(hardDiskFile.getThumbnail().getType(), hardDiskFile.getThumbnail().getSubType(), hardDiskFile.getThumbnail().getData()));
            image.setThumbnail(thumbnail);
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
                Media thumbnail = new Media();
                Base64Resource resource = FileDiskUtils.extractBase64(mediaType.getThumbnail().getData());
                thumbnail.setData(resource.getData());
                thumbnail.setType(resource.getType());
                thumbnail.setSubType(resource.getSubType());
                thumbnail.setName(mediaType.getName());
                thumbnail.setLastModified(mediaType.getLastModified());
                media.setThumbnail(thumbnail);
            }
            return media;
        }
        return null;
    }
}

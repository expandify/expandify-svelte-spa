package dev.kenowi.exportify.domain.entities.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class Image {

    private String url;
    private Integer height;
    private Integer width;

}
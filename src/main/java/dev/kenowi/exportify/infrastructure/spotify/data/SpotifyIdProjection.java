package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SpotifyIdProjection {

    @JsonProperty("type")
    private SpotifyObjectType type;

    @JsonProperty("id")
    private String id;
}
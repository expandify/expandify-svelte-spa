package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.models.Album;
import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.models.Track;
import de.wittenbude.exportify.spotify.data.SpotifyAlbum;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AlbumConverter {

    public static Album from(SpotifyAlbum spotifyAlbum, Set<Artist> artists, List<Track> tracks) {
        return new Album()
                .setId(null)
                .setAlbumType(spotifyAlbum.getAlbumType().getType())
                .setTotalTracks(spotifyAlbum.getTotalTracks())
                .setAvailableMarkets(Arrays.asList(spotifyAlbum.getAvailableMarkets()))
                .setExternalUrls(spotifyAlbum.getExternalUrls())
                .setHref(spotifyAlbum.getHref())
                .setSpotifyID(spotifyAlbum.getId())
                .setImages(ImageConverter.from(spotifyAlbum.getImages()))
                .setName(spotifyAlbum.getName())
                .setPopularity(spotifyAlbum.getPopularity())
                .setReleaseDatePrecision(EnumConverters.from(spotifyAlbum.getReleaseDatePrecision()))
                .setRestrictions(spotifyAlbum.getRestrictions().getReason())
                .setSpotifyObjectType(spotifyAlbum.getType().getType())
                .setUri(spotifyAlbum.getUri())
                .setArtists(artists)
                .setTracks(tracks)
                .setCopyrights(CopyrightConverter.from(spotifyAlbum.getCopyrights()))
                .setExternalIDs(ExternalIDsConverter.from(spotifyAlbum.getExternalIDs()))
                .setGenres(Arrays.asList(spotifyAlbum.getGenres()))
                .setLabel(spotifyAlbum.getLabel())
                .setPopularity(spotifyAlbum.getPopularity());
    }

}

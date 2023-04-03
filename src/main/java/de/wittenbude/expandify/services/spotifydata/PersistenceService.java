package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.*;
import de.wittenbude.expandify.models.spotifydata.helper.PlaylistTrack;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import de.wittenbude.expandify.repositories.spotifydata.*;
import de.wittenbude.expandify.requestscope.AuthenticatedUserData;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.List;
import java.util.Optional;

@Service
class PersistenceService {

    private final AlbumRepository albumRepository;
    private final AlbumSimplifiedRepository albumSimplifiedRepository;
    private final ArtistRepository artistRepository;
    private final ArtistSimplifiedRepository artistSimplifiedRepository;
    private final EpisodeRepository episodeRepository;
    private final PlaylistRepository playlistRepository;
    private final PlaylistSimplifiedRepository playlistSimplifiedRepository;
    private final ShowSimplifiedRepository showSimplifiedRepository;
    private final SpotifyUserRepository spotifyUserRepository;
    private final TrackRepository trackRepository;
    private final TrackSimplifiedRepository trackSimplifiedRepository;

    public PersistenceService(
            AlbumRepository albumRepository,
            AlbumSimplifiedRepository albumSimplifiedRepository,
            ArtistRepository artistRepository,
            ArtistSimplifiedRepository artistSimplifiedRepository,
            EpisodeRepository episodeRepository,
            PlaylistRepository playlistRepository,
            PlaylistSimplifiedRepository playlistSimplifiedRepository,
            ShowSimplifiedRepository showSimplifiedRepository,
            SpotifyUserRepository spotifyUserRepository,
            TrackRepository trackRepository,
            TrackSimplifiedRepository trackSimplifiedRepository
    ) {
        this.albumRepository = albumRepository;
        this.albumSimplifiedRepository = albumSimplifiedRepository;
        this.artistRepository = artistRepository;
        this.artistSimplifiedRepository = artistSimplifiedRepository;
        this.episodeRepository = episodeRepository;
        this.playlistRepository = playlistRepository;
        this.playlistSimplifiedRepository = playlistSimplifiedRepository;
        this.showSimplifiedRepository = showSimplifiedRepository;
        this.spotifyUserRepository = spotifyUserRepository;
        this.trackRepository = trackRepository;
        this.trackSimplifiedRepository = trackSimplifiedRepository;
    }

    public Album save(Album album) {
        if (album.getTracks() != null) {
            List<TrackSimplified> tracks = album.getTracks().stream().map(this::save).toList();
            album.setTracks(tracks);
        }

        if (album.getArtists() != null) {
            List<ArtistSimplified> artists = this.saveAll(album.getArtists());
            album.setArtists(artists);
        }

        return albumRepository.save(album);
    }


    public Optional<Album> find(Album album) {
        return albumRepository.findById(album.getId());
    }

    public Optional<Album> findAlbum(String id) {
        return albumRepository.findById(id);
    }

    public AlbumSimplified save(AlbumSimplified album) {
        if (album.getArtists() != null) {
            List<ArtistSimplified> artists = this.saveAll(album.getArtists());
            album.setArtists(artists);
        }

        return albumSimplifiedRepository.save(album);
    }

    public Optional<AlbumSimplified> find(AlbumSimplified album) {
        return albumSimplifiedRepository.findById(album.getId());
    }

    public SavedAlbum save(SavedAlbum album) {
        Album saved = this.save(album.getAlbum());
        return new SavedAlbum(saved, album.getAddedAt());
    }

    public Optional<SavedAlbum> find(SavedAlbum album) {
        return albumRepository.findById(album.getAlbum().getId())
                .map(a -> new SavedAlbum(a, album.getAddedAt()));
    }

    public Artist save(Artist artist) {
        return artistRepository.save(artist);
    }

    public Optional<Artist> find(Artist artist) {
        return artistRepository.findById(artist.getId());
    }

    public Optional<Artist> findArtist(String id) {
        return artistRepository.findById(id);
    }

    public ArtistSimplified save(ArtistSimplified artist) {
        return artistSimplifiedRepository.save(artist);
    }

    public Optional<ArtistSimplified> find(ArtistSimplified artistSimplified) {
        return artistSimplifiedRepository.findById(artistSimplified.getId());
    }

    private List<ArtistSimplified> saveAll(List<ArtistSimplified> artists) {
        return artistSimplifiedRepository.saveAll(artists);
    }

    public Episode save(Episode episode) {
        if (episode.getShow() != null) {
            ShowSimplified show = this.save(episode.getShow());
            episode.setShow(show);
        }
        return episodeRepository.save(episode);
    }

    public Optional<Episode> find(Episode episode) {
        return episodeRepository.findById(episode.getId());
    }

    public PlaylistSimplified save(PlaylistSimplified playlistSimplified) {
        if (playlistSimplified.getOwner() != null) {
            SpotifyUser owner = this.save(playlistSimplified.getOwner());
            playlistSimplified.setOwner(owner);
        }

        if (playlistSimplified.getTracks() != null) {
            List<PlaylistTrack> tracks = playlistSimplified.getTracks().stream().map(this::save).toList();
            playlistSimplified.setTracks(tracks);
        }

        return playlistSimplifiedRepository.save(playlistSimplified);
    }

    public Optional<PlaylistSimplified> find(PlaylistSimplified playlistSimplified) {
        return playlistSimplifiedRepository.findById(playlistSimplified.getId());
    }

    public Playlist save(Playlist playlist) {
        if (playlist.getTracks() != null) {
            List<PlaylistTrack> tracks = playlist.getTracks().stream().map(this::save).toList();
            playlist.setTracks(tracks);
        }

        if (playlist.getOwner() != null) {
            SpotifyUser owner = this.save(playlist.getOwner());
            playlist.setOwner(owner);
        }

        return playlistRepository.save(playlist);
    }

    public Optional<Playlist> find(Playlist playlist) {
        return playlistRepository.findById(playlist.getId());
    }

    public Optional<Playlist> findPlaylist(String id) {
        return playlistRepository.findById(id);
    }

    public ShowSimplified save(ShowSimplified showSimplified) {
        return showSimplifiedRepository.save(showSimplified);
    }

    public Optional<ShowSimplified> find(ShowSimplified showSimplified) {
        return showSimplifiedRepository.findById(showSimplified.getId());
    }

    public SpotifyUser save(SpotifyUser spotifyUser) {
        return spotifyUserRepository.save(spotifyUser);
    }

    public Optional<SpotifyUser> find(AuthenticatedUserData authenticatedUserData) {
        return spotifyUserRepository.findById(authenticatedUserData.getSpotifyUserId());
    }

    public Optional<SpotifyUser> findSpotifyUser(String id) {
        return spotifyUserRepository.findById(id);
    }

    public Optional<SpotifyUser> find(SpotifyUser spotifyUser) {
        return spotifyUserRepository.findById(spotifyUser.getId());
    }

    public Track save(Track track) {
        if (track.getAlbum() != null) {
            AlbumSimplified album = this.save(track.getAlbum());
            track.setAlbum(album);
        }

        if (track.getArtists() != null) {
            List<ArtistSimplified> artists = this.saveAll(track.getArtists());
            track.setArtists(artists);
        }

        return trackRepository.save(track);
    }

    public Optional<Track> find(Track track) {
        return trackRepository.findById(track.getId());
    }

    public Optional<Track> findTrack(String id) {
        return trackRepository.findById(id);
    }

    public TrackSimplified save(TrackSimplified trackSimplified) {
        if (trackSimplified.getArtists() != null) {
            List<ArtistSimplified> artists = this.saveAll(trackSimplified.getArtists());
            trackSimplified.setArtists(artists);
        }

        return trackSimplifiedRepository.save(trackSimplified);
    }

    public Optional<TrackSimplified> find(TrackSimplified trackSimplified) {
        return trackSimplifiedRepository.findById(trackSimplified.getId());
    }

    public PlaylistTrack save(PlaylistTrack playlistTrack) {
        SpotifyUser owner = this.save(playlistTrack.getAddedBy());
        playlistTrack.setAddedBy(owner);

        if (playlistTrack.getType() == ModelObjectType.TRACK) {
            Track track = this.save(playlistTrack.getTrack());
            playlistTrack.setTrack(track);
        }

        if (playlistTrack.getType() == ModelObjectType.EPISODE) {
            Episode episode = this.save(playlistTrack.getEpisode());
            playlistTrack.setEpisode(episode);

        }
        return playlistTrack;
    }

    public Optional<PlaylistTrack> find(PlaylistTrack playlistTrack) {
        if (playlistTrack.getType() == ModelObjectType.TRACK) {
            return this.find(playlistTrack.getTrack())
                    .map(track -> {
                        playlistTrack.setTrack(track);
                        return playlistTrack;
                    });

        }

        if (playlistTrack.getType() == ModelObjectType.EPISODE) {
            return this.find(playlistTrack.getEpisode())
                    .map(episode -> {
                        playlistTrack.setEpisode(episode);
                        return playlistTrack;
                    });
        }

        return Optional.empty();
    }

    public SavedTrack save(SavedTrack savedTrack) {
        return new SavedTrack(
                this.save(savedTrack.getTrack()),
                savedTrack.getAddedAt()
        );
    }

    public Optional<SavedTrack> find(SavedTrack savedTrack) {
        return this.find(savedTrack.getTrack())
                .map(track -> new SavedTrack(track, savedTrack.getAddedAt()));
    }

}

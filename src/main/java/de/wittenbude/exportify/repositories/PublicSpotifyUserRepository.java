package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.PublicSpotifyUser;
import de.wittenbude.exportify.models.comparators.UserEquality;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;


public interface PublicSpotifyUserRepository extends CrudRepository<PublicSpotifyUser, UUID> {

    @Query("select a from PublicSpotifyUser a where a.spotifyID = ?1 order by a.versionTimestamp desc limit 1")
    Optional<PublicSpotifyUser> findLatest(String spotifyID);

    default PublicSpotifyUser upsert(PublicSpotifyUser publicSpotifyUser) {
        return this.findLatest(publicSpotifyUser.getSpotifyID())
                .filter(u -> UserEquality.equals(u, publicSpotifyUser))
                .orElseGet(() -> this.save(publicSpotifyUser));
    }
}

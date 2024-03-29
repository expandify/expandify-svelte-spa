package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.SpotifyCredentials;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CredentialsRepository extends CrudRepository<SpotifyCredentials, UUID> {

    @Query("select s from SpotifyCredentials s where s.exportifyUser.id = :userID")
    Optional<SpotifyCredentials> findByUserID(UUID userID);


    default SpotifyCredentials upsert(SpotifyCredentials spotifyCredentials) {
        this.findByUserID(spotifyCredentials.getExportifyUser().getId())
                .map(SpotifyCredentials::getId)
                .ifPresent(spotifyCredentials::setId);

        return this.save(spotifyCredentials);
    }
}
package de.wittenbude.exportify.models;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.exportify.models.embeds.ExternalIDs;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToMany
    @JoinTable
    private Set<Artist> artists = new LinkedHashSet<>();

    @ElementCollection
    private Set<CountryCode> availableMarkets;
    private Integer discNumber;
    private Integer durationMs;
    private Boolean explicit;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> externalUrls;
    private String href;

    @Column(unique = true)
    private String spotifyID;
    private Boolean isPlayable;
    private String restrictions;
    private String name;
    private String previewUrl;
    private Integer trackNumber;
    private String spotifyObjectType;
    private String uri;
    private Boolean isLocal;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Album album;

    private ExternalIDs externalIDs;
    private Integer popularity;


    public Track setArtists(Set<Artist> artists) {
        if (this.artists == null) {
            this.artists = artists;
            return this;
        }
        this.artists.clear();
        this.artists.addAll(artists);
        return this;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Track track = (Track) o;
        return getId() != null && Objects.equals(getId(), track.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Singer")
@NamedQueries({
        @NamedQuery(name = "Singer.findAllWithAlbum",query = "Select distinct s from Singer s left join fetch s.albums a left join fetch s.instruments i"),
        @NamedQuery(name = "Singer.findById",query = "Select distinct s from Singer s left join fetch s.albums a left join fetch s.instruments i where s.id=:id")
})
public class Singer implements Serializable {

    private Long id;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    private String firstName;
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    private String lastName;
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private Date birthDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


    private int version;
    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }


    private Set<Album> albums = new HashSet<>();
    @OneToMany( mappedBy = "singer",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    public Set<Album> getAlbums() {
        return albums;
    }
    public boolean addAlbum (Album album){
        album.setSinger(this);
        return getAlbums().add(album);
    }
    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
    public void removeAlbum(Album album){
        getAlbums().remove(album);
    }


    private Set<Instrument> instruments = new HashSet<>();
    @ManyToMany
    @JoinTable( name = "singer_instrument",
                joinColumns = @JoinColumn(name = "SINGER_ID"),
                inverseJoinColumns = @JoinColumn(name = "INSTRUMENT_ID"))
    public Set<Instrument> getInstruments() {
        return instruments;
    }
    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }


    @Override
    public String toString() {
        return "Singer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", version=" + version +
                '}';
    }
}
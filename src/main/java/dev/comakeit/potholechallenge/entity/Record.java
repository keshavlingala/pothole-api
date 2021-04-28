package dev.comakeit.potholechallenge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "records")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "record_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID recordId;

    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

//    @JoinColumn(name = "zipcode")
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "lat")
    private Double lat;
    @Column(name = "lng")
    private Double lng;
    @Column(name = "description")
    private String description;
    @Column(name = "img_url")
    private String img;

    @ManyToOne
    @JoinColumn(name="cluster", referencedColumnName = "zipcode", insertable = false, updatable = false)
    private Cluster cluster;



    public Record(UUID userId, String zipcode, Double lat, Double lng, String description, String imgUrl) {
        this.recordId = UUID.randomUUID();
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
        this.description = description;
        this.zipcode = zipcode;
        this.img = imgUrl;
    }
}

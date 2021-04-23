package dev.comakeit.potholechallenge.entity;

import dev.comakeit.potholechallenge.models.BidStatus;
import dev.comakeit.potholechallenge.models.ClusterStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clusters")
public class Cluster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "zipcode", nullable = false, columnDefinition = "varchar(50)")
    private String zipcode;

    @Column(name = "contractor_id", columnDefinition = "BINARY(16)")
    @Nullable
    private UUID contractorId;

    @Column(name = "status")
    @Nullable
    private ClusterStatus status;

    @OneToMany(targetEntity = Record.class, mappedBy = "cluster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Transient
    private List<Record> records = new ArrayList<>();

    public Cluster(String zipcode, @Nullable UUID contractorId, @Nullable ClusterStatus status) {
        this.zipcode = zipcode;
        this.contractorId = contractorId;
        this.status = status;
    }

    //    @OneToMany(targetEntity = Record.class, mappedBy = "clusterId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Record> user = new ArrayList<>();


//    @OneToMany(mappedBy = "cluster")
//    private Set<Record> records = new HashSet<>();
}

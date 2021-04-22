package dev.comakeit.potholechallenge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bids")
public class Bid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "bid_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID bidId;

    @Column(name = "contractor_id", nullable = false)
    private Integer contractorId;

    @Column(name = "cluster_id")
    private Integer clusterId;

    @Column(name = "bid_ammount")
    private Double bidAmmount;

}

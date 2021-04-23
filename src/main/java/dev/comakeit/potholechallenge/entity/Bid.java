package dev.comakeit.potholechallenge.entity;

import dev.comakeit.potholechallenge.models.BidStatus;
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

    @Column(name = "contractor_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID contractorId;

    @Column(name = "cluster_id")
    private String clusterId;

    @Column(name = "bid_amount")
    private Double bidAmount;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private BidStatus status;

    public Bid(UUID contractorId, String clusterId, Double bidAmount, String description) {
        this.bidId = UUID.randomUUID();
        this.contractorId = contractorId;
        this.clusterId = clusterId;
        this.bidAmount = bidAmount;
        this.description = description;
        this.status = BidStatus.PENDING;
    }
}

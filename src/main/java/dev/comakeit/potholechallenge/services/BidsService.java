package dev.comakeit.potholechallenge.services;

import dev.comakeit.potholechallenge.entity.Bid;
import dev.comakeit.potholechallenge.entity.Cluster;
import dev.comakeit.potholechallenge.entity.User;
import dev.comakeit.potholechallenge.models.BidStatus;
import dev.comakeit.potholechallenge.models.ClusterStatus;
import dev.comakeit.potholechallenge.repositories.BidsRepository;
import dev.comakeit.potholechallenge.repositories.ClustersRepository;
import dev.comakeit.potholechallenge.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BidsService {
    @Autowired
    BidsRepository bidsRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    ClustersRepository clustersRepository;

    @Transactional(isolation = Isolation.DEFAULT)
    public Boolean approveBid(UUID bidId) {
        try {
            Bid bid = bidsRepository.findBidBybidId(bidId);
            User contractor = usersRepository.findUserByuserId(bid.getContractorId());
            Cluster cluster = clustersRepository.findClusterByzipcode(bid.getClusterId());
            List<Bid> rejectBids = bidsRepository.findBidsByclusterId(bid.getClusterId());
            cluster.setContractorId(contractor.getUserId());
            cluster.setStatus(ClusterStatus.ASSIGNED);
            rejectBids.stream().forEach(bid1 -> {
                if (bid1.getBidId().equals(bid.getBidId()))
                    bid1.setStatus(BidStatus.APPROVED);
                else
                    bid1.setStatus(BidStatus.REJECTED);
                bidsRepository.save(bid);
            });
            return true;
        } catch (Exception e) {
            log.error("Unable to Approve Bid", e);
            return false;
        }
    }
}

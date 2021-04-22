package dev.comakeit.potholechallenge.repositories;

import dev.comakeit.potholechallenge.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface BidsRepository extends JpaRepository<Bid, Integer>, JpaSpecificationExecutor<Bid> {

}
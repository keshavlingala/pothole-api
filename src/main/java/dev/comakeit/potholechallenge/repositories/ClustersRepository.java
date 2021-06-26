package dev.comakeit.potholechallenge.repositories;

import dev.comakeit.potholechallenge.entity.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface ClustersRepository extends JpaRepository<Cluster, String>, JpaSpecificationExecutor<Cluster> {
    Boolean existsByzipcode(String id);

    Cluster findClusterByzipcode(String id);

    @Query("SELECT c FROM Cluster c WHERE c.status=0")
    List<Cluster> findOpenContracts();

    List<Cluster> findClustersBycontractorId(UUID id);

    @Query("SELECT c FROM Cluster c where c.contractorId IS NOT NULL")
    List<Cluster> getApprovedContracts();
}
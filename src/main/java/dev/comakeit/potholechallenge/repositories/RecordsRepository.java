package dev.comakeit.potholechallenge.repositories;

import dev.comakeit.potholechallenge.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface RecordsRepository extends JpaRepository<Record, Integer>, JpaSpecificationExecutor<Record> {
    //    @Query("SELECT r from Record r where r.userId=?")
    List<Record> findRecordsByuserId(UUID uuid);

    List<Record> findRecordsByzipcode(String string);
}
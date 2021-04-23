package dev.comakeit.potholechallenge.repositories;

import dev.comakeit.potholechallenge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface UsersRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findUserByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.licence IS NOT NULL AND u.roles NOT LIKE '%CONTRACTOR%'")
    List<User> getAllContractorApplications();

    User findUserByuserId(UUID id);

}

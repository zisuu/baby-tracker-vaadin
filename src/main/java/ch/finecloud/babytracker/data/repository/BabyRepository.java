package ch.finecloud.babytracker.data.repository;

import ch.finecloud.babytracker.data.entity.Baby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BabyRepository extends JpaRepository<Baby, Long> {

    @Query("""
            SELECT b FROM Baby b
            WHERE LOWER(b.name)
            LIKE LOWER(CONCAT('%', :searchTerm, '%'))
            AND b.userAccount.email = :email
            """)
    List<Baby> searchBabiesByUserAccount_Email(@Param("searchTerm") String searchTerm, @Param("email") String email);

    List<Baby> findBabyByUserAccount_Email(String email);

    Optional<Baby> findBabyByName(String name);
}

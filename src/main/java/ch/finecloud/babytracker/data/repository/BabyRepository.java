package ch.finecloud.babytracker.data.repository;

import ch.finecloud.babytracker.data.entity.Baby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BabyRepository extends JpaRepository<Baby, Long> {

    @Query("select b from Baby b " +
            "where lower(b.name) like lower(concat('%', :searchTerm, '%')) ")
    List<Baby> search(@Param("searchTerm") String searchTerm);
}

package ch.finecloud.babytracker.data.repository;

import ch.finecloud.babytracker.data.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select c from Event c " +
            "where lower(c.baby.name) like lower(concat('%', :searchTerm, '%')) ")
    List<Event> search(@Param("searchTerm") String searchTerm);
}

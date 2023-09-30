package ch.finecloud.babytracker.data.repository;

import ch.finecloud.babytracker.data.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event e " +
            "where lower(e.notes) like lower(concat('%', :searchTerm, '%')) ")
    List<Event> search(@Param("searchTerm") String searchTerm);
}

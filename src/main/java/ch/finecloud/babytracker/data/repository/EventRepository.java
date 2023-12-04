package ch.finecloud.babytracker.data.repository;

import ch.finecloud.babytracker.data.dto.BabySleepPerDay;
import ch.finecloud.babytracker.data.dto.EventTypeNumber;
import ch.finecloud.babytracker.data.entity.Event;
import ch.finecloud.babytracker.data.entity.EventType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    @Query("""
            SELECT e FROM Event e
            WHERE LOWER(e.notes)
            LIKE LOWER(CONCAT('%', :searchTerm, '%'))
            AND e.baby.userAccount.email = :email
            """)
    List<Event> searchEventsByBaby_UserAccount_Email(@Param("searchTerm") String searchTerm, @Param("email") String email);

    List<Event> findEventsByBaby_UserAccount_Email(String email);

    long countEventsByBaby_UserAccount_Email(String email);

    long countEventsByBaby_UserAccount_EmailAndBabyName(String email, String babyName);

    Event findTopByBaby_NameOrderByStartDateDesc(String babyName);

    @Query("""
            SELECT NEW ch.finecloud.babytracker.data.dto.EventTypeNumber(e.eventType, count(e))
            FROM Event e
            WHERE e.baby.userAccount.email = :email
            GROUP BY e.eventType
            """)
    List<EventTypeNumber> numberOfEventsPerEventType(@Param("email") String email);

    @Query("""
            SELECT new ch.finecloud.babytracker.data.dto.BabySleepPerDay(
                DATE(e.startDate),
                CAST(FUNCTION('TIME_TO_SEC', FUNCTION('TIMEDIFF', e.endDate, e.startDate)) AS java.lang.Long)
            )
            FROM Event e
            WHERE e.eventType = 'SLEEPING'
            AND e.baby.userAccount.email = :email
            AND e.startDate >= :startDate
            """)
    List<BabySleepPerDay> findBabySleepPerDay(@Param("email") String email, @Param("startDate") LocalDateTime startDate);
}

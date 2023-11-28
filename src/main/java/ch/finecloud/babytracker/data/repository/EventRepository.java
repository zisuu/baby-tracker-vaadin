package ch.finecloud.babytracker.data.repository;

import ch.finecloud.babytracker.data.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    @Query("select e from Event e " +
            "where lower(e.notes) like lower(concat('%', :searchTerm, '%')) and e.baby.userAccount.email = :email")
    List<Event> searchEventsByBaby_UserAccount_Email(@Param("searchTerm") String searchTerm, @Param("email") String email);

    List<Event> findEventsByBaby_UserAccount_Email(String email);

    long countEventsByBaby_UserAccount_Email(String email);
    long countEventsByBaby_UserAccount_EmailAndBabyName(String email, String babyName);

    Event findTopByBaby_NameOrderByStartDateDesc(String babyName);
}

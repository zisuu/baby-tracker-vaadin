package ch.finecloud.babytracker.data.repository;

import ch.finecloud.babytracker.data.entity.Baby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BabyRepository extends JpaRepository<Baby, Long> {

}

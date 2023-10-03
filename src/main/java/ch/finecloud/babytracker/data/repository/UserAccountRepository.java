package ch.finecloud.babytracker.data.repository;

import ch.finecloud.babytracker.data.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findUserAccountByEmail(String email);
}

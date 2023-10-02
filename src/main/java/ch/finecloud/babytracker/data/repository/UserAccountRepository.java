package ch.finecloud.babytracker.data.repository;

import ch.finecloud.babytracker.data.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    List<UserAccount> findAllByEmailIsLikeIgnoreCase(String username);

    Optional<UserAccount> findUserAccountByEmail(String email);
}

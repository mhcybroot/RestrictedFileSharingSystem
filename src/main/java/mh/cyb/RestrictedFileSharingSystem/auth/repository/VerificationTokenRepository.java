package mh.cyb.RestrictedFileSharingSystem.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mh.cyb.RestrictedFileSharingSystem.auth.entity.User;
import mh.cyb.RestrictedFileSharingSystem.auth.entity.VerificationToken;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    Optional<VerificationToken> findByUser(User user);

    void deleteByUser(User user);
}

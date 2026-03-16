package backend.lostandfound.repo;

import backend.lostandfound.model.UserProfile;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepo extends JpaRepository<UserProfile,Long> {
    @Override
    Optional<UserProfile> findById(Long aLong);

    Optional<UserProfile> findByregNo(Long id);

    Boolean existsByRegNo(Long regNo);
    Optional<UserProfile> findByEmail(String a);
}

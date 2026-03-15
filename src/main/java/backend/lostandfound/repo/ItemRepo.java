package backend.lostandfound.repo;

import backend.lostandfound.model.ItemTable;
import backend.lostandfound.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepo extends JpaRepository<ItemTable,Long> {
    @Override
    Optional<ItemTable> findById(Long aLong);
}

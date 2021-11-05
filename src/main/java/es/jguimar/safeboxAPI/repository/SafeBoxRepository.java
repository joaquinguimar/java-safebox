package es.jguimar.safeboxAPI.repository;

import es.jguimar.safeboxAPI.entity.SafeBox;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SafeBoxRepository extends MongoRepository<SafeBox, String> {

    Optional<SafeBox> findByName(String name);

}

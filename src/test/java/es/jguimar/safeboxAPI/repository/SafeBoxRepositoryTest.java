package es.jguimar.safeboxAPI.repository;

import es.jguimar.safeboxAPI.entity.SafeBox;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class SafeBoxRepositoryTest {

  public static final String NAME_SAVED = "name saved";

  @Autowired
  SafeBoxRepository safeBoxRepository;

  @Autowired MongoTemplate mongoTemplate;

  @Test
  public void findByName_shouldReturnOK() {
    // given
    DBObject objectToSave = BasicDBObjectBuilder.start().add("name", NAME_SAVED).get();

    // when
    mongoTemplate.save(objectToSave, "safebox");

    Optional<SafeBox> safebox = safeBoxRepository.findByName(NAME_SAVED);

    // then
    assertThat(safebox.get().getName()).isEqualTo(NAME_SAVED);
  }
}

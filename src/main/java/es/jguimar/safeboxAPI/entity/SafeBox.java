package es.jguimar.safeboxAPI.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("safebox")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SafeBox {

    @Id
    private String id;

    private String name;

    private String password;

    private List<String> items;

    private int incorrectAttempts;

    public Boolean isLocked() {
        return incorrectAttempts > 2;
    }


}

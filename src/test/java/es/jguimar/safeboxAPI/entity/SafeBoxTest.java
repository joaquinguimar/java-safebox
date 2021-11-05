package es.jguimar.safeboxAPI.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class SafeBoxTest {

    @Test
    public void builderOK() {
        SafeBox result = SafeBox.builder().build();

        assertThat(result).isNotNull();
    }

    @Test
    public void newOK() {
        SafeBox result = new SafeBox("id", "name", "pass", new ArrayList<>(), 1);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("id");
        assertThat(result.getName()).isEqualTo("name");
        assertThat(result.getPassword()).isEqualTo("pass");
        assertThat(result.getItems()).isEqualTo(new ArrayList<>());
        assertThat(result.getIncorrectAttempts()).isEqualTo(1);
    }

    @Test
    public void openOk() {
        SafeBox result = SafeBox.builder()
                .incorrectAttempts(2).build();

        assertThat(result.isLocked()).isEqualTo(Boolean.FALSE);
    }

    @Test
    public void lockOk() {
        SafeBox result = SafeBox.builder()
                .incorrectAttempts(3).build();

        assertThat(result.isLocked()).isEqualTo(Boolean.TRUE);
    }

}

package es.jguimar.safeboxAPI.service;

import es.jguimar.safeboxAPI.config.filter.JWTAuthorizationFilter;
import es.jguimar.safeboxAPI.entity.SafeBox;
import es.jguimar.safeboxAPI.exception.SafeboxExitsException;
import es.jguimar.safeboxAPI.mapper.SafeboxMapper;
import es.jguimar.safeboxAPI.model.LoginSafeboxDTO;
import es.jguimar.safeboxAPI.model.SafeboxDTO;
import es.jguimar.safeboxAPI.repository.SafeBoxRepository;
import es.jguimar.safeboxAPI.service.impl.SafeBoxServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.eq;


@RunWith(SpringRunner.class)
public class SafeboxServiceTest {

    @Mock
    private SafeBoxRepository safeBoxRepository;

    @Mock
    private SafeboxMapper safeboxMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    private SafeboxService safeboxService;

    @Before
    public void setup() {
        safeboxService = new SafeBoxServiceImpl(safeBoxRepository, safeboxMapper, passwordEncoder, jwtAuthorizationFilter);
    }

    @Test
    public void createNewSafebox_shouldReturnOk() throws Exception {

        // Given
        given(safeBoxRepository.findByName(any()))
                .willReturn(Optional.empty());

        given(safeBoxRepository.save(any()))
                .willReturn(SafeBox.builder().id("abc1234").build());

        given(passwordEncoder.encode(any())).willReturn("passenco1234");

        SafeboxDTO safeboxDTOMock = new SafeboxDTO();
        safeboxDTOMock.setId("abc1234");
        given(safeboxMapper.toSafeboxDTO(any())).willReturn(safeboxDTOMock);


        // When
        LoginSafeboxDTO login = new LoginSafeboxDTO();
        login.setName("abc1234");
        login.setPassword("pass1234");
        SafeboxDTO safeboxDTO = safeboxService.create(login);

        // Then
        verify(safeBoxRepository, times(1)).findByName(eq("abc1234"));
        verify(safeBoxRepository, times(1)).save(any());
        verify(passwordEncoder, times(1)).encode(eq("pass1234"));
        verify(safeboxMapper, times(1)).toSafeboxDTO(any());
        assertThat(safeboxDTO.getId()).isEqualTo("abc1234");
    }

    @Test
    public void createNewSafebox_shouldReturnKO() throws Exception {

        // Given
        given(safeBoxRepository.findByName(any()))
                .willReturn(Optional.of(SafeBox.builder().id("abc1234").build()));

        // When
        Exception exception = assertThrows(SafeboxExitsException.class, () -> {
                    LoginSafeboxDTO login = new LoginSafeboxDTO();
                    login.setName("abc1234");
                    safeboxService.create(login);
         });

        // Then
        verify(safeBoxRepository, times(1)).findByName(eq("abc1234"));
        assertThat(exception.getMessage()).isNull();
    }

    //TODO: Test cases for all methods

}
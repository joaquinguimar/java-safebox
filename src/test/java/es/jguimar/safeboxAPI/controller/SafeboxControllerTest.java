package es.jguimar.safeboxAPI.controller;

import es.jguimar.safeboxAPI.controller.impl.SafeboxController;
import es.jguimar.safeboxAPI.exception.SafeboxExitsException;
import es.jguimar.safeboxAPI.model.SafeboxDTO;
import es.jguimar.safeboxAPI.service.SafeboxService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class SafeboxControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SafeboxService safeboxService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SafeboxController(safeboxService)).build();
    }

    @Test
    public void createNewSafebox_shouldReturnOk() throws Exception {
        SafeboxDTO safeboxDTO = new SafeboxDTO();
        safeboxDTO.setId("abc1234");
        // Given
        given(safeboxService.create(any()))
                .willReturn(safeboxDTO);

        // When
        final ResultActions result = mockMvc.perform(
                post("/safebox/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"Secure safebox 05\",\n" +
                                "  \"password\": \"extremelySecurePassword\"\n" +
                                "}"));

        // Then
        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("abc1234"));
    }

    @Test
    public void createNewSafeboxDuplicated_shouldReturnKO() throws Exception {

        // Given
        given(safeboxService.create(any()))
                .willThrow(new SafeboxExitsException());

        // When
        final ResultActions result = mockMvc.perform(
                post("/safebox/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"Secure safebox 05\",\n" +
                                "  \"password\": \"extremelySecurePassword\"\n" +
                                "}"));

        // Then
        result.andExpect(status().isConflict());
    }

    @Test
    public void openSafebox_shouldReturnOk() throws Exception {
        Map token = new HashMap();
        token.put("token", "token1234");
        // Given
        given(safeboxService.openSafebox(any()))
                .willReturn(token);

        // When
        final ResultActions result = mockMvc.perform(
                get("/safebox/abc1234/open")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token1234"));
    }

    //TODO: Test cases for all endpoints
}
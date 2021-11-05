package es.jguimar.safeboxAPI.controller.impl;

import es.jguimar.safeboxAPI.controller.Safebox;
import es.jguimar.safeboxAPI.exception.SafeboxExitsException;
import es.jguimar.safeboxAPI.exception.SafeboxLockedException;
import es.jguimar.safeboxAPI.model.ContentDTO;
import es.jguimar.safeboxAPI.model.SafeboxDTO;
import es.jguimar.safeboxAPI.model.LoginSafeboxDTO;
import es.jguimar.safeboxAPI.service.SafeboxService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;


@RestController
@Api(value = "API Safebox poc", tags = { "Safebox" })
@AllArgsConstructor //Constructor-Based Dependency Injection
public class SafeboxController implements Safebox {

    private final SafeboxService safeboxService;

    @Override
    public ContentDTO safeboxIdItemsGet(String id) {
        try {
            return safeboxService.findById(id);
        } catch (SafeboxLockedException e) {
            throw new ResponseStatusException(HttpStatus.LOCKED);
        }
    }

    @Override
    public void safeboxIdItemsPut(String id, @Valid ContentDTO body) {
        try {
            safeboxService.putItemsToSafebox(id, body);
        } catch (SafeboxLockedException e) {
            throw new ResponseStatusException(HttpStatus.LOCKED);
        }
    }

    @Override
    public Map safeboxIdOpenGet(String id) {
        try {
            return safeboxService.openSafebox(id);
        } catch (SafeboxLockedException e) {
            throw new ResponseStatusException(HttpStatus.LOCKED);
        }
    }

    @Override
    public SafeboxDTO safeboxPost(@Valid LoginSafeboxDTO body) {
        try {
            return safeboxService.create(body);
        } catch (SafeboxExitsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}

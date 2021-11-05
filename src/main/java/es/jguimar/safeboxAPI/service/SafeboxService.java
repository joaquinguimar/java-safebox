package es.jguimar.safeboxAPI.service;

import es.jguimar.safeboxAPI.exception.SafeboxExitsException;
import es.jguimar.safeboxAPI.exception.SafeboxLockedException;
import es.jguimar.safeboxAPI.model.ContentDTO;
import es.jguimar.safeboxAPI.model.SafeboxDTO;
import es.jguimar.safeboxAPI.model.LoginSafeboxDTO;

import java.util.Map;

public interface SafeboxService {

    ContentDTO findById(String id) throws SafeboxLockedException;

    SafeboxDTO create(LoginSafeboxDTO user) throws SafeboxExitsException;

    void putItemsToSafebox(String id, ContentDTO body) throws SafeboxLockedException;

    Map openSafebox(String id) throws SafeboxLockedException;

    void incrIncorrectAttemp(String safeboxId);
}

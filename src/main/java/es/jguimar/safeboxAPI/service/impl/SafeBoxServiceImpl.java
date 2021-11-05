package es.jguimar.safeboxAPI.service.impl;

import es.jguimar.safeboxAPI.config.filter.JWTAuthorizationFilter;
import es.jguimar.safeboxAPI.exception.SafeboxExitsException;
import es.jguimar.safeboxAPI.exception.SafeboxLockedException;
import es.jguimar.safeboxAPI.model.ContentDTO;
import es.jguimar.safeboxAPI.model.LoginSafeboxDTO;
import es.jguimar.safeboxAPI.repository.SafeBoxRepository;
import es.jguimar.safeboxAPI.entity.SafeBox;
import es.jguimar.safeboxAPI.mapper.SafeboxMapper;
import es.jguimar.safeboxAPI.model.SafeboxDTO;
import es.jguimar.safeboxAPI.service.SafeboxService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor //Constructor-Based Dependency Injection
public class SafeBoxServiceImpl implements SafeboxService {

    private final SafeBoxRepository safeBoxRepository;

    private final SafeboxMapper safeboxMapper;

    private final PasswordEncoder passwordEncoder;

    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Override
    public SafeboxDTO create(LoginSafeboxDTO user) throws SafeboxExitsException {
        if (!safeBoxRepository.findByName(user.getName()).isEmpty()) {
           throw new SafeboxExitsException();
        } else {
            return safeboxMapper.toSafeboxDTO(
                    safeBoxRepository.save(SafeBox.builder()
                            .name(user.getName())
                            .password(passwordEncoder.encode(user.getPassword()))
                            .build()));
        }
    }

    @Override
    public ContentDTO findById(String id) throws SafeboxLockedException {
        SafeBox safeBox = findAndCheckSafeBox(id);
        return safeboxMapper.toContentDTO(safeBox);
    }

    @Override
    public void putItemsToSafebox(String id, ContentDTO body) throws SafeboxLockedException {
        SafeBox safeBox = findAndCheckSafeBox(id);
        safeBox.setItems(body.getItems());
        safeBoxRepository.save(safeBox);
    }

    @Override
    public Map openSafebox(String id) throws SafeboxLockedException {
        SafeBox safeBox = findAndCheckSafeBox(id);
        Map tokenObj = new HashMap();
        tokenObj.put("token", jwtAuthorizationFilter.generateToken(safeBox.getName()));
        return tokenObj;
    }

    @Override
    public void incrIncorrectAttemp(String id) {
        SafeBox safeBox = safeBoxRepository.findById(id).get();
        safeBox.setIncorrectAttempts(safeBox.getIncorrectAttempts() + 1);
        safeBoxRepository.save(safeBox);
    }

    private SafeBox findAndCheckSafeBox(String id) throws SafeboxLockedException {
        SafeBox safeBox = safeBoxRepository.findById(id).get();
        checkSafeboxLocked(safeBox);
        return safeBox;
    }

    private void checkSafeboxLocked(SafeBox safeBox) throws SafeboxLockedException {
        if (safeBox.isLocked()) {
            throw new SafeboxLockedException();
        }
    }

}

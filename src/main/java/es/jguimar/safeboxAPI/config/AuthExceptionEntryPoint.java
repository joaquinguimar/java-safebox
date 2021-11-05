package es.jguimar.safeboxAPI.config;

import es.jguimar.safeboxAPI.service.SafeboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Punto de tratamiento en caso de autentificacion erronea actualiza el contador de intentos erroneos para poder bloquear la caja fuerte
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint
{

    @Autowired
    SafeboxService safeboxService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException, ServletException
    {
        Matcher m = Pattern.compile(".*\\/safebox\\/([a-zA-Z0-9-]+)(\\/?).*").matcher(request.getRequestURI());
        if (m.matches()) {
            String safeboxId = m.group(1);
            safeboxService.incrIncorrectAttemp(safeboxId);
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED) ;
    }
}
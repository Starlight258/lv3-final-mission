package finalmission.common.security;

import finalmission.common.config.CookieProperties;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(CookieProperties.class)
public class CookieManager {

    private static final String LAX = "Lax";

    private final CookieProperties cookieProperties;

    public CookieManager(final CookieProperties cookieProperties) {
        this.cookieProperties = cookieProperties;
    }

    public ResponseCookie makeCookie(final String name, final String value) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .sameSite(LAX)
                .path("/")
                .maxAge(cookieProperties.getMaxAge())
                .build();
    }

    public void deleteCookie(final HttpServletResponse response, final String name) {
        ResponseCookie cookie = ResponseCookie.from(name, "")
                .httpOnly(true)
                .sameSite(LAX)
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}

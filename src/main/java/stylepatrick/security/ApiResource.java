package stylepatrick.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
public class ApiResource {

    @AllArgsConstructor
    @Getter
    private class Response {
        private String message;
    }

    @RequestMapping
    public ResponseEntity<Response> getPublic() {
        return ResponseEntity.ok().body(new Response("Hello to public resource!"));
    }

    @RequestMapping(value = "secret")
    public ResponseEntity<Response> getSecret() {
        return ResponseEntity.ok().body(new Response("Hello to secret resource!"));
    }

    // Example of Methode security. If the role 'TOPSECRET' is given to the user he can access the methode otherwise a 403 will be given
    @PreAuthorize("hasAuthority('TOPSECRET')")
    @RequestMapping(value = "topsecret")
    public ResponseEntity<Response> getTopSecret() {
        return ResponseEntity.ok().body(new Response("Hello to top-secret resource!"));
    }

    @RequestMapping(value = "user")
    public Object currentUserName(Authentication authentication) {
        if (authentication != null)
            return authentication.getPrincipal();
        else
            return null;
    }
}

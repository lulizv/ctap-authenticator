package mock.ctap.interfaces.webauthnio.registration.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WebAuthnIOVerification {

    @JsonProperty("username")
    private String username;

    @JsonProperty("response")
    private WebAuthnIOVerificationResponse response;
}

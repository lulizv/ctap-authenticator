package mock.ctap.interfaces.webauthnio.login.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import mock.ctap.interfaces.webauthnio.registration.verification.WebAuthnIOVerificationResponse;

@AllArgsConstructor
public class WebAuthnIOLoginVerification {

    @JsonProperty("username")
    private String username;

    @JsonProperty("response")
    private WebAuthnIOLoginVerificationResponse response;
}

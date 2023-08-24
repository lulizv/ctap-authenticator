package mock.ctap.interfaces.webauthnio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebAuthnIOAuthenticatorSelection {

    @JsonProperty("residentKey")
    private String residentKey;
    @JsonProperty("requireResidentKey")
    private String requiresResidentKey;
    @JsonProperty("userVerification")
    private String userVerification;
}

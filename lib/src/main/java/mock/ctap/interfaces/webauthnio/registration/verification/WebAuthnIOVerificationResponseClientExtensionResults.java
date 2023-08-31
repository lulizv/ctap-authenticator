package mock.ctap.interfaces.webauthnio.registration.verification;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebAuthnIOVerificationResponseClientExtensionResults {

    @JsonProperty("credProps")
    private final WebAuthnIOVerificationResponseClientExtensionResultsCredProps credProps = new WebAuthnIOVerificationResponseClientExtensionResultsCredProps();
}

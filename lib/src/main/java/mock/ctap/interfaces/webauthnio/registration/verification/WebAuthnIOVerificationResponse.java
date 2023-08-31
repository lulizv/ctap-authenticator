package mock.ctap.interfaces.webauthnio.registration.verification;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WebAuthnIOVerificationResponse {

    @JsonProperty("authenticatorAttachment")
    private final String authenticatorAttachment = "cross-platform";

    @NonNull
    @JsonProperty("rawId")
    private String rawId;

    @NonNull
    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private final String type = "public-key";

    @JsonProperty("clientExtensionResults")
    private final WebAuthnIOVerificationResponseClientExtensionResults clientExtensionResults = new WebAuthnIOVerificationResponseClientExtensionResults();

    @NonNull
    @JsonProperty("response")
    private WebAuthnIOVerificationResponseResponse response;

}

package mock.ctap.interfaces.webauthnio.login.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class WebAuthnIOLoginVerificationResponse {

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

    @NonNull
    @JsonProperty("response")
    private WebAuthnIOLoginVerificationResponseResponse response;
}

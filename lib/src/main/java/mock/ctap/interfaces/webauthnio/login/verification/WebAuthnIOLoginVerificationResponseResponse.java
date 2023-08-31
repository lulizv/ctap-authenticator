package mock.ctap.interfaces.webauthnio.login.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WebAuthnIOLoginVerificationResponseResponse {

    @JsonProperty("userHandle")
    private String userHandle;

    @JsonProperty("clientDataJSON")
    private String clientDataJSON;

    @JsonProperty("signature")
    private String signature;

    @JsonProperty("authenticatorData")
    private String authenticatorData;
}

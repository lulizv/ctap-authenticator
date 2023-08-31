package mock.ctap.interfaces.webauthnio.registration.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class WebAuthnIOPubKeyCredParams {
    @JsonProperty("type")
    private String type;
    @JsonProperty("alg")
    private int alg;
}

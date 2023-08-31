package mock.ctap.interfaces.opf.login.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OPFLoginResponse {

    @JsonProperty("clientDataJSON")
    private String clientDataJSON;

    @JsonProperty("authenticatorData")
    private String authenticatorData;

    @JsonProperty("signature")
    private String signature;

    @JsonProperty("userHandle")
    private String userHandle;
}

package mock.ctap.interfaces.webauthnio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class WebAuthnIOUser {
    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;

    @JsonProperty("displayName")
    private String displayName;
}

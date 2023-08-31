package mock.ctap.interfaces.webauthnio.registration.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class WebAuthnIORp {
    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;
}

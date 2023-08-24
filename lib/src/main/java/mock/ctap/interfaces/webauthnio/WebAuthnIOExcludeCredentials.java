package mock.ctap.interfaces.webauthnio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class WebAuthnIOExcludeCredentials {

    @JsonProperty("id")
    String id;

    @JsonProperty("type")
    String type;

    @JsonProperty("transports")
    List<String> transports = new ArrayList<String>();
}

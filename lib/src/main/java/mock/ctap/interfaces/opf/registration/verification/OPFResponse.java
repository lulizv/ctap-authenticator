package mock.ctap.interfaces.opf.registration.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OPFResponse {

    @JsonProperty("clientDataJSON")
    private String clientDataJSON;

    @JsonProperty("attestationObject")
    private String attestationObject;
}

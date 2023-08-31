package mock.ctap.interfaces.opf.registration.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OPFPubKeyCredParams {
    @JsonProperty("type")
    private String type;
    @JsonProperty("alg")
    private int alg;
}

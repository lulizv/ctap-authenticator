package mock.ctap.interfaces.opf.login.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class OPFPublicKeyCredentialDescriptor {

    @Getter
    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("transports")
    private List<String> transports = new ArrayList<String>();

}

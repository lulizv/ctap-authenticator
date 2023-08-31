package mock.ctap.interfaces.opf.registration.options;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OPFExcludeCredentials {

    @JsonProperty("id")
    String id;

    @JsonProperty("type")
    String type;

    @JsonProperty("transports")
    List<String> transports = new ArrayList<String>();
}

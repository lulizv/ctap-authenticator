package mock.ctap.interfaces.opf.registration.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OPFRp {
    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;
}

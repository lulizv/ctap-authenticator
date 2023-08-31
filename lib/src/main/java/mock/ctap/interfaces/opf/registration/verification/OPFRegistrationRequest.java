package mock.ctap.interfaces.opf.registration.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;

@Data
@RequiredArgsConstructor
public class OPFRegistrationRequest {

    @NonNull
    @JsonProperty("id")
    private String id;

    @NonNull
    @JsonProperty("rawId")
    private String rawId;

    @NonNull
    @JsonProperty("response")
    private OPFResponse response;

    @JsonProperty("authenticatorAttachment")
    private final String authenticatorAttachment = "cross-platform";

    @JsonProperty("type")
    private final String type = "public-key";
}

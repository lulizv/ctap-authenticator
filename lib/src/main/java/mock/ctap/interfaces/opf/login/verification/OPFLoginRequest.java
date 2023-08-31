package mock.ctap.interfaces.opf.login.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OPFLoginRequest {

    @NonNull
    @JsonProperty("id")
    private String id;

    @NonNull
    @JsonProperty("rawId")
    private String rawId;

    @JsonProperty("type")
    private final String type = "public-key";

    @NonNull
    @JsonProperty("response")
    private OPFLoginResponse response;
}

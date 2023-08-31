package mock.ctap.interfaces.opf.registration.options;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OPFAuthenticatorSelection {

    @JsonProperty("residentKey")
    private String residentKey;
    @JsonProperty("requireResidentKey")
    private String requiresResidentKey;
    @JsonProperty("userVerification")
    private String userVerification;
}

package mock.ctap.interfaces.opf.login.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.webauthn4j.data.PublicKeyCredentialDescriptor;
import com.webauthn4j.data.PublicKeyCredentialType;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OPFLoginOptions {

    @Getter
    @JsonProperty("challenge")
    private String challenge;

    @JsonProperty("timeout")
    private int timeout;

    @Getter
    @JsonProperty("rpId")
    private String rpId;

    @JsonProperty("allowCredentials")
    private List<OPFPublicKeyCredentialDescriptor> allowCredentials = new ArrayList<OPFPublicKeyCredentialDescriptor>();

    @JsonProperty("userVerification")
    private String userVerification;

    public List<PublicKeyCredentialDescriptor> getAllowList() {
        return this.allowCredentials.stream().map(cred -> new PublicKeyCredentialDescriptor(PublicKeyCredentialType.PUBLIC_KEY, cred.getId().getBytes(StandardCharsets.UTF_8), null)).collect(Collectors.toList());
    }
}

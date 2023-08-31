package mock.ctap.interfaces.webauthnio.login.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.webauthn4j.data.PublicKeyCredentialDescriptor;
import com.webauthn4j.data.PublicKeyCredentialType;
import lombok.Getter;
import mock.ctap.interfaces.opf.login.options.OPFPublicKeyCredentialDescriptor;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class WebAuthnIOLoginOptions {

    @Getter
    @JsonProperty("challenge")
    private String challenge;

    @JsonProperty("timeout")
    private int timeout;

    @Getter
    @JsonProperty("rpId")
    private String rpId;

    @JsonProperty("allowCredentials")
    private List<WebAuthnIOPublicKeyCredentialDescriptor> allowCredentials = new ArrayList<WebAuthnIOPublicKeyCredentialDescriptor>();

    @JsonProperty("userVerification")
    private String userVerification;

    public List<PublicKeyCredentialDescriptor> getAllowList() {
        return this.allowCredentials.stream().map(cred -> new PublicKeyCredentialDescriptor(PublicKeyCredentialType.PUBLIC_KEY, Base64.getDecoder().decode(cred.getId()), null)).collect(Collectors.toList());
    }
}

package mock.ctap.interfaces.webauthnio.registration.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.webauthn4j.data.PublicKeyCredentialParameters;
import com.webauthn4j.data.PublicKeyCredentialRpEntity;
import com.webauthn4j.data.PublicKeyCredentialType;
import com.webauthn4j.data.PublicKeyCredentialUserEntity;
import com.webauthn4j.data.attestation.statement.COSEAlgorithmIdentifier;
import lombok.Getter;
import mock.ctap.interfaces.RegistrationOptions;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class WebAuthnIORegistrationOptions implements RegistrationOptions {

    @JsonProperty("rp")
    private WebAuthnIORp rp;

    @JsonProperty("user")
    private WebAuthnIOUser user;

    @Getter
    @JsonProperty("challenge")
    private String challenge;

    @JsonProperty("pubKeyCredParams")
    private List<WebAuthnIOPubKeyCredParams> pubKeyCredParams = new ArrayList<WebAuthnIOPubKeyCredParams>();

    @JsonProperty("timeout")
    private int timeout;

    @JsonProperty("excludeCredentials")
    private List<WebAuthnIOExcludeCredentials> excludeCredentials = new ArrayList<WebAuthnIOExcludeCredentials>();

    @JsonProperty("authenticatorSelection")
    private WebAuthnIOAuthenticatorSelection authenticatorSelection;

    @JsonProperty("attestation")
    private String attestation;

    @JsonProperty("extensions")
    private WebAuthnIOExtensions extensions;

    @Override
    public byte[] getClientDataHash() {
        return new byte[0];
    }

    @Override
    public PublicKeyCredentialRpEntity getRp() {
        return new PublicKeyCredentialRpEntity(this.rp.getId(), this.rp.getName());
    }

    @Override
    public PublicKeyCredentialUserEntity getUser() {
        byte[] id = Base64.getDecoder().decode(this.user.getId());
        return new PublicKeyCredentialUserEntity(id, this.user.getName(), this.user.getDisplayName());
    }

    @Override
    public List<PublicKeyCredentialParameters> getPubKeyCredParams() {
        return this.pubKeyCredParams.stream()
                .map(param -> new PublicKeyCredentialParameters(PublicKeyCredentialType.create(param.getType()), COSEAlgorithmIdentifier.create(param.getAlg())))
                .collect(Collectors.toList());
    }
}

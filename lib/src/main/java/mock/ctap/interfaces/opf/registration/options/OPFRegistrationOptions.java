package mock.ctap.interfaces.opf.registration.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.webauthn4j.data.PublicKeyCredentialParameters;
import com.webauthn4j.data.PublicKeyCredentialRpEntity;
import com.webauthn4j.data.PublicKeyCredentialType;
import com.webauthn4j.data.PublicKeyCredentialUserEntity;
import com.webauthn4j.data.attestation.statement.COSEAlgorithmIdentifier;
import lombok.Getter;
import mock.ctap.interfaces.RegistrationOptions;
import mock.ctap.interfaces.webauthnio.registration.options.WebAuthnIOExtensions;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class OPFRegistrationOptions implements RegistrationOptions {

    @JsonProperty("rp")
    private OPFRp rp;

    @JsonProperty("user")
    private OPFUser user;

    @Getter
    @JsonProperty("challenge")
    private String challenge;

    @JsonProperty("pubKeyCredParams")
    private List<OPFPubKeyCredParams> pubKeyCredParams = new ArrayList<OPFPubKeyCredParams>();

    @JsonProperty("timeout")
    private int timeout;

    @JsonProperty("excludeCredentials")
    private List<OPFExcludeCredentials> excludeCredentials = new ArrayList<OPFExcludeCredentials>();

    @JsonProperty("authenticatorSelection")
    private OPFAuthenticatorSelection authenticatorSelection;

    @JsonProperty("attestation")
    private String attestation;

    @JsonProperty("attestationFormats")
    private List<String> attestationFormats = new ArrayList<String>();

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

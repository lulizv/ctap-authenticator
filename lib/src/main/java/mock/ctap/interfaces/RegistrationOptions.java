package mock.ctap.interfaces;

import com.webauthn4j.data.PublicKeyCredentialParameters;
import com.webauthn4j.data.PublicKeyCredentialRpEntity;
import com.webauthn4j.data.PublicKeyCredentialUserEntity;

import java.util.List;

public interface RegistrationOptions {
    byte[] getClientDataHash();
    PublicKeyCredentialRpEntity getRp();
    PublicKeyCredentialUserEntity getUser();
    List<PublicKeyCredentialParameters> getPubKeyCredParams();

}

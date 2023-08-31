package mock.ctap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.webauthn4j.converter.util.CborConverter;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.attestation.AttestationObject;
import mock.ctap.exceptions.AuthenticatorException;
import mock.ctap.interfaces.opf.login.options.OPFLoginOptions;
import mock.ctap.interfaces.opf.login.verification.OPFLoginRequest;
import mock.ctap.interfaces.opf.login.verification.OPFLoginResponse;
import mock.ctap.interfaces.opf.registration.options.OPFRegistrationOptions;
import mock.ctap.interfaces.opf.registration.verification.OPFRegistrationRequest;
import mock.ctap.interfaces.opf.registration.verification.OPFResponse;
import mock.ctap.interfaces.webauthnio.login.options.WebAuthnIOLoginOptions;
import mock.ctap.interfaces.webauthnio.login.verification.WebAuthnIOLoginVerification;
import mock.ctap.interfaces.webauthnio.login.verification.WebAuthnIOLoginVerificationResponse;
import mock.ctap.interfaces.webauthnio.login.verification.WebAuthnIOLoginVerificationResponseResponse;
import mock.ctap.interfaces.webauthnio.registration.options.WebAuthnIORegistrationOptions;
import mock.ctap.interfaces.webauthnio.registration.verification.WebAuthnIOVerification;
import mock.ctap.interfaces.webauthnio.registration.verification.WebAuthnIOVerificationResponse;
import mock.ctap.interfaces.webauthnio.registration.verification.WebAuthnIOVerificationResponseResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MockFido2Client {

    public String origin;

    MockFido2Client(String origin) {
        this.origin = origin;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ObjectWriter objectWriter = new ObjectMapper().writer();

    private static final MockAuthenticator mockAuthenticator = new MockAuthenticator();
    public String register(String registerOptions) throws IOException, AuthenticatorException {

        //OPFRegistrationOptions registrationOptions = objectMapper.readValue(registerOptions, OPFRegistrationOptions.class);
        WebAuthnIORegistrationOptions registrationOptions = objectMapper.readValue(registerOptions, WebAuthnIORegistrationOptions.class);
        byte[] clientDataJson = new JSONObject()
                .put("type", "webauthn.create")
                .put("challenge", registrationOptions.getChallenge())
                .put("origin", this.origin)
                .put("crossOrigin", true)
                .toString().getBytes();

        AttestationObject attestationObject = mockAuthenticator.makeCredential(new byte[0], registrationOptions.getRp(), registrationOptions.getUser(), registrationOptions.getPubKeyCredParams());

        CborConverter converter = new ObjectConverter().getCborConverter();

        /*
        OPFResponse responseField = new OPFResponse(Base64.getEncoder().withoutPadding().encodeToString(clientDataJson),
                Base64.getEncoder().withoutPadding().encodeToString(converter.writeValueAsBytes(attestationObject)));

        byte[] credentialId = attestationObject.getAuthenticatorData().getAttestedCredentialData().getCredentialId();
        OPFRegistrationRequest registrationRequest = new OPFRegistrationRequest(Base64.getEncoder().withoutPadding().encodeToString(credentialId),
                Base64.getEncoder().withoutPadding().encodeToString(credentialId),
                responseField);

        */

        WebAuthnIOVerificationResponseResponse responseResponseField = new WebAuthnIOVerificationResponseResponse(Base64.getEncoder().withoutPadding().encodeToString(clientDataJson),
                Base64.getEncoder().withoutPadding().encodeToString(converter.writeValueAsBytes(attestationObject)));

        byte[] credentialId = attestationObject.getAuthenticatorData().getAttestedCredentialData().getCredentialId();
        WebAuthnIOVerificationResponse responseField = new WebAuthnIOVerificationResponse(Base64.getEncoder().withoutPadding().encodeToString(credentialId),
                Base64.getEncoder().withoutPadding().encodeToString(credentialId),responseResponseField
                );

        WebAuthnIOVerification registrationRequest = new WebAuthnIOVerification(registrationOptions.getUser().getName(), responseField);
        return objectWriter.writeValueAsString(registrationRequest);

    }
    public String login(String loginOptionsString, String username) throws IOException, NoSuchAlgorithmException, AuthenticatorException {
        //OPFLoginOptions loginOptions = objectMapper.readValue(loginOptionsString, OPFLoginOptions.class);
        WebAuthnIOLoginOptions loginOptions = objectMapper.readValue(loginOptionsString, WebAuthnIOLoginOptions.class);

        byte[] clientDataJson = new JSONObject()
                .put("type", "webauthn.get")
                .put("challenge", loginOptions.getChallenge())
                .put("origin", this.origin)
                .put("crossOrigin", true)
                .toString().getBytes();

        byte[] clientHash = MessageDigest.getInstance("SHA-256").digest(clientDataJson);

        FIDO2Assertion assertion = mockAuthenticator.getAssertion(loginOptions.getRpId(), clientHash, loginOptions.getAllowList());

        /*
        OPFLoginResponse responseField = new OPFLoginResponse(Base64.getEncoder().withoutPadding().encodeToString(clientDataJson),
                Base64.getEncoder().withoutPadding().encodeToString(assertion.getAuthData()),
                Base64.getEncoder().withoutPadding().encodeToString(assertion.getSignature()),
                Base64.getEncoder().withoutPadding().encodeToString(username.getBytes(StandardCharsets.UTF_8)));

        OPFLoginRequest loginRequest =  new OPFLoginRequest(Base64.getEncoder().withoutPadding().encodeToString(assertion.getCredentialId()),
                Base64.getEncoder().withoutPadding().encodeToString(assertion.getCredentialId()),
                responseField);
         */

        WebAuthnIOLoginVerificationResponseResponse responseResponseField = new WebAuthnIOLoginVerificationResponseResponse(Base64.getEncoder().withoutPadding().encodeToString(username.getBytes(StandardCharsets.UTF_8)),
                Base64.getEncoder().withoutPadding().encodeToString(clientDataJson),
                Base64.getEncoder().withoutPadding().encodeToString(assertion.getSignature()),
                Base64.getEncoder().withoutPadding().encodeToString(assertion.getAuthData())
                );

        WebAuthnIOLoginVerificationResponse responseField = new WebAuthnIOLoginVerificationResponse(Base64.getEncoder().withoutPadding().encodeToString(assertion.getCredentialId()),
                Base64.getEncoder().withoutPadding().encodeToString(assertion.getCredentialId()),
                responseResponseField
                );

        WebAuthnIOLoginVerification loginRequest = new WebAuthnIOLoginVerification(username, responseField);
        return objectWriter.writeValueAsString(loginRequest);
    }
}

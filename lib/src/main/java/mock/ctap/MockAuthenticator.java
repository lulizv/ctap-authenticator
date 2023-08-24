/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package mock.ctap;

import com.webauthn4j.data.PublicKeyCredentialDescriptor;
import com.webauthn4j.data.PublicKeyCredentialParameters;
import com.webauthn4j.data.PublicKeyCredentialRpEntity;
import com.webauthn4j.data.PublicKeyCredentialUserEntity;
import com.webauthn4j.data.attestation.AttestationObject;
import com.webauthn4j.data.attestation.authenticator.*;
import com.webauthn4j.data.attestation.statement.AttestationStatement;
import com.webauthn4j.data.attestation.statement.COSEAlgorithmIdentifier;
import com.webauthn4j.data.attestation.statement.NoneAttestationStatement;
import com.webauthn4j.util.ArrayUtil;
import mock.ctap.exceptions.AuthenticatorException;
import mock.ctap.interfaces.RegistrationRequest;
import mock.ctap.models.PublicKeyCredential;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.*;
import java.util.stream.Collectors;

public class MockAuthenticator {

    static final List<COSEAlgorithmIdentifier> supportedAlgorithms = Arrays.asList(COSEAlgorithmIdentifier.ES256, COSEAlgorithmIdentifier.RS256);

    List<PublicKeyCredential> publicKeyCredentials = new ArrayList<PublicKeyCredential>();

    public MockAuthenticator() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public AttestationObject makeCredential(RegistrationRequest request) throws AuthenticatorException {
        return this.makeCredential(request.getClientDataHash(), request.getRp(), request.getUser(), request.getPubKeyCredParams());
    }
    public AttestationObject makeCredential(byte[] clientDataHash, PublicKeyCredentialRpEntity rp, PublicKeyCredentialUserEntity user, List<PublicKeyCredentialParameters> pubKeyCredParams) throws AuthenticatorException {
        Optional<PublicKeyCredentialParameters> publicKeyCredential = pubKeyCredParams.stream().filter(param -> supportedAlgorithms.contains(param.getAlg())).findFirst();
        if (publicKeyCredential.isEmpty()) {
            throw new AuthenticatorException("MockAuthenticator does not support requested algorithm.");
        }

        try {
            KeyPair keypair = generateKeyPair(publicKeyCredential.get().getAlg());

            byte[] rpIdHash = MessageDigest.getInstance("SHA-256").digest(rp.getId().getBytes(StandardCharsets.UTF_8));

            byte flags = Byte.parseByte("01000101", 2); // Bits set indicate the UP, UV, and AT flags as per the webauthn specification

            byte[] credentialId = saveCredential(rp.getId(), keypair);
            AttestedCredentialData attestedCredentialData = new AttestedCredentialData(AAGUID.ZERO, credentialId, EC2COSEKey.create(keypair, publicKeyCredential.get().getAlg()));
            AuthenticatorData authenticatorData = new AuthenticatorData(rpIdHash, flags, 0, attestedCredentialData);
            AttestationStatement attestationStatement = new NoneAttestationStatement();
            return new AttestationObject(authenticatorData, attestationStatement);
        } catch (NoSuchAlgorithmException e) {
            throw new AuthenticatorException("The requested algorithm is not available: " + e.getMessage());
        }
    }

    public String getAssertion(String rpId, byte[] clientDataHash, List<PublicKeyCredentialDescriptor> allowList, String challenge, String username) throws AuthenticatorException{
        try {
            PublicKeyCredential credential = getCredential(allowList);
            byte[] rpIdHash = MessageDigest.getInstance("SHA-256").digest(rpId.getBytes(StandardCharsets.UTF_8));
            byte flags = Byte.parseByte("00000101", 2);
            byte[] signCount = new byte[4];

            byte[] clientDataJson = new JSONObject()
                    .put("type", "webauthn.get")
                    .put("challenge", challenge)
                    .put("origin", "https://webauthn.io")
                    .put("crossOrigin", false)
                    .toString().getBytes();

            byte[] clientHash = MessageDigest.getInstance("SHA-256").digest(clientDataJson);

            byte[] payload = ArrayUtils.add(rpIdHash, flags);
            byte[] authData = ArrayUtils.addAll(payload, signCount);
            payload = ArrayUtils.addAll(authData, clientHash);

            byte[] signedData = signData(payload, credential.getKeyPair().getPrivate());

            return new JSONObject()
                    .put("username", username)
                    .put("response", new JSONObject()
                            .put("id", Base64.getEncoder().withoutPadding().encodeToString(credential.getId()))
                            .put("rawId", Base64.getEncoder().withoutPadding().encodeToString(credential.getId()))
                            .put("response", new JSONObject()
                                    .put("authenticatorData", Base64.getEncoder().withoutPadding().encodeToString(authData))
                                    .put("clientDataJSON", Base64.getEncoder().withoutPadding().encodeToString(clientDataJson))
                                    .put("signature", Base64.getEncoder().withoutPadding().encodeToString(signedData))
                                    .put("userHandle", Base64.getEncoder().withoutPadding().encodeToString(username.getBytes(StandardCharsets.UTF_8)))
                            )
                            .put("type", "public-key")
                            .put("clientExtensionResults", new JSONObject())
                            .put("authenticatorAttachment", "cross-platform")
                    ).toString();

        } catch (NoSuchAlgorithmException e) {
            throw new AuthenticatorException("The request algorithm is not available: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new AuthenticatorException("The key provided is invalid: " + e.getMessage());
        } catch (SignatureException e) {
            throw new AuthenticatorException("Unknown error when trying to sign data: " + e.getMessage());
        }
    }
    private KeyPair generateKeyPair(COSEAlgorithmIdentifier algorithmIdentifier) throws AuthenticatorException{
        try {
            KeyPairGenerator keyPairGenerator;
            if (algorithmIdentifier.getValue() == COSEAlgorithmIdentifier.ES256.getValue()) {
                keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
                keyPairGenerator.initialize(new ECGenParameterSpec("prime256v1"));
            } else {
                keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(2048);
            }
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new AuthenticatorException("The requested algorithm is not available: " + e.getMessage());
        } catch (NoSuchProviderException e) {
            throw new AuthenticatorException("The requested provider is not available: " + e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new AuthenticatorException("The requested algorithm parameter is not available: " + e.getMessage());
        }
    }

    private byte[] saveCredential(String rpId, KeyPair keyPair) {
        byte[] credentialId = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
        publicKeyCredentials.add(new PublicKeyCredential(credentialId, rpId, keyPair));
        return credentialId;
    }

    private PublicKeyCredential getCredential(List<PublicKeyCredentialDescriptor> allowList) throws AuthenticatorException{
        byte[] credentialIds = allowList.stream().map(o -> o.getId()).findFirst().get();
        Optional<PublicKeyCredential> credential = publicKeyCredentials.stream().filter(cred -> ArrayUtils.isEquals(credentialIds, cred.getId())).findFirst();
        if (credential.isEmpty()) {
            throw new AuthenticatorException("No valid credentials provided.");
        }
        return credential.get();
    }

    public byte[] signData(byte[] data, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }
}

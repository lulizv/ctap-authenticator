package mock.ctap.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.KeyPair;

@Data
@AllArgsConstructor
public class PublicKeyCredential {
    private byte[] id;
    private String rpId;
    private KeyPair keyPair;
}

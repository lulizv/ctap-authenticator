package mock.ctap.interfaces.webauthnio.registration.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class WebAuthnIOVerificationResponseResponse {

    @NonNull
    @JsonProperty("clientDataJSON")
    private String clientDataJSON;

    @JsonProperty("transports")
    private final List<String> transports = new ArrayList<String> (
            List.of("nfc", "usb")
    );

    @NonNull
    @JsonProperty("attestationObject")
    private String attestationObject;
}

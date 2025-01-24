package cz.kdolezal.culturaleventshub.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QrDecryptRequest {
    private String qrEncryptedString;
}

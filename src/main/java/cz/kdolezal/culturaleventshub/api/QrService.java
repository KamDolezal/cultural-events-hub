package cz.kdolezal.culturaleventshub.api;

import cz.kdolezal.culturaleventshub.api.request.QrDecryptRequest;

public interface QrService {
    void generateQr(Long ticketId);

    String decrypt(QrDecryptRequest request);

    String validation(Long ticketId);
}

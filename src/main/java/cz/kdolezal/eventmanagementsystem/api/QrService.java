package cz.kdolezal.eventmanagementsystem.api;

import cz.kdolezal.eventmanagementsystem.api.request.QrDecryptRequest;

public interface QrService {
    void generateQr(Long ticketId);

    String decrypt(QrDecryptRequest request);

    String validation(Long ticketId);
}

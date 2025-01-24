package cz.kdolezal.culturaleventshub.controller;

import cz.kdolezal.culturaleventshub.api.QrService;
import cz.kdolezal.culturaleventshub.api.request.QrDecryptRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("qrcode")
public class QrCodeController {
    private final QrService qrService;

    public QrCodeController(QrService qrService) {
        this.qrService = qrService;
    }


    @PostMapping("/decrypt/")
    public ResponseEntity<String> decryptQr(@RequestBody QrDecryptRequest request) {
        return ResponseEntity.ok().body(qrService.decrypt(request));
    }

    @PostMapping("/add/{ticketId}")
    public ResponseEntity<Void> generateQr(@PathVariable("ticketId") Long ticketId) {
        qrService.generateQr(ticketId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/validation/{ticketId}")
    public ResponseEntity<String> edit(@PathVariable("ticketId") long id) {
        return ResponseEntity.ok().body(qrService.validation(id));
    }
}

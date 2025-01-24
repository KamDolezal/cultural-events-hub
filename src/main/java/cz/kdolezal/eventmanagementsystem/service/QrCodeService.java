package cz.kdolezal.eventmanagementsystem.service;

import cz.kdolezal.eventmanagementsystem.api.QrService;
import cz.kdolezal.eventmanagementsystem.api.TicketService;
import cz.kdolezal.eventmanagementsystem.api.request.QrDecryptRequest;
import cz.kdolezal.eventmanagementsystem.api.request.TicketAddQrRequest;
import cz.kdolezal.eventmanagementsystem.entity.TicketEntity;
import cz.kdolezal.eventmanagementsystem.qrcode.EncryptionUtil;
import cz.kdolezal.eventmanagementsystem.qrcode.QRCodeGenerator;
import cz.kdolezal.eventmanagementsystem.repository.TicketJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class QrCodeService implements QrService {
    private final TicketService ticketService;
    private final TicketJpaRepository ticketRepository;
    private final EmailService emailService;
    private final EncryptionUtil encryptionUtil;

    public QrCodeService(TicketService ticketService, TicketJpaRepository ticketRepository, EmailService emailService, EncryptionUtil encryptionUtil) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
        this.emailService = emailService;
        this.encryptionUtil = encryptionUtil;
    }

    @Override
    public void generateQr(Long ticketId) {
        if(ticketId != null) {
            String ticketApi = "https://localhost:8080/qrcode/validation/" + Long.toString(ticketId);
            String qrLink = encryptionUtil.encrypt(ticketApi);
            byte[] qrCode;
            qrCode = QRCodeGenerator.generateQRCode(qrLink, 200, 200);

            // Save of qr code to local disk - added for test
            //String file = "C:\\QR\\qr.png";
            //QRCodeUtil.saveQrCodeToPng(qrCode, file);

            // add QR into the ticket entity
            TicketAddQrRequest request = new TicketAddQrRequest(ticketId,qrCode);
            ticketService.addQr(request);

            //email notification //TODO email notification was deactivated
            // emailService.sendEmailWithAttachment("**@**.com", "Your ticket to the party", "Please find your QR code attached.", qrCode, "ticket.png");
        }
    }

    @Override
    public String decrypt(QrDecryptRequest request) {
        return encryptionUtil.decrypt(request.getQrEncryptedString());
    }

    @Override
    public String validation(Long ticketId) {
        TicketEntity ticket = ticketService.getTicketEntity(ticketId);
        if(ticket.getValid()){
            ticket.setValid(false);
            ticketRepository.save(ticket);
            return ticket.getEvent().getName();
        }
        return "Ticket was used!";
    }
}

package cz.kdolezal.eventmanagementsystem.init;

import cz.kdolezal.eventmanagementsystem.api.QrService;
import cz.kdolezal.eventmanagementsystem.api.TicketService;
import cz.kdolezal.eventmanagementsystem.entity.TicketEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

// Class is for initialization of QR codes after restart of system.
// It is necessary for correct display of tickets on website
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final TicketService ticketService;
    private final QrService qrService;

    public DatabaseInitializer(TicketService ticketService, QrService qrService) {
        this.ticketService = ticketService;
        this.qrService = qrService;
    }

    @Override
    public void run(String... args) throws Exception {

        List<TicketEntity> tickets = ticketService.getAllTicketEntity();
        if(tickets!= null){
            for(TicketEntity ticket : tickets){
                qrService.generateQr(ticket.getId());
            }
            System.out.println("Database - QR codes were initialized");
        }
    }
}
package cz.kdolezal.culturaleventshub.controller;

import cz.kdolezal.culturaleventshub.api.TicketService;
import cz.kdolezal.culturaleventshub.api.request.TicketAddRequest;
import cz.kdolezal.culturaleventshub.dto.TicketDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PreAuthorize("isAuthenticated() and (@ticketSecurity.isOwner(#id, principal.userId) or hasRole('ADMIN'))")
    @GetMapping("{id}")
    public ResponseEntity<TicketDTO> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(ticketService.get(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAll() {
        return ResponseEntity.ok().body(ticketService.getAll());
    }

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody TicketAddRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.add(request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        ticketService.delete(id);
        return ResponseEntity.ok().build();
    }

    //Endpoint response is png file of QR code
    @PreAuthorize("isAuthenticated() and (@ticketSecurity.isOwner(#id, principal.userId) or hasRole('ADMIN'))")
    @GetMapping("/{id}/qrcode")
    public ResponseEntity<byte[]> getQrByTicketId(@PathVariable("id") long id) {

        TicketDTO ticket = ticketService.get(id);
        if (ticket == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //Loading of QR code byte[]
        byte[] qrCode = ticket.getQrCode();
        String str = new String(qrCode, StandardCharsets.UTF_8);

        //Response as PNG file
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCode);
    }
}

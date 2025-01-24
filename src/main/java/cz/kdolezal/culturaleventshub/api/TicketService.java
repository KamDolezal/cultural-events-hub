package cz.kdolezal.eventmanagementsystem.api;

import cz.kdolezal.eventmanagementsystem.api.request.TicketAddQrRequest;
import cz.kdolezal.eventmanagementsystem.api.request.TicketAddRequest;
import cz.kdolezal.eventmanagementsystem.dto.TicketDTO;
import cz.kdolezal.eventmanagementsystem.entity.TicketEntity;

import java.util.List;

public interface TicketService {
    long add(TicketAddRequest request);

    void addQr(TicketAddQrRequest request);

    void delete(long id);

    TicketEntity getTicketEntity(long id);

    TicketDTO get(long id);

    List<TicketEntity> getAllTicketEntity();

    List<TicketDTO> getAll();
}

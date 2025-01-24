package cz.kdolezal.culturaleventshub.api;

import cz.kdolezal.culturaleventshub.api.request.TicketAddQrRequest;
import cz.kdolezal.culturaleventshub.api.request.TicketAddRequest;
import cz.kdolezal.culturaleventshub.dto.TicketDTO;
import cz.kdolezal.culturaleventshub.entity.TicketEntity;

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

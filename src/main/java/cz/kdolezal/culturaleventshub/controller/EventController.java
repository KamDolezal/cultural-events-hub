package cz.kdolezal.eventmanagementsystem.controller;

import cz.kdolezal.eventmanagementsystem.api.EventService;
import cz.kdolezal.eventmanagementsystem.api.request.EventAddRequest;
import cz.kdolezal.eventmanagementsystem.api.request.EventEditRequest;
import cz.kdolezal.eventmanagementsystem.dto.EventDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(eventService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAll() {
        return ResponseEntity.ok().body(eventService.getAll());
    }

    @PreAuthorize("isAuthenticated() and ((#userId == authentication.principal.userId and hasRole('EDITOR')) or hasRole('ADMIN'))")
    @GetMapping("/editor/{userId}")
    public ResponseEntity<List<EventDTO>> getByUserId(@PathVariable("userId") long userId) {
        return ResponseEntity.ok().body(eventService.getEventByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody EventAddRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.add(request));
    }

    @PreAuthorize("isAuthenticated() and (@eventSecurity.isOwner(#id, principal.userId) or hasRole('ADMIN'))")
    @PutMapping("{id}")
    public ResponseEntity<Void> edit(@PathVariable("id") long id, @RequestBody EventEditRequest request) {
        eventService.edit(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        eventService.delete(id);
        return ResponseEntity.ok().build();
    }
}

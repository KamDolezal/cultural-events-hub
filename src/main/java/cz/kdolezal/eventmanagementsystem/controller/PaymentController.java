package cz.kdolezal.eventmanagementsystem.controller;

import cz.kdolezal.eventmanagementsystem.api.PaymentService;
import cz.kdolezal.eventmanagementsystem.api.request.PaymentAddRequestShoppingBasket;
import cz.kdolezal.eventmanagementsystem.dto.PaymentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<PaymentDTO> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(paymentService.get(id));
    }

    @GetMapping("/order/{userId}")
    public ResponseEntity<PaymentDTO> getOrderByUserId(@PathVariable("userId") long id) {
        return ResponseEntity.ok().body(paymentService.getOrderPaymentByUserId(id));
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAll() {
        return ResponseEntity.ok().body(paymentService.getAll());
    }

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody PaymentAddRequestShoppingBasket request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.add(request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        paymentService.delete(id);
        return ResponseEntity.ok().build();
    }
}

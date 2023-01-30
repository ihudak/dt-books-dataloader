package com.dynatrace.ingest.controller;

import com.dynatrace.ingest.model.Ingest;
import com.dynatrace.ingest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ingest")
public class IngestController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    StorageRepository storageRepository;

    // make a payment
    @PostMapping("")
    public Ingest generateData(@RequestBody Ingest ingest) {
        if (ingest.getNumBooksRandVend() + ingest.getNumBooksVend() + ingest.getNumBooksNotvend() < ingest.getNumStorage()) {
            ingest.setNumStorage(ingest.getNumBooksRandVend() + ingest.getNumBooksVend() + ingest.getNumBooksNotvend());
        }
        clearData();

        booksGenerator(ingest);
        for (int i = 0; i < ingest.getNumClients(); i++) {
            clientRepository.create();
        }
        for (int i = 0; i < ingest.getNumRatings(); i++) {
            ratingRepository.create();
        }
        for (int i = 0; i < ingest.getNumCarts(); i++) {
            cartRepository.create();
        }
        for (int i = 0; i < ingest.getNumStorage(); i++) {
            storageRepository.create();
        }
        for (int i = 0; i < ingest.getNumOrders(); i++) {
            orderRepository.create();
        }
        for (int i = 0; i < ingest.getNumSubmitOrders(); i++) {
            orderRepository.update(null); // random order
        }


        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/books")
    public Ingest createBooks(@RequestBody Ingest ingest) {
        booksGenerator(ingest);
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/clients")
    public Ingest createClients(@RequestBody Ingest ingest) {
        for (int i = 0; i < ingest.getNumClients(); i++) {
            clientRepository.create();
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/storage")
    public Ingest ingestStorage(@RequestBody Ingest ingest) {
        for (int i = 0; i < ingest.getNumStorage(); i++) {
            storageRepository.create();
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/cart")
    public Ingest createCarts(@RequestBody Ingest ingest) {
        for (int i = 0; i < ingest.getNumCarts(); i++) {
            cartRepository.create();
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/orders")
    public Ingest createOrders(@RequestBody Ingest ingest) {
        for (int i = 0; i < ingest.getNumOrders(); i++) {
            orderRepository.create();
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/orders/submit")
    public Ingest submitOrders(@RequestBody Ingest ingest) {
        for (int i = 0; i < ingest.getNumSubmitOrders(); i++) {
            orderRepository.update(null); // random order
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/orders/cancel")
    public Ingest cancelOrders(@RequestBody Ingest ingest) {
        for (int i = 0; i < ingest.getNumSubmitOrders(); i++) {
            orderRepository.update(null); // random order
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/ratings")
    public Ingest createRatings(@RequestBody Ingest ingest) {
        for (int i = 0; i < ingest.getNumRatings(); i++) {
            ratingRepository.create();
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }


    @DeleteMapping("")
    public void deleteData() {
        clearData();
    }

    private void booksGenerator(@RequestBody Ingest ingest) {
        for (int i = 0; i < ingest.getNumBooksVend(); i++) {
            if (ingest.isRandomPrice()) {
                bookRepository.create(true);
            } else {
                bookRepository.create(true, 12);
            }
        }
        for (int i = 0; i < ingest.getNumBooksNotvend(); i++) {
            if (ingest.isRandomPrice()) {
                bookRepository.create(false);
            } else {
                bookRepository.create(false, 12);
            }
        }
        for (int i = 0; i < ingest.getNumBooksRandVend(); i++) {
            if (ingest.isRandomPrice()) {
                bookRepository.create();
            } else {
                bookRepository.create(12);
            }
        }
    }

    private void clearData() {
        ratingRepository.deleteAll();
        orderRepository.deleteAll();
        cartRepository.deleteAll();
        storageRepository.deleteAll();
        clientRepository.deleteAll();
        bookRepository.deleteAll();
    }
}

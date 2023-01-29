package com.dynatrace.ingest.controller;

import com.dynatrace.ingest.model.Ingest;
import com.dynatrace.ingest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
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
    @PostMapping("/ingest")
    public Ingest createPayment(@RequestBody Ingest ingest) {
        if (ingest.getNumBooksRandVend() + ingest.getNumBooksVend() + ingest.getNumBooksNotvend() < ingest.getNumStorage()) {
            ingest.setNumStorage(ingest.getNumBooksRandVend() + ingest.getNumBooksVend() + ingest.getNumBooksNotvend());
        }
        clearData();

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

    @DeleteMapping("/ingest")
    public void deleteData() {
        clearData();
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

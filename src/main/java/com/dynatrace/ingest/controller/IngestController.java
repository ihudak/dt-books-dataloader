package com.dynatrace.ingest.controller;

import com.dynatrace.ingest.model.Ingest;
import com.dynatrace.ingest.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(IngestController.class);

    // make a payment
    @PostMapping("")
    public Ingest generateData(@RequestBody Ingest ingest) {
        logger.debug("Generate Data");
        logger.error("Generate Data");
        logger.info("Generate Data");
        if (ingest.getNumBooksRandVend() + ingest.getNumBooksVend() + ingest.getNumBooksNotvend() < ingest.getNumStorage()) {
            logger.info("books");
            ingest.setNumStorage(ingest.getNumBooksRandVend() + ingest.getNumBooksVend() + ingest.getNumBooksNotvend());
        }
        clearData();

        booksGenerator(ingest);
        for (int i = 0; i < ingest.getNumClients(); i++) {
            logger.info("clients");
            clientRepository.create();
        }
        for (int i = 0; i < ingest.getNumRatings(); i++) {
            logger.info("ratings");
            ratingRepository.create();
        }
        for (int i = 0; i < ingest.getNumCarts(); i++) {
            logger.info("carts");
            cartRepository.create();
        }
        for (int i = 0; i < ingest.getNumStorage(); i++) {
            logger.info("storage");
            storageRepository.create();
        }
        for (int i = 0; i < ingest.getNumOrders(); i++) {
            logger.info("orders");
            orderRepository.create();
        }
        for (int i = 0; i < ingest.getNumSubmitOrders(); i++) {
            logger.info("pay orders");
            orderRepository.update(null); // random order
        }


        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/books")
    public Ingest createBooks(@RequestBody Ingest ingest) {
        logger.debug("Generate books");
        logger.error("Generate books");
        logger.info("Generate books");
        booksGenerator(ingest);
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/clients")
    public Ingest createClients(@RequestBody Ingest ingest) {
        logger.debug("Generate clients");
        logger.error("Generate clients");
        logger.info("Generate clients");
        for (int i = 0; i < ingest.getNumClients(); i++) {
            clientRepository.create();
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/storage")
    public Ingest ingestStorage(@RequestBody Ingest ingest) {
        logger.debug("Generate storage");
        logger.error("Generate storage");
        logger.info("Generate storage");
        for (int i = 0; i < ingest.getNumStorage(); i++) {
            storageRepository.create();
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/cart")
    public Ingest createCarts(@RequestBody Ingest ingest) {
        logger.debug("Generate cart");
        logger.error("Generate cart");
        logger.info("Generate cart");
        for (int i = 0; i < ingest.getNumCarts(); i++) {
            cartRepository.create();
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/orders")
    public Ingest createOrders(@RequestBody Ingest ingest) {
        logger.debug("Generate orders");
        logger.error("Generate orders");
        logger.info("Generate orders");
        for (int i = 0; i < ingest.getNumOrders(); i++) {
            orderRepository.create();
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/orders/submit")
    public Ingest submitOrders(@RequestBody Ingest ingest) {
        logger.debug("Generate order pay");
        logger.error("Generate order pay");
        logger.info("Generate order pay");
        for (int i = 0; i < ingest.getNumSubmitOrders(); i++) {
            orderRepository.update(null); // random order
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/orders/cancel")
    public Ingest cancelOrders(@RequestBody Ingest ingest) {
        logger.debug("Generate order cancel");
        logger.error("Generate order cancel");
        logger.info("Generate order cancel");
        for (int i = 0; i < ingest.getNumSubmitOrders(); i++) {
            orderRepository.update(null); // random order
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/ratings")
    public Ingest createRatings(@RequestBody Ingest ingest) {
        logger.debug("Generate ratings");
        logger.error("Generate ratings");
        logger.info("Generate ratings");
        for (int i = 0; i < ingest.getNumRatings(); i++) {
            ratingRepository.create();
        }
        ingest.setCode(200);
        ingest.setMessage("Ok");
        return ingest;
    }


    @DeleteMapping("")
    public void deleteData() {
        logger.debug("Clear Data");
        logger.error("Clear Data");
        logger.info("Clear Data");
        clearData();
    }

    private void booksGenerator(@RequestBody Ingest ingest) {
        logger.debug("Generate Books");
        logger.error("Generate Books");
        logger.info("Generate Books");
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

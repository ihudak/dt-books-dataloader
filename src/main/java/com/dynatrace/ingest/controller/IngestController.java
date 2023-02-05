package com.dynatrace.ingest.controller;

import com.dynatrace.ingest.model.Book;
import com.dynatrace.ingest.model.Client;
import com.dynatrace.ingest.model.Ingest;
import com.dynatrace.ingest.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ingest")
public class IngestController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private StorageRepository storageRepository;
    static private boolean isWorking = false;

    private final Logger logger = LoggerFactory.getLogger(IngestController.class);

    // make a payment
    @PostMapping("")
    public Ingest generateData(@RequestBody Ingest ingest) {
        ingest.setCode(200);
        if (IngestController.isWorking) {
            IngestController.isWorking = false;
            ingest.setMessage("Generation Stopped");
        } else if (ingest.isContinuousLoad()) {
            IngestController.isWorking = true;
            ingest.setMessage("Generation In Loop Started");
            this.getInLoop(ingest);
        } else {
            IngestController.isWorking = true;
            ingest.setMessage("One-time Generation Started");
            this.generate(ingest);
            IngestController.isWorking = false;
        }

        return ingest;
    }

    @PostMapping("/books")
    public Ingest createBooks(@RequestBody Ingest ingest) {
        ingest.setCode(200);
        if (IngestController.isWorking) {
            IngestController.isWorking = false;
            ingest.setMessage("Generation Stopped");
            return ingest;
        }
        logger.info("Generate books");
        booksGenerator(ingest);
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/clients")
    public Ingest createClients(@RequestBody Ingest ingest) {
        ingest.setCode(200);
        if (IngestController.isWorking) {
            IngestController.isWorking = false;
            ingest.setMessage("Generation Stopped");
            return ingest;
        }
        logger.info("Generate clients");
        for (int i = 0; i < ingest.getNumClients(); i++) {
            clientRepository.create();
        }
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/storage")
    public Ingest ingestStorage(@RequestBody Ingest ingest) {
        ingest.setCode(200);
        if (IngestController.isWorking) {
            IngestController.isWorking = false;
            ingest.setMessage("Generation Stopped");
            return ingest;
        }
        logger.info("Generate storage");
        for (int i = 0; i < ingest.getNumStorage(); i++) {
            storageRepository.create();
        }
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/cart")
    public Ingest createCarts(@RequestBody Ingest ingest) {
        ingest.setCode(200);
        if (IngestController.isWorking) {
            IngestController.isWorking = false;
            ingest.setMessage("Generation Stopped");
            return ingest;
        }
        logger.info("Generate cart");
        for (int i = 0; i < ingest.getNumCarts(); i++) {
            cartRepository.create();
        }
        ingest.setMessage("Ok");
        return ingest;
    }

    @PostMapping("/orders")
    public Ingest createOrders(@RequestBody Ingest ingest) {
        ingest.setCode(200);
        if (IngestController.isWorking) {
            IngestController.isWorking = false;
            ingest.setMessage("Generation Stopped");
            return ingest;
        }
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
        ingest.setCode(200);
        if (IngestController.isWorking) {
            IngestController.isWorking = false;
            ingest.setMessage("Generation Stopped");
            return ingest;
        }
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
        ingest.setCode(200);
        if (IngestController.isWorking) {
            IngestController.isWorking = false;
            ingest.setMessage("Generation Stopped");
            return ingest;
        }
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
        ingest.setCode(200);
        if (IngestController.isWorking) {
            IngestController.isWorking = false;
            ingest.setMessage("Generation Stopped");
            return ingest;
        }
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
        if (IngestController.isWorking) {
            IngestController.isWorking = false;
            return;
        }
        logger.info("Clear Data");
        clearData(true);
    }

    private void booksGenerator(@RequestBody Ingest ingest) {
        logger.info("Generate Books");
        // always generate one extra book ( i <= .. in the for loops)
        for (int i = 0; i <= ingest.getNumBooksVend(); i++) {
            if (ingest.isRandomPrice()) {
                bookRepository.create(true);
            } else {
                bookRepository.create(true, 12);
            }
        }
        for (int i = 0; i <= ingest.getNumBooksNotvend(); i++) {
            if (ingest.isRandomPrice()) {
                bookRepository.create(false);
            } else {
                bookRepository.create(false, 12);
            }
        }
        for (int i = 0; i <= ingest.getNumBooksRandVend(); i++) {
            if (ingest.isRandomPrice()) {
                bookRepository.create();
            } else {
                bookRepository.create(12);
            }
        }
    }

    private void clearData(boolean clearBooksAndClients) {
        logger.info("Clearing ratings");
        ratingRepository.deleteAll();
        logger.info("Clearing orders");
        orderRepository.deleteAll();
        logger.info("Clearing carts");
        cartRepository.deleteAll();
        logger.info("Clearing storage");
        storageRepository.deleteAll();

        if (clearBooksAndClients) {
            logger.info("Clearing clients");
            clientRepository.deleteAll();
            logger.info("Clearing books");
            bookRepository.deleteAll();
        }
    }

    @Async
    protected void getInLoop(Ingest ingest) {
        if (!IngestController.isWorking) {
            logger.info("Stopping Generator");
            return;
        }
        while (IngestController.isWorking) {
            this.generate(ingest);
        }
    }

    private void generate(Ingest ingest) {
        logger.info("Generate Data");
        if (ingest.getNumBooksRandVend() + ingest.getNumBooksVend() + ingest.getNumBooksNotvend() < ingest.getNumStorage()) {
            ingest.setNumStorage(ingest.getNumBooksRandVend() + ingest.getNumBooksVend() + ingest.getNumBooksNotvend());
        }
        logger.info("clearing data");
        boolean regenerateBooksAndClients = ingest.getNumBooks() > Book.getNumOfISBNs() || ingest.getNumClients() > Client.getNumOfClients();
        clearData(regenerateBooksAndClients);

        if (regenerateBooksAndClients) {
            logger.info("books");
            booksGenerator(ingest);
            for (int i = 0; i < ingest.getNumClients(); i++) {
                logger.info("clients");
                clientRepository.create();
            }
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
        for (int i = 0; i < ingest.getNumRatings(); i++) {
            logger.info("ratings");
            ratingRepository.create();
        }
    }
}

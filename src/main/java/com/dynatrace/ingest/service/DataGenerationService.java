package com.dynatrace.ingest.service;

import com.dynatrace.ingest.controller.IngestController;
import com.dynatrace.ingest.model.Ingest;
import com.dynatrace.ingest.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DataGenerationService {
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
    private final Logger logger = LoggerFactory.getLogger(DataGenerationService.class);

    @Async
    public void getInLoop(Ingest ingest) {
        if (!IngestController.isWorking()) {
            logger.info("Stopping Generator");
            return;
        }
        while (IngestController.isWorking()) {
            this.generate(ingest);
        }
    }

    public void generate(Ingest ingest) {
        logger.info("Generate Data");
        if (ingest.getNumBooksRandVend() + ingest.getNumBooksVend() + ingest.getNumBooksNotvend() < ingest.getNumStorage()) {
            ingest.setNumStorage(ingest.getNumBooksRandVend() + ingest.getNumBooksVend() + ingest.getNumBooksNotvend());
        }
        logger.info("clearing data");
        clearData();

        logger.info("books");
        booksGenerator(ingest);
        for (int i = 0; i < ingest.getNumClients(); i++) {
            logger.info("clients");
            clientRepository.create();
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

    public void clearData() {
        logger.info("Clearing orders");
        orderRepository.deleteAll();
        logger.info("Clearing carts");
        cartRepository.deleteAll();
        logger.info("Clearing storage");
        storageRepository.deleteAll();
        logger.info("Clearing clients");
        clientRepository.deleteAll();
        logger.info("Clearing books");
        bookRepository.deleteAll();
        logger.info("Clearing ratings");
        ratingRepository.deleteAll();
    }

    public void booksGenerator(@RequestBody Ingest ingest) {
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
}

package com.dynatrace.ingest.controller;

import com.dynatrace.ingest.model.Ingest;
import com.dynatrace.ingest.repository.*;
import com.dynatrace.ingest.service.DataGenerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ingest")
public class IngestController {
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
    @Autowired
    private DataGenerationService dataGenerationService;
    static private boolean isWorking = false;

    public static boolean isWorking() {
        return isWorking;
    }

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
            dataGenerationService.getInLoop(ingest);
        } else {
            IngestController.isWorking = true;
            ingest.setMessage("One-time Generation Started");
            dataGenerationService.generate(ingest);
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
        dataGenerationService.booksGenerator(ingest);
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
        dataGenerationService.clearData();
    }
}

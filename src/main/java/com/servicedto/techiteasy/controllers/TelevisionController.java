package com.servicedto.techiteasy.controllers;

import com.servicedto.techiteasy.exceptions.ProductNameTooLongException;
import com.servicedto.techiteasy.exceptions.RecordNotFoundException;
import com.servicedto.techiteasy.models.Television;
import com.servicedto.techiteasy.repositories.TelevisionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/televisions")
public class TelevisionController {

    private final TelevisionRepository repo;

    public TelevisionController(TelevisionRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Television>> getAllTelevisions() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTelevision(@PathVariable Long id) {
        if (repo.findById(id).isPresent()) {
            return ResponseEntity.ok(repo.findByIdIs(id));
        } else {
            throw new RecordNotFoundException("No television in database with id " + id + "!");
        }
    }

    @PostMapping
    public ResponseEntity<?> createTelevision(@RequestBody Television television) {
        if (television.getName().length() < 21) {
            try {
                this.repo.save(television);
                URI uri = URI.create(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + television.getId()).toUriString());
                return ResponseEntity.created(uri).body(television);
            } catch (Exception ex) {
                return ResponseEntity.unprocessableEntity().body("Failed to create television");
            }
        } else {
            throw new ProductNameTooLongException("Name for new television exceeds 20 characters! Please use a shorter name.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTelevision(@PathVariable Long id, @RequestBody Television updateTelevisionDetails) {
        if (updateTelevisionDetails.getName().length() < 21) {
            Television updateTelevision = repo.findById(id).orElseThrow(() -> new RecordNotFoundException("No television in list with id " + id + "!"));

            updateTelevision.setType(updateTelevisionDetails.getType());
            updateTelevision.setBrand(updateTelevisionDetails.getBrand());
            updateTelevision.setName(updateTelevisionDetails.getName());
            updateTelevision.setPrice(updateTelevisionDetails.getPrice());
            updateTelevision.setAvailableSize(updateTelevisionDetails.getAvailableSize());
            updateTelevision.setRefreshRate(updateTelevisionDetails.getRefreshRate());
            updateTelevision.setScreenType(updateTelevisionDetails.getScreenType());
            updateTelevision.setScreenQuality(updateTelevisionDetails.getScreenQuality());
            updateTelevision.setSmartTv(updateTelevisionDetails.isSmartTv());
            updateTelevision.setWifi(updateTelevisionDetails.isWifi());
            updateTelevision.setVoiceControl(updateTelevisionDetails.isVoiceControl());
            updateTelevision.setHdr(updateTelevisionDetails.isHdr());
            updateTelevision.setBluetooth(updateTelevisionDetails.isBluetooth());
            updateTelevision.setAmbiLight(updateTelevisionDetails.isAmbiLight());
            updateTelevision.setOriginalStock(updateTelevisionDetails.getOriginalStock());
            updateTelevision.setSold(updateTelevisionDetails.getSold());

            repo.save(updateTelevision);

            return ResponseEntity.ok("Updated television #" + id + ".");

        } else {
            throw new ProductNameTooLongException("New name for television #" + id + " exceeds 20 characters! Please use a shorter name.");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTelevision(@PathVariable Long id) {
        if (repo.findById(id).isPresent()) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new RecordNotFoundException("No television in list with id " + id + "!");
        }

    }

}

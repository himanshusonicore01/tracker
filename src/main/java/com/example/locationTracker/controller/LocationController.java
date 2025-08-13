package com.example.locationTracker.controller;
import com.example.locationTracker.entity.LocationData;
import com.example.locationTracker.repository.LocationRepository;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationRepository repo;

    public LocationController(LocationRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<LocationData> ingest(@Valid @RequestBody LocationData body) {
        body.setReceivedAt(Instant.now());
        return ResponseEntity.ok(repo.save(body));
    }

    @GetMapping("/latest/{deviceId}")
    public ResponseEntity<LocationData> latest(@PathVariable String deviceId) {
        LocationData latest = repo.findTopByDeviceIdOrderByTimestampDesc(deviceId);
        return ResponseEntity.ok(latest);
    }

    @GetMapping("/history/{deviceId}")
    public ResponseEntity<List<LocationData>> history(
            @PathVariable String deviceId,
            @RequestParam("since") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant since) {
        return ResponseEntity.ok(repo.findByDeviceIdAndTimestampAfterOrderByTimestampAsc(deviceId, since));
    }
}
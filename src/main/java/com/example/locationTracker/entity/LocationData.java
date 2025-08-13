package com.example.locationTracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "locations", indexes = {
        @Index(name = "idx_device_time", columnList = "deviceId,timestamp")
})
public class LocationData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String deviceId;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private Float accuracyMeters; // optional

    @NotNull
    private Instant timestamp; // when the reading happened on device

    private Instant receivedAt = Instant.now(); // server time
}
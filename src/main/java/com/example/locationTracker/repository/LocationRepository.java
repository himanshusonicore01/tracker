package com.example.locationTracker.repository;

import com.example.locationTracker.entity.LocationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationData, Long> {
    LocationData findTopByDeviceIdOrderByTimestampDesc(String deviceId);

    List<LocationData> findByDeviceIdAndTimestampAfterOrderByTimestampAsc(String deviceId, Instant since);

    @Query("select l from LocationData l where l.deviceId = :deviceId order by l.timestamp desc limit 100")
    List<LocationData> latest100(String deviceId);
}

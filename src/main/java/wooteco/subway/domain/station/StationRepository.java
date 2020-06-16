package wooteco.subway.domain.station;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface StationRepository extends JpaRepository<Station, Long> {
    @Override
    List<Station> findAllById(Iterable ids);

    @Override
    List<Station> findAll();

    Optional<Station> findByName(String stationName);
}

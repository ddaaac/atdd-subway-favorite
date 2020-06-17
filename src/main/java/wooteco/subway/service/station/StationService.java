package wooteco.subway.service.station;

import org.springframework.stereotype.Service;
import wooteco.subway.service.line.LineStationService;
import wooteco.subway.domain.station.Station;
import wooteco.subway.domain.station.StationRepository;

import java.util.List;

@Service
public class StationService {
    private LineStationService lineStationService;
    private StationRepository stationRepository;

    public StationService(LineStationService lineStationService, StationRepository stationRepository) {
        this.lineStationService = lineStationService;
        this.stationRepository = stationRepository;
    }

    public Station createStation(Station station) {
        return stationRepository.save(station);
    }

    public List<Station> findStations() {
        return stationRepository.findAll();
    }

    public void deleteStationById(Long id) {
        Station station = stationRepository.findById(id).orElseThrow(RuntimeException::new);
        lineStationService.deleteLineStationByStation(station);
        stationRepository.deleteById(id);
    }

    public Station findById(Long stationId) {
        return stationRepository.findById(stationId).orElseThrow(RuntimeException::new);
    }
}

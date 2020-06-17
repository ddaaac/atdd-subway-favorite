package wooteco.subway.service.line;

import org.springframework.stereotype.Service;

import wooteco.subway.domain.line.LineStation;
import wooteco.subway.domain.line.LineStationRepository;
import wooteco.subway.service.line.dto.LineDetailResponse;
import wooteco.subway.service.line.dto.LineStationCreateRequest;
import wooteco.subway.service.line.dto.WholeSubwayResponse;
import wooteco.subway.domain.line.Line;
import wooteco.subway.domain.line.LineRepository;
import wooteco.subway.domain.line.Lines;
import wooteco.subway.domain.station.Station;
import wooteco.subway.domain.station.StationRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LineStationService {
    private LineRepository lineRepository;
    private StationRepository stationRepository;
    private LineStationRepository lineStationRepository;

    public LineStationService(LineRepository lineRepository, StationRepository stationRepository,
        LineStationRepository lineStationRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
        this.lineStationRepository = lineStationRepository;
    }

    public LineDetailResponse findLineWithStationsById(Long lineId) {
        Line line = lineRepository.findById(lineId).orElseThrow(RuntimeException::new);
        List<Station> stations = line.getAllStations();

        return LineDetailResponse.of(line, stations);
    }

    public WholeSubwayResponse findLinesWithStations() {
        Lines lines = new Lines(lineRepository.findAll());

        List<LineDetailResponse> lineDetailResponses = lines.getLines().stream()
            .map(it -> LineDetailResponse.of(it, it.getAllStations()))
            .collect(Collectors.toList());

        return WholeSubwayResponse.of(lineDetailResponses);
    }

    public void deleteLineStationByStation(Station station) {
        List<Line> lines = lineRepository.findAll();
        lines.forEach(it -> it.removeLineStationBy(station));
        lineRepository.saveAll(lines);
    }

    public void deleteLineStation(Line line, Station station) {
        List<LineStation> lineStations = line.getStations()
            .stream()
            .filter(it -> Objects.equals(it.getStation(), station))
            .collect(Collectors.toList());
        for (LineStation lineStation : lineStations) {
            lineStation.getLine().removeLineStation(lineStation);
        }
        lineStationRepository.deleteById(lineStations.get(0).getId());
        lineStationRepository.flush();
    }

    public LineStation createLineStation(LineStationCreateRequest request) {
        Station preStation = findPreStationOrNull(request.getPreStationId());
        Station station = stationRepository.findById(request.getStationId()).orElseThrow(RuntimeException::new);
        LineStation lineStation = new LineStation(preStation, station, request.getDistance(), request.getDuration());
        return lineStationRepository.save(lineStation);
    }

    private Station findPreStationOrNull(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return stationRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}

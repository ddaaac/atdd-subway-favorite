package wooteco.subway.service.line;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import wooteco.subway.domain.line.Line;
import wooteco.subway.domain.line.LineRepository;
import wooteco.subway.domain.line.LineStation;
import wooteco.subway.domain.station.Station;
import wooteco.subway.service.line.dto.LineStationCreateRequest;
import wooteco.subway.service.station.StationService;

@ExtendWith(MockitoExtension.class)
public class LineServiceTest {
    private static final String STATION_NAME1 = "강남역";
    private static final String STATION_NAME2 = "역삼역";
    private static final String STATION_NAME3 = "선릉역";
    private static final String STATION_NAME4 = "삼성역";

    @Mock
    private LineRepository lineRepository;
    @Mock
    private LineStationService lineStationService;
    @Mock
    private StationService stationService;

    private LineService lineService;

    private Line line;
    private Station station1;
    private Station station2;
    private Station station3;
    private Station station4;

    @BeforeEach
    void setUp() {
        lineService = new LineService(lineStationService, lineRepository, stationService);

        station1 = new Station(1L, STATION_NAME1);
        station2 = new Station(2L, STATION_NAME2);
        station3 = new Station(3L, STATION_NAME3);
        station4 = new Station(4L, STATION_NAME4);

        line = new Line(1L, "2호선", LocalTime.of(05, 30), LocalTime.of(22, 30), 5);
        line.addLineStation(new LineStation(null, station1, 10, 10));
        line.addLineStation(new LineStation(station1, station2, 10, 10));
        line.addLineStation(new LineStation(station2, station3, 10, 10));
    }

    @Test
    void addLineStationAtTheFirstOfLine() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        when(lineStationService.createLineStation(any())).thenReturn(new LineStation(null, station4, 10, 10));

        LineStationCreateRequest request = new LineStationCreateRequest(null, station4.getId(), 10, 10);
        lineService.addLineStation(line.getId(), request);

        assertThat(line.getStations()).hasSize(4);

        List<Station> stations = line.getAllStations();
        assertThat(stations.get(0)).isEqualTo(station4);
        assertThat(stations.get(1)).isEqualTo(station1);
        assertThat(stations.get(2)).isEqualTo(station2);
        assertThat(stations.get(3)).isEqualTo(station3);
    }

    @Test
    void addLineStationBetweenTwo() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        when(lineStationService.createLineStation(any())).thenReturn(new LineStation(station1, station4, 10, 10));

        LineStationCreateRequest request = new LineStationCreateRequest(station1.getId(), station4.getId(), 10, 10);
        lineService.addLineStation(line.getId(), request);

        assertThat(line.getStations()).hasSize(4);

        List<Station> stations = line.getAllStations();
        assertThat(stations.get(0)).isEqualTo(station1);
        assertThat(stations.get(1)).isEqualTo(station4);
        assertThat(stations.get(2)).isEqualTo(station2);
        assertThat(stations.get(3)).isEqualTo(station3);
    }

    @Test
    void addLineStationAtTheEndOfLine() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        when(lineStationService.createLineStation(any())).thenReturn(new LineStation(station3, station4, 10, 10));

        LineStationCreateRequest request = new LineStationCreateRequest(station3.getId(), station4.getId(), 10, 10);
        lineService.addLineStation(line.getId(), request);

        assertThat(line.getStations()).hasSize(4);

        List<Station> stationIds = line.getAllStations();
        assertThat(stationIds.get(0)).isEqualTo(station1);
        assertThat(stationIds.get(1)).isEqualTo(station2);
        assertThat(stationIds.get(2)).isEqualTo(station3);
        assertThat(stationIds.get(3)).isEqualTo(station4);
    }

    @Test
    void removeLineStationAtTheFirstOfLine() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        lineService.removeLineStation(line.getId(), station1.getId());
        line.removeLineStationBy(station1);

        assertThat(line.getStations()).hasSize(2);

        List<Station> stationIds = line.getAllStations();
        assertThat(stationIds.get(0)).isEqualTo(station2);
        assertThat(stationIds.get(1)).isEqualTo(station3);
    }

    @Test
    void removeLineStationBetweenTwo() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        lineService.removeLineStation(line.getId(), station2.getId());
    }

    @Test
    void removeLineStationAtTheEndOfLine() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        lineService.removeLineStation(line.getId(), station3.getId());
        line.removeLineStationBy(station3);

        assertThat(line.getStations()).hasSize(2);

        List<Station> stationIds = line.getAllStations();
        assertThat(stationIds.get(0)).isEqualTo(station1);
        assertThat(stationIds.get(1)).isEqualTo(station2);
    }
}

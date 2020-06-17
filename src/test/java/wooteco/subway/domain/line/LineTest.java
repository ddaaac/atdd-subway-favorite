package wooteco.subway.domain.line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import wooteco.subway.domain.station.Station;

public class LineTest {
    private static final String STATION_NAME1 = "강남역";
    private static final String STATION_NAME2 = "역삼역";
    private static final String STATION_NAME3 = "선릉역";
    private static final String STATION_NAME4 = "삼성역";

    private Line line;
    private Station station1;
    private Station station2;
    private Station station3;
    private Station station4;

    @BeforeEach
    void setUp() {
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
    void addLineStation() {
        line.addLineStation(new LineStation(null, station4, 10, 10));
        assertThat(line.getStations()).hasSize(4);
    }

    @Test
    void getLineStations() {
        List<Station> stations = line.getAllStations();

        assertThat(stations.size()).isEqualTo(3);
        assertThat(stations.get(0)).isEqualTo(station1);
        assertThat(stations.get(1)).isEqualTo(station2);
        assertThat(stations.get(2)).isEqualTo(station3);
    }

    @Test
    void removeLineStation() {
        line.removeLineStationBy(station2);

        assertThat(line.getStations()).hasSize(2);
    }
}

package wooteco.subway.domain.line;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

import wooteco.subway.domain.station.Station;

@DataJpaTest
public class LineRepositoryTest {
    @Autowired
    private LineRepository lineRepository;

    @Test
    void addLineStation() {
        // given
        Line line = new Line("2호선", LocalTime.of(05, 30), LocalTime.of(22, 30), 5);
        Line persistLine = lineRepository.save(line);
        Station jamsil = new Station(1L, "잠실역");
        Station dogok = new Station(2L, "도곡역");
        persistLine.addLineStation(new LineStation(null, jamsil, 10, 10));
        persistLine.addLineStation(new LineStation(jamsil, dogok, 10, 10));

        // when
        Line resultLine = lineRepository.save(persistLine);

        // then
        assertThat(resultLine.getStations()).hasSize(2);
    }
}

package wooteco.subway.domain.line;

import static java.util.Arrays.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import wooteco.subway.domain.station.Station;

@Embeddable
public class LineStations {
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "line")
    private List<LineStation> stations = new ArrayList<>();

    public LineStations(List<LineStation> stations) {
        this.stations = stations;
    }

    public LineStations() {
    }

    public static LineStations empty() {
        return new LineStations(new ArrayList<>());
    }

    public List<LineStation> getStations() {
        return stations;
    }

    public void setStations(List<LineStation> stations) {
        this.stations = stations;
    }

    public void add(LineStation targetLineStation) {
        if (stations.contains(targetLineStation)) {
            return;
        }
        updatePreStationOfNextLineStation(targetLineStation.getPreStation(), targetLineStation.getStation());
        stations.add(targetLineStation);
    }

    void remove(LineStation targetLineStation) {
        updatePreStationOfNextLineStation(targetLineStation.getStation(), targetLineStation.getPreStation());
        stations.remove(targetLineStation);
    }

    public void removeById(Station targetStation) {
        extractByStationId(targetStation)
                .ifPresent(this::remove);
    }

    private void updatePreStationOfNextLineStation(Station targetStation, Station newPreStation) {
        extractByPreStationId(targetStation)
                .ifPresent(it -> it.updatePreLineStation(newPreStation));
    }

    private Optional<LineStation> extractByStationId(Station station) {
        return stations.stream()
                .filter(it -> Objects.equals(it.getStation(), station))
                .findFirst();
    }

    private Optional<LineStation> extractByPreStationId(Station preStation) {
        return stations.stream()
                .filter(it -> Objects.equals(it.getPreStation(), preStation))
                .findFirst();
    }

    public List<Station> getAllStations() {
        return stations.stream()
            .flatMap(station -> Stream.of(station.getPreStation(), station.getStation()))
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
    }
}

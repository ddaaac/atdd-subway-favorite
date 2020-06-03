package wooteco.subway.domain.station;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Stations {
    private List<Station> stations;

    public Stations(List<Station> stations) {
        this.stations = stations;
    }

    public List<Station> getStations() {
        return stations;
    }

    public Map<Long, Station> convertToMap() {
        return stations.stream()
            .collect(Collectors.toMap(Station::getId, Function.identity()));
    }
}

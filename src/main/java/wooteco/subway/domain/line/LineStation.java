package wooteco.subway.domain.line;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import wooteco.subway.domain.station.Station;

@Entity
public class LineStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_station_id")
    private Long id;

    private int distance;
    private int duration;

    @ManyToOne
    @JoinColumn(name = "pre_station_id")
    private Station preStation;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    public LineStation(Station preStation, Station station, int distance, int duration) {
        this.preStation = preStation;
        this.station = station;
        this.distance = distance;
        this.duration = duration;
    }

    public LineStation() {
    }

    public Long getId() {
        return id;
    }

    public int getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public Line getLine() {
        return line;
    }

    public Station getPreStation() {
        return preStation;
    }

    public Station getStation() {
        return station;
    }

    public void setLine(Line line) {
        if (Objects.equals(line, this.line)) {
            return;
        }
        this.line = line;
        line.addLineStation(this);
    }

    public void setPreStation(Station preStation) {
        this.preStation = preStation;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void updatePreLineStation(Station preStation) {
        this.preStation = preStation;
    }

    public boolean isLineStationOf(Long preStationId, Long stationId) {
        return this.preStation.getId() == preStationId && this.station.getId() == stationId
                || this.preStation.getId() == stationId && this.station.getId() == preStationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LineStation that = (LineStation)o;
        return distance == that.distance &&
            duration == that.duration &&
            Objects.equals(preStation, that.preStation) &&
            Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preStation, station, distance, duration);
    }
}

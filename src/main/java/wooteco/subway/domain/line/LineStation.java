package wooteco.subway.domain.line;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LineStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_station_id")
    private Long id;
    private Long preStationId;
    private Long stationId;
    private int distance;
    private int duration;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    public LineStation(Long preStationId, Long stationId, int distance, int duration) {
        this.preStationId = preStationId;
        this.stationId = stationId;
        this.distance = distance;
        this.duration = duration;
    }

    public LineStation() {
    }

    public Long getPreStationId() {
        return preStationId;
    }

    public Long getStationId() {
        return stationId;
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

    public void setLine(Line line) {
        if (Objects.equals(line, this.line)) {
            return;
        }
        this.line = line;
        line.addLineStation(this);
    }

    public void updatePreLineStation(Long preStationId) {
        this.preStationId = preStationId;
    }

    public boolean isLineStationOf(Long preStationId, Long stationId) {
        return this.preStationId == preStationId && this.stationId == stationId
                || this.preStationId == stationId && this.stationId == preStationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineStation that = (LineStation) o;
        return distance == that.distance &&
                duration == that.duration &&
                Objects.equals(preStationId, that.preStationId) &&
                Objects.equals(stationId, that.stationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preStationId, stationId, distance, duration);
    }
}

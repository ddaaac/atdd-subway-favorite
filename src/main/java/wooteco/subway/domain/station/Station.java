package wooteco.subway.domain.station;

import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Station {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @CreatedDate
    private LocalDateTime createdAt;

    public Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public Station(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

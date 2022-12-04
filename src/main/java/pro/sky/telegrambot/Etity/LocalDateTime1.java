package pro.sky.telegrambot.Etity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
@Entity
public class LocalDateTime1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public LocalDateTime1(Long id, LocalDateTime localDateTime) {
        this.id = id;
        this.localDateTime = localDateTime;
    }

    private LocalDateTime localDateTime;

    public LocalDateTime1() {

    }

    @Override
    public String toString() {
        return "LocalDateTime1{" +
                "localDateTime=" + localDateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocalDateTime1)) return false;
        LocalDateTime1 that = (LocalDateTime1) o;
        return Objects.equals(localDateTime, that.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localDateTime);
    }



    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime1(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}

package pro.sky.telegrambot.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.Etity.LocalDateTime1;
@Repository
public interface LocalDateTimeRepos extends JpaRepository<LocalDateTime1, Long> {
}

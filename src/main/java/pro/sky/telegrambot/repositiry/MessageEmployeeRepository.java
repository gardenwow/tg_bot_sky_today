package pro.sky.telegrambot.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.Etity.MessageEmployee;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface MessageEmployeeRepository  extends JpaRepository<MessageEmployee, Long> {

    Collection<MessageEmployee> findByLocalDateTime(LocalDateTime localDateTime);
}

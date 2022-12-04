package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.Etity.LocalDateTime1;
import pro.sky.telegrambot.Etity.MessageEmployee;
import pro.sky.telegrambot.repositiry.LocalDateTimeRepos;
import pro.sky.telegrambot.repositiry.MessageEmployeeRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final LocalDateTimeRepos localDateTimeRepos;
    private final MessageEmployeeRepository messageEmployeeRepository;
    private final Pattern patternMessage = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
    private final Pattern patternText = Pattern.compile("[a-zA-Z]");

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private long chatId;

    public TelegramBotUpdatesListener(LocalDateTimeRepos localDateTimeRepos,
                                      MessageEmployeeRepository messageEmployeeRepository,
                                      TelegramBot telegramBot) {
        this.localDateTimeRepos = localDateTimeRepos;
        this.messageEmployeeRepository = messageEmployeeRepository;

        this.telegramBot = telegramBot;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            chatId = update.message().chat().id();
            if (update.message().text().equals("/start")) {
                SendResponse response = telegramBot.execute(new SendMessage(chatId, "zdarova"));
            }
            Matcher matcher = patternMessage.matcher(update.message().text());
            if (matcher.matches()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                LocalDateTime localDateTime = LocalDateTime.parse(update.message().text().substring(0, 16), formatter);
                String msgUser = update.message().text().substring(17);
                messageEmployeeRepository.save(new MessageEmployee(localDateTime, msgUser));

            }
            runMessage();

            //с раздницей пытался разобраться, не обращай внимания
            /*LocalDateTime d0 = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            LocalDateTime d1 = messageEmployeeRepository.findAll().
            stream()
            .map(e -> e.getLocalDateTime())
            .collect(Collectors.toList()).get(0);

            telegramBot.execute(new SendMessage(chatId, d1.toString() + messageEmployeeRepository.findAll().get(0)));
            System.out.println("d1 = " + d1);*/


        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;

    }


    @Scheduled(cron = "0 0/1 * * * *")
    public void runMessage() {
        Collection<MessageEmployee> messageToday = messageEmployeeRepository.
                findByLocalDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        telegramBot.execute(new SendMessage(chatId, messageToday.toString()));
        telegramBot.execute(new SendMessage(chatId, "privet"));

    }

}

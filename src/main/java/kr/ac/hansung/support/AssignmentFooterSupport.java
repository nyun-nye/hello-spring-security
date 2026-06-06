package kr.ac.hansung.support;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AssignmentFooterSupport {

    private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");

    private static final String[] DAY_OF_WEEK_KO = {"월", "화", "수", "목", "금", "토", "일"};

    @Value("${assignment.student-id:2371058}")
    private String studentId;

    @Value("${assignment.student-name:윤예진}")
    private String studentName;

    public String footerLine() {
        ZonedDateTime now = ZonedDateTime.now(SEOUL);
        String datePart = now.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.KOREA));
        String dow = "(" + DAY_OF_WEEK_KO[now.getDayOfWeek().getValue() - 1] + ")";
        String timePart = now.format(DateTimeFormatter.ofPattern("a hh:mm:ss", Locale.KOREA));
        return "실습 수행 일시: " + datePart + " " + dow + " " + timePart
                + " | 학번: " + studentId
                + " | 성명: " + studentName;
    }
}

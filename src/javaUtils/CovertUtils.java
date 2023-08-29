package javaUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CovertUtils {
    public static LocalDate ConvertStringtoDate(String date) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.ENGLISH);
		LocalDate localDate = LocalDate.parse(date,dateTimeFormatter);
		return localDate;
	}
}

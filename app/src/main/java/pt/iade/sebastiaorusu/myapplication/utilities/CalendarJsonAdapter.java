package pt.iade.sebastiaorusu.myapplication.utilities;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CalendarJsonAdapter implements JsonSerializer<Calendar>, JsonDeserializer<Calendar> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);

    @Override
    public JsonElement serialize(Calendar src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return null;
        }
        ZonedDateTime zdt = ((GregorianCalendar) src).toZonedDateTime();
        return new JsonPrimitive(FORMATTER.format(zdt));
    }

    @Override
    public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null) {
            return null;
        }
        try {
            ZonedDateTime zdt = ZonedDateTime.parse(json.getAsString(), FORMATTER);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(zdt.toInstant().toEpochMilli());
            return calendar;
        } catch (Exception e) {
            throw new JsonParseException("Could not parse date: " + json.getAsString(), e);
        }
    }
}
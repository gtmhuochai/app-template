package edu.jmu.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Instant类型序列化转化器
 *
 * @author LiuJiaJun
 * @date 2018/11/6 21:23
 */
public class InstantSerializer extends JsonSerializer<Instant> {
    private String chinaZone = "Asia/Shanghai";
    private String timePattern = "yyyy-MM-dd HH:mm:ss";
    @Override
    public void serialize(Instant instant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(LocalDateTime.ofInstant(instant,ZoneId.of(chinaZone)).format(DateTimeFormatter.ofPattern(timePattern)));
    }
}

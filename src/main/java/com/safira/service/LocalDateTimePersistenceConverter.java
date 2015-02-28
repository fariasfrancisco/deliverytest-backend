package com.safira.service;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by francisco on 28/02/15.
 */
@Converter
public class LocalDateTimePersistenceConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public java.sql.Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {
        if (entityValue != null) {
            return java.sql.Timestamp.valueOf(entityValue);
        }
        return null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(java.sql.Timestamp databaseValue) {
        if (databaseValue != null) {
            return databaseValue.toLocalDateTime();
        }
        return null;
    }
}

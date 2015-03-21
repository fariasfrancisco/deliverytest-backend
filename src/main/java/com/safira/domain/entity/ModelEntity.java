package com.safira.domain.entity;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by francisco on 20/03/15.
 */
@MappedSuperclass
public abstract class ModelEntity extends AbstractPersistable<Long> {

    @Column(length = 36)
    private String uuid;

    @Type(type = "com.safira.common.LocalDateTimeUserType")
    private LocalDateTime timeCreated;

    public ModelEntity() {
        this(UUID.randomUUID());
    }

    public ModelEntity(UUID guid) {
        Assert.notNull(guid, "UUID is required");
        setUuid(guid.toString());
        this.timeCreated = LocalDateTime.now();
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int hashCode() {
        return getUuid().hashCode();
    }

    public String getIdentifier() {
        return getUuid().toString();
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

}

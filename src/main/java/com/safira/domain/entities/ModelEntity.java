package com.safira.domain.entities;

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

    @Column(length = 36, nullable = false, unique = true)
    private String uuid;

    private LocalDateTime timeCreated;

    public ModelEntity() {
        this(UUID.randomUUID().toString());
    }

    public ModelEntity(String guid) {
        Assert.notNull(guid, "UUID is required");
        setUuid(guid.toString());
        this.timeCreated = LocalDateTime.now();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int hashCode() {
        return getUuid().hashCode();
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

}

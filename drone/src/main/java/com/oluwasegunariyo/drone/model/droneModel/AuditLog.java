package com.oluwasegunariyo.drone.model.droneModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "auditLog")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditLog_seq")
    @SequenceGenerator(name = "auditLog_seq", sequenceName = "auditLog_seq", initialValue = 1, allocationSize = 1)
    private long id;

    private int droneId;

    private int batteryCapacity;

    @JsonProperty(value = "date_created")
    private Date date_created;

    public AuditLog(){super();}

    public AuditLog(int droneId, int batteryCapacity, Date date_created) {
        this.droneId = droneId;
        this.batteryCapacity = batteryCapacity;
        this.date_created = date_created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDroneId() {
        return droneId;
    }

    public void setDroneId(int droneId) {
        this.droneId = droneId;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "id=" + id +
                ", droneId=" + droneId +
                ", batteryCapacity=" + batteryCapacity +
                ", date_created=" + date_created +
                '}';
    }
}

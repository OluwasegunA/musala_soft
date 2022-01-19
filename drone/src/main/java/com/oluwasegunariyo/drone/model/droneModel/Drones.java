package com.oluwasegunariyo.drone.model.droneModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oluwasegunariyo.drone.model.enums.StateEnums;
import com.oluwasegunariyo.drone.model.medicationModel.Medication;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "drones")

public class Drones {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drones_seq")
    @SequenceGenerator(name = "drones_seq", sequenceName = "drones_seq", initialValue = 1, allocationSize = 1)
    private int id;

    @Length(max = 100)
    @NotNull(message="Please enter a Serial Number")
    private String serialNumber;

    @Column(unique = true)
    private String model;

    //@Min(1)
    //@Max(5)
    private int weightLimit;

    @NotNull(message="Please enter Battery Capacity")
    //@Size(min = 1, max = 5, message = "Battery Capacity must be between 1 and 5 characters")
    //@Pattern(regexp = "^[0-9]$", message="Battery Capacity must use numbers")
    private int batteryCapacity;

    @NotNull(message="Please provide drone State")
    private StateEnums state;

    @JsonProperty(value = "date_created")
    private Date date_created;

    @JsonProperty(value = "last_updated")
    private Date last_updated;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @Column(nullable = true)
    private List<Medication> medications;

    public Drones(){super();}

    public Drones(@NotNull(message = "Please enter a Serial Number") @Length(max = 100) String serialNumber, String model, int weightLimit, @NotNull(message = "Please enter Battery Capacity") int batteryCapacity, @NotNull(message = "Please provide drone State") StateEnums state, Date date_created, Date last_updated, List<Medication> medications) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
        this.date_created = date_created;
        this.last_updated = last_updated;
        this.medications = medications;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(int weightLimit) {
        this.weightLimit = weightLimit;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public StateEnums getState() {
        return state;
    }

    public void setState(StateEnums state) {
        this.state = state;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    @Override
    public String toString() {
        return "Drones{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", model='" + model + '\'' +
                ", weightLimit=" + weightLimit +
                ", batteryCapacity=" + batteryCapacity +
                ", state=" + state +
                ", date_created=" + date_created +
                ", last_updated=" + last_updated +
                ", medications=" + medications +
                '}';
    }
}

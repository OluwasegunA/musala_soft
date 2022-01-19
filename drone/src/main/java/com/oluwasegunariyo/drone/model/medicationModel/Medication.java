package com.oluwasegunariyo.drone.model.medicationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

/*import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;*/

@Entity
@Table(name = "medications")

public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medications_seq")
    @SequenceGenerator(name = "medications_seq", sequenceName = "medications_seq", initialValue = 1, allocationSize = 1)
    private int id;

    @Column(unique = true)
    @NotNull(message="Please enter Medication name")
    @Pattern(regexp = "^([A-Za-z0-9\\-\\_]+)$", message="Medication name must be a character of letters, numbers, hyphen and underscore")
    private String name;

    private int weight;

    @Column(unique = true)
    @NotNull(message="Please enter Medication code")
    @Pattern(regexp = "^([A-Z0-9\\-\\_]+)$", message="Medication code must be a character of Upper case letters, numbers, and underscore")
    private String code;

    private String imageUrl;

    @JsonProperty(value = "date_created")
    private Date date_created;

    @JsonProperty(value = "last_updated")
    private Date last_updated;

    public Medication() {
        super();
    }

    public Medication(@NotNull(message = "Please enter Medication name") @Pattern(regexp = "^([A-Za-z0-9\\-\\_]+)$", message = "Medication name must be a character of letters, numbers, hyphen and underscore") String name, int weight, @NotNull(message = "Please enter Medication code") @Pattern(regexp = "^([A-Z0-9\\-\\_]+)$", message = "Medication code must be a character of Upper case letters, numbers, and underscore") String code, String imageUrl, Date date_created, Date last_updated) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.imageUrl = imageUrl;
        this.date_created = date_created;
        this.last_updated = last_updated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", code='" + code + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", date_created=" + date_created +
                ", last_updated=" + last_updated +
                '}';
    }
}

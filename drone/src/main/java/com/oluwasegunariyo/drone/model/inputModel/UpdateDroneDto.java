package com.oluwasegunariyo.drone.model.inputModel;

import com.oluwasegunariyo.drone.model.enums.StateEnums;

public class UpdateDroneDto {
    public int droneId;
    public String serialNumber;
    public String model;
    public int weightLimit;
    public int batteryCapacity;
    public StateEnums state;
}

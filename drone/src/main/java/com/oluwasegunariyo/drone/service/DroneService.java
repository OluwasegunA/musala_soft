package com.oluwasegunariyo.drone.service;

import com.oluwasegunariyo.drone.model.droneModel.AuditLog;
import com.oluwasegunariyo.drone.model.droneModel.Drones;
import com.oluwasegunariyo.drone.model.enums.StateEnums;
import com.oluwasegunariyo.drone.model.inputModel.DroneDto;
import com.oluwasegunariyo.drone.model.inputModel.UpdateDroneDto;
import com.oluwasegunariyo.drone.model.medicationModel.Medication;
import com.oluwasegunariyo.drone.model.response.BaseResponse;
import com.oluwasegunariyo.drone.repository.AuditLogRepository;
import com.oluwasegunariyo.drone.repository.DroneRepository;
import com.oluwasegunariyo.drone.repository.MedicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DroneService {
    Logger logger = LoggerFactory.getLogger(DroneService.class.getName());

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    AuditLogRepository auditLogRepository;

    public BaseResponse registerDrone(DroneDto model) {
        BaseResponse response = new BaseResponse();
        try {
            Drones drone = new Drones();
            drone.setDate_created(new Date());
            drone.setLast_updated(new Date());
            drone.setState(StateEnums.IDLE);
            drone.setBatteryCapacity(model.batteryCapacity);
            drone.setModel(model.model);
            drone.setSerialNumber(model.serialNumber);
            drone.setWeightLimit(model.weightLimit);
            drone.setMedications(null);
            Drones savedDrone = droneRepository.save(drone);
            response.setData(savedDrone);
            response.setDescription("Drone registered successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", savedDrone.toString());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Drone not registered");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse deleteDrone(int droneId) {
        BaseResponse response = new BaseResponse();
        if (droneRepository.existsById(droneId)) {
            droneRepository.deleteById(droneId);
            response.setDescription("Drone deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("Drone not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse editDrone(UpdateDroneDto model) {
        BaseResponse response = new BaseResponse();
        if (droneRepository.existsById(model.droneId)) {
            Drones editDrone = new Drones();
            editDrone.setLast_updated(new Date());
            editDrone.setState(model.state);
            editDrone.setBatteryCapacity(model.batteryCapacity);
            editDrone.setModel(model.model);
            editDrone.setSerialNumber(model.serialNumber);
            editDrone.setWeightLimit(model.weightLimit);
            Drones updatedDrone = droneRepository.save(editDrone);
            response.setData(updatedDrone);
            response.setDescription("Drone updated successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedDrone.toString());
        } else {
            response.setDescription("Drone not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getAllDrones() {
        BaseResponse response = new BaseResponse();
        List<Drones> drones = droneRepository.findAll();
        if (!drones.isEmpty()) {
            response.setData(drones);
            response.setDescription("Drones found successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("No drone found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse loadDroneWithMedications(int droneId, List<Integer> medicationIds){
        BaseResponse response = new BaseResponse();
        new BatteryCapacityWorker().start();
        try {
            if(droneRepository.existsById(droneId)){
                Drones drone = droneRepository.getById(droneId);
                List<Medication> newMedications = new ArrayList<>();
                int weightCounter = 0;
                if(drone.getState() == StateEnums.IDLE){
                    for(int medicationId : medicationIds){
                        if(medicationRepository.existsById(medicationId)){
                            Medication medication = medicationRepository.getById(medicationId);
                            weightCounter += medication.getWeight();
                            while(weightCounter<drone.getWeightLimit()){
                                newMedications.add(medication);
                                drone.setState(StateEnums.LOADING);
                                Drones updatedDrone = droneRepository.save(drone);
                            }
                        }else{

                        }
                    }
                    if(drone.getBatteryCapacity() < 25){
                        response.setDescription("Drone battery capacity is below 25%");
                        response.setStatusCode(HttpServletResponse.SC_OK);
                    }else{
                        drone.setMedications(newMedications);
                        drone.setLast_updated(new Date());
                        drone.setState(StateEnums.LOADED);
                        Drones updatedDrone = droneRepository.save(drone);
                        response.setData(updatedDrone);
                        response.setDescription("Drone loaded successfully.");
                        response.setStatusCode(HttpServletResponse.SC_OK);
                        logger.info(response.getDescription() + ": {}", updatedDrone.toString());
                    }
                }
                else {
                    response.setDescription("Drone is not in Idle state");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                }
            }
            else{
                response.setDescription("Drone not found");
                response.setStatusCode(HttpServletResponse.SC_OK);
            }

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Drone not loaded");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse checkDroneBatteryLevel(int droneId){
        BaseResponse response = new BaseResponse();

        if (droneRepository.existsById(droneId)) {
            Drones drones = droneRepository.getById(droneId);
            response.setData(drones.getBatteryCapacity());
            response.setDescription("Drone's battery capacity");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", drones.getBatteryCapacity());
        } else {
            response.setDescription("No drone found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse checkLoadedMedication(int droneId){
        BaseResponse response = new BaseResponse();

        try{
            if(droneRepository.existsById(droneId)){
                Drones drone = droneRepository.getById(droneId);
                List<Medication> medications = drone.getMedications();
                if(!medications.isEmpty()){
                    response.setData(medications);
                    response.setDescription("Drone is loaded with medication items");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription() + ": {}", medications.toString());
                }
                else{
                    response.setDescription("No medication items loaded for Drone.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription());
                }
            }
            else{
                response.setDescription(String.format("Drone with the Id %2d found.", droneId));
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        }catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription(ex.getMessage());
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse checkDroneAvailablitiy(int droneId){
        BaseResponse response = new BaseResponse();

        try{
            if(droneRepository.existsById(droneId)){
                Drones drone = droneRepository.getById(droneId);
                switch(drone.getState()){
                    case IDLE:
                        response.setData(drone.getState());
                        response.setDescription("Drone available for loading");
                        response.setStatusCode(HttpServletResponse.SC_OK);
                        logger.info(response.getDescription() + ": {}", drone.getState().toString());
                        break;
                    case LOADING:
                        response.setData(drone.getState());
                        response.setDescription("Drone not available for loading");
                        response.setStatusCode(HttpServletResponse.SC_OK);
                        logger.info(response.getDescription() + ": {}", drone.getState().toString());
                        break;
                    case LOADED:
                        response.setData(drone.getState());
                        response.setDescription("Drone not available for loading. Fully Loaded");
                        response.setStatusCode(HttpServletResponse.SC_OK);
                        logger.info(response.getDescription() + ": {}", drone.getState().toString());
                        break;
                    case DELIVERING:
                        response.setData(drone.getState());
                        response.setDescription("Drone not available for loading, already delivering");
                        response.setStatusCode(HttpServletResponse.SC_OK);
                        logger.info(response.getDescription() + ": {}", drone.getState().toString());
                        break;
                    case DELIVERED:
                        response.setData(drone.getState());
                        response.setDescription("Drone not available for loading. Delivery medications");
                        response.setStatusCode(HttpServletResponse.SC_OK);
                        logger.info(response.getDescription() + ": {}", drone.getState().toString());
                        break;
                    case RETURNING:
                        response.setData(drone.getState());
                        response.setDescription("Drone will soon be available for loading");
                        response.setStatusCode(HttpServletResponse.SC_OK);
                        logger.info(response.getDescription() + ": {}", drone.getState().toString());
                        break;
                    default:
                        break;
                }
            }
            else{
                response.setDescription(String.format("Drone with the Id %2d found.", droneId));
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        }catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription(ex.getMessage());
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    class BatteryCapacityWorker extends Thread{
        public void run(){
            try{
                List<Drones> allDrones = droneRepository.findAll();
                AuditLog logs = new AuditLog();

                for (Drones drone : allDrones){
                    logs.setBatteryCapacity(drone.getBatteryCapacity());
                    logs.setDroneId(drone.getId());
                    logs.setDate_created(new Date());
                    AuditLog savedLog = auditLogRepository.save(logs);
                }
            }catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
                logger.error(ex.getMessage());
            }

        }
    }
}


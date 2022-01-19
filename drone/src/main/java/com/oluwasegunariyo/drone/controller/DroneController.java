package com.oluwasegunariyo.drone.controller;

import com.oluwasegunariyo.drone.model.droneModel.Drones;
import com.oluwasegunariyo.drone.model.inputModel.DroneDto;
import com.oluwasegunariyo.drone.model.inputModel.UpdateDroneDto;
import com.oluwasegunariyo.drone.model.response.BaseResponse;
import com.oluwasegunariyo.drone.service.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("api")
public class DroneController {
    Logger logger = LoggerFactory.getLogger(DroneController.class.getName());

    @Autowired
    DroneService droneService;

    //------------------------------Start--------------Drone----------------------//
    @DeleteMapping("/drone/delete/{id}")
    ResponseEntity<?> deleteDrone(@PathVariable int id) {
        BaseResponse response = droneService.deleteDrone(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/drone/edit")
    ResponseEntity<?> editDrone(@RequestBody UpdateDroneDto model) {
        BaseResponse response = droneService.editDrone(model);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/drone/register")
    ResponseEntity<?> createDrone(@RequestBody DroneDto drone) {
        BaseResponse response = droneService.registerDrone(drone);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/drone/getAllDrones")
    ResponseEntity<?> getAllDrones() {
        BaseResponse response = droneService.getAllDrones();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/drone/loadDrone/{droneId}")
    ResponseEntity<?> loadDrone(@PathVariable int droneId, @RequestBody List<Integer> medicationIds) {
        BaseResponse response = droneService.loadDroneWithMedications(droneId, medicationIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/drone/checkLoadedMedication/{droneId}")
    ResponseEntity<?> checkLoadedMedication(@PathVariable int droneId){
        BaseResponse response = droneService.checkLoadedMedication(droneId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/drone/checkDroneAvailablitiy/{droneId}")
    ResponseEntity<?> checkDroneAvailablitiy(@PathVariable int droneId){
        BaseResponse response = droneService.checkDroneAvailablitiy(droneId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/drone/checkDroneBatteryLevel/{droneId}")
    ResponseEntity<?> checkDroneBatteryLevel(@PathVariable int droneId){
        BaseResponse response = droneService.checkDroneBatteryLevel(droneId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //------------------------------End--------------Drone----------------------//

}

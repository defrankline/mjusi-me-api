package com.team.mjusi.controller;


import com.team.mjusi.entity.Training;
import com.team.mjusi.entity.TrainingAttendee;
import com.team.mjusi.entity.dto.TrainingDto;
import com.team.mjusi.service.TrainingAttendeeService;
import com.team.mjusi.service.TrainingService;
import com.team.mjusi.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;
    private final TrainingAttendeeService attendeeService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Training trainingDto) {
        ResponseEntity<?> validate = validate(trainingDto);
        if (validate != null) return validate;

        Training training = trainingService.save(trainingDto);
        ApiResponse<?> response = new ApiResponse<>("Training Created Successfully", training);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "0") int size,
                                    @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        if(size <= 0){
            List<Training> items = trainingService.findAll();
            ApiResponse<?> response = new ApiResponse<>("Trainings", items);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else{
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<Training> items = trainingService.findAll(pageable);
            ApiResponse<?> response = new ApiResponse<>("Role", items);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id) {
        Optional<Training> row = trainingService.findById(id);
        if (row.isPresent()) {
            Training training = row.get();
            ApiResponse<?> response = new ApiResponse<>("Training", training);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<?> response = new ApiResponse<>("Training not found", id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Training trainingDto) {
        Optional<Training> row = trainingService.findById(id);
        if (row.isPresent()) {
            ResponseEntity<?> validate = validate(trainingDto);
            if (validate != null) return validate;
            Training training = trainingService.save(trainingDto);
            ApiResponse<?> response = new ApiResponse<>("Training Updated Successfully", training);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            ApiResponse<?> response = new ApiResponse<>("Training Not Found", trainingDto);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Training> row = trainingService.findById(id);
        if (row.isPresent()) {
            List<TrainingAttendee> attendees = attendeeService.findAllByTrainingId(id);
            if(attendees.size() > 0){
                attendees.forEach(trainingAttendee -> {
                    attendeeService.delete(trainingAttendee.getId());
                });
            }
            trainingService.delete(id);
            ApiResponse<?> response = new ApiResponse<>("No Content", "Training Deleted Successfully");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            ApiResponse<?> response = new ApiResponse<>("Training Not Found", id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<?> validate(@RequestBody Training training) {
        if (training.getName().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>("First Name is required", training);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (training.getDate() == null) {
            ApiResponse<?> response = new ApiResponse<>("Date is required", training);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}

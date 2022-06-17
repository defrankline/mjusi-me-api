package com.team.mjusi.controller;

import com.team.mjusi.entity.TrainingAttendee;
import com.team.mjusi.entity.dto.MaleFemaleDto;
import com.team.mjusi.entity.dto.MaleFemaleWrapperDto;
import com.team.mjusi.entity.dto.UserTrainingCreateDto;
import com.team.mjusi.service.TrainingAttendeeService;
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

import javax.validation.Valid;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dataCollection")
@RequiredArgsConstructor
public class DataCollectionController {

    private final TrainingAttendeeService trainingAttendeeService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "20") int size,
                                     @RequestParam(value = "query", defaultValue = "20") String query) throws Exception {
        if (size <= 0) {
            List<TrainingAttendee> items = trainingAttendeeService.findAll();
            ApiResponse<?> response = new ApiResponse<>("Trainings", items);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<TrainingAttendee> items = trainingAttendeeService.findAll(pageable);
            ApiResponse<?> response = new ApiResponse<>("Trainings", items);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserTrainingCreateDto createDto) {
        if (createDto.getTrainings() == null) {
            ApiResponse<?> response = new ApiResponse<>("Training is required");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (createDto.getUser() == null) {
            ApiResponse<?> response = new ApiResponse<>("Student is required");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        List<TrainingAttendee> items = trainingAttendeeService.save(createDto);
        ApiResponse<?> response = new ApiResponse<>("Success", items);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        if (trainingAttendeeService.findById(id).isPresent()) {
            TrainingAttendee attendee = trainingAttendeeService.findById(id).get();
            ApiResponse<?> response = new ApiResponse<>("Success", attendee);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else{
            ApiResponse<?> response = new ApiResponse<>("Student Training Assignment Doest Not Exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        if (trainingAttendeeService.findById(id).isPresent()) {
            trainingAttendeeService.delete(id);
            ApiResponse<?> response = new ApiResponse<>("Success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else{
            ApiResponse<?> response = new ApiResponse<>("User Role Assignment Doest Not Exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/report")
    public ResponseEntity<?> report() {
        MaleFemaleWrapperDto dto = trainingAttendeeService.reportByGender();
        ApiResponse<?> response = new ApiResponse<>("Success", dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
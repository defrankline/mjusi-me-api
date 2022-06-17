package com.team.mjusi.service.impl;

import com.team.mjusi.entity.Training;
import com.team.mjusi.entity.TrainingAttendee;
import com.team.mjusi.entity.User;
import com.team.mjusi.entity.dto.MaleFemaleDto;
import com.team.mjusi.entity.dto.MaleFemaleWrapperDto;
import com.team.mjusi.entity.dto.UserTrainingCreateDto;
import com.team.mjusi.repository.TrainingAttendeeRepository;
import com.team.mjusi.service.TrainingAttendeeService;
import com.team.mjusi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingAttendeeServiceImp implements TrainingAttendeeService {
    private final TrainingAttendeeRepository repository;
    private final UserService userService;
    private final EntityManager entityManager;

    @Override
    public List<TrainingAttendee> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Page<TrainingAttendee> findAllByUserId(Long userId, Pageable pageable) {
        return repository.findAllByUserId(userId, pageable);
    }

    @Override
    public List<TrainingAttendee> findAllByTrainingId(Long trainingId) {
        return repository.findAllByTrainingId(trainingId);
    }

    @Override
    public Page<TrainingAttendee> findAllByTrainingId(Long trainingId, Pageable pageable) {
        return repository.findAllByTrainingId(trainingId, pageable);
    }

    @Override
    public Optional<TrainingAttendee> findFirstByUserIdAndTrainingId(Long userId, Long trainingId) {
        return repository.findFirstByUserIdAndTrainingId(userId, trainingId);
    }

    @Override
    public List<TrainingAttendee> save(UserTrainingCreateDto createDto) {
        Optional<User> userOptional = userService.findById(createDto.getUser().getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<TrainingAttendee> saved = new ArrayList<>();
            for (Training training : createDto.getTrainings()) {
                TrainingAttendee row = new TrainingAttendee();
                Optional<TrainingAttendee> optional = findFirstByUserIdAndTrainingId(user.getId(), training.getId());
                if (optional.isPresent()) {
                    row = optional.get();
                }
                row.setTraining(training);
                row.setUser(user);
                TrainingAttendee item = this.save(row);
                saved.add(item);
            }
            return saved;
        } else {
            return null;
        }
    }

    @Override
    public TrainingAttendee save(TrainingAttendee trainingAttendee) {
        return repository.saveAndFlush(trainingAttendee);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<TrainingAttendee> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<TrainingAttendee> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<TrainingAttendee> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public MaleFemaleDto reportAttendeeByGender() {
        Query q = entityManager.createNativeQuery("select sum(i.male) as male, sum(i.female) as female from (select t.id, case when t.male is null then 0 else t.male end as male, case when t.female is null then 0 else t.female end as female from (select u.id, case when u.gender = 0 then 1 end as male, case when u.gender = 1 then 1 end as female from trainings t join training_attendees ta on t.id = ta.training_id join users u on u.id = ta.user_id) t) i");
        Object[] data = (Object[]) q.getSingleResult();
        MaleFemaleDto dto = new MaleFemaleDto();
        dto.setMale((BigInteger) data[0]);
        dto.setFemale((BigInteger) data[1]);
        return dto;
    }

    @Override
    public MaleFemaleDto reportStudentByGender() {
        Query q = entityManager.createNativeQuery("select sum(i.male) as male,sum(i.female) as female from (select case when i.male is null then 0 else i.male end as male, case when i.female is null then 0 else i.female end as female from (select case when u.gender = 0 then 1 end as male, case when u.gender = 1 then 1 end as female from users u) i) i");
        Object[] data = (Object[]) q.getSingleResult();
        MaleFemaleDto dto = new MaleFemaleDto();
        dto.setMale((BigInteger) data[0]);
        dto.setFemale((BigInteger) data[1]);
        return dto;
    }

    @Override
    public MaleFemaleWrapperDto reportByGender() {
        MaleFemaleDto attendee = reportAttendeeByGender();
        MaleFemaleDto student = reportStudentByGender();

        MaleFemaleWrapperDto wrapperDto = new MaleFemaleWrapperDto();
        wrapperDto.setAttendee(attendee);
        wrapperDto.setStudent(student);

        return wrapperDto;
    }
}

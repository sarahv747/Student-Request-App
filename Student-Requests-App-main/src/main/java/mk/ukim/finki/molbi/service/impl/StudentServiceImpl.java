package mk.ukim.finki.molbi.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.molbi.model.base.Student;
import mk.ukim.finki.molbi.model.exceptions.StudentNotFoundException;
import mk.ukim.finki.molbi.repository.StudentRepository;
import mk.ukim.finki.molbi.service.StudentService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;


    @Override
    public Student findByIndex(String index) {
        return studentRepository.findById(index).orElseThrow(() -> new StudentNotFoundException(index));
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }
}

package mk.ukim.finki.molbi.service;

import mk.ukim.finki.molbi.model.base.Student;

public interface StudentService {

    Student findByIndex(String index);

    void save(Student student);
}

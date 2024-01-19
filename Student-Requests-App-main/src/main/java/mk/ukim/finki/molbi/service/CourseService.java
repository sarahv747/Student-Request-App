package mk.ukim.finki.molbi.service;

import mk.ukim.finki.molbi.model.base.Course;
import mk.ukim.finki.molbi.model.base.Semester;

import java.util.List;

public interface CourseService {
    List<Course> listBySemester(Semester semester);

    Course findById(String id);

    void save(Course course);
}

package mk.ukim.finki.molbi.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.molbi.model.base.Course;
import mk.ukim.finki.molbi.model.base.Semester;
import mk.ukim.finki.molbi.model.exceptions.CourseNotFoundException;
import mk.ukim.finki.molbi.repository.CourseRepository;
import mk.ukim.finki.molbi.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public List<Course> listBySemester(Semester semester) {
        return courseRepository.findBySemester(semester);
    }

    @Override
    public Course findById(String id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }
}

package mk.ukim.finki.molbi.service;

import mk.ukim.finki.molbi.model.base.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> listAll();

    Subject findById(String id);
}

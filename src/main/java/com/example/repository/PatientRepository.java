package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long>{

}

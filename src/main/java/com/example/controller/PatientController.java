package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.model.Patient;
import com.example.repository.PatientRepository;
import com.example.storage.service.FileStorageService;

@RestController
@CrossOrigin(origins = "*")
public class PatientController {
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	FileStorageService fileStorageService;
	
	
	
	
	@PostMapping(value = "/patient/save")
	public ResponseEntity<?> save(@RequestBody Patient entity){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Patient patient = patientRepository.save(entity);
			map.put("message", "Data save successfully");
			map.put("data", patient);
			map.put("statusCode", 200);
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "Data saved failed");
			map.put("data", null);
			map.put("statusCode", 400);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
		
	}
	
	
	
	
	//to save patient with file.
	
		@PostMapping("/savepatient_withfile")
		public ResponseEntity<Map> saveFormData(@ModelAttribute Patient patient,
				@RequestParam("file") MultipartFile file) {
			Map<String, Object> map = new HashMap<String, Object>();
			try {

				String fileName = fileStorageService.storeFile(file);
				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
						.path(fileName).toUriString();
				patient.setImages(fileName);
				patient.setImageUri(fileDownloadUri);
				patient = patientRepository.save(patient);
				map.put("status", "Success");
				map.put("data", patient);
				map.put("message", "Data saved successfully");
				map.put("statusCode", 200);
				return ResponseEntity.ok(map);
			} catch (Exception e) {
				map.put("status", "failed");
				map.put("data", null);
				map.put("message", e.getLocalizedMessage());
				return ResponseEntity.status(500).body(map);
			}

		}
		
		
		
		
		
		//to get all patients
		
		@GetMapping(value = "/patient/getAll")
		public ResponseEntity<?> getAll() {
			Map<String, Object> map = new HashMap<>();
			try {
				List<Patient> patient =(List<Patient>) patientRepository.findAll();
				map.put("message", "Data get successfully");
				map.put("data", patient);
				map.put("statusCode", 200);
				return ResponseEntity.ok(map);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message", "Data fetch failed");
				map.put("data", null);
				map.put("statusCode", 400);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
			}
		}
		
		
		
		
		@GetMapping(value = "/patient/findById/{id}")
		public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
			Map<String, Object> map = new HashMap<>();
			try {
				Patient patient = patientRepository.findById(id).get();
				map.put("message", "Data get successfully");
				map.put("data", patient);
				map.put("statusCode", 200);
				return ResponseEntity.ok(map);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message", "Data fetch failed");
				map.put("data", null);
				map.put("statusCode", 400);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
			}
		}
		
		
		
		//to update patient with file
		
		@PostMapping(value = "/patient/update")
		public ResponseEntity<Map> updateFormData(@ModelAttribute Patient patient,
				@RequestParam("file") MultipartFile file) {
			Map<String, Object> map = new HashMap<String, Object>();
			try {

				String fileName = fileStorageService.storeFile(file);
				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
						.path(fileName).toUriString();
				patient.setImages(fileName);
				patient.setImageUri(fileDownloadUri);
				
				
				patient = patientRepository.save(patient);
				map.put("status", "Success");
				map.put("data", patient);
				map.put("message", "Data update successfully");
				map.put("statusCode", 200);
				return ResponseEntity.ok(map);
			} catch (Exception e) {
				map.put("status", "failed");
				map.put("data", null);
				map.put("message", e.getLocalizedMessage());
				return ResponseEntity.status(500).body(map);
			}
		}
		
		
		
		
		//to delete Patient by id.
		
		@GetMapping(value = "/patient/delete/{id}")
		public ResponseEntity<?> deleteData(@PathVariable(value = "id") Long id) {
			Map<String, Object> map = new HashMap<>();
			Patient patient = patientRepository.findById(id).get();
			try {
				patientRepository.delete(patient);
				map.put("message", "Data deleted successfully");
				map.put("data", patient);
				map.put("statusCode", 200);
				return ResponseEntity.ok(map);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message", "Data deletation failed");
				map.put("data", null);
				map.put("statusCode", 400);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
			}
		}

	
	
	
	
	
	
	
	
	
	
	
	
	

}

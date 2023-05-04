package com.innovator.resort.controllers;

import com.innovator.resort.Exception.ResourceNotFoundException;
import com.innovator.resort.models.Admin;
import com.innovator.resort.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class http://localhost:9092/resort
 */
@RestController
@RequestMapping("/resort")
public class AdminController {

	@Autowired
	AdminRepository adminRepository;

	@GetMapping("/admins")
	public Iterable<Admin> getAllUsers() {
		return adminRepository.findAll();
	}

	@PostMapping("/saveAdmin")
	public Admin createUser(@RequestBody Admin admin) {
		adminRepository.save(admin);
		return admin;
	}

	@GetMapping("/admins/{id}")
	public Admin getUserById(@PathVariable(value = "id") Long userId) {
		return adminRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
	}

	@PutMapping("/updateAdmins/{id}")
	public Admin updateUser(@PathVariable(value = "id") Long adminId, @RequestBody Admin adminDetails) {

		Admin admin = adminRepository.findById(adminId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", adminId));

		admin.setF_name(adminDetails.getF_name());
		admin.setL_name(adminDetails.getL_name());
		admin.setSex(adminDetails.getSex());
		admin.setAge(adminDetails.getAge());
		admin.setCity(adminDetails.getCity());
		Admin updatedAdmin = adminRepository.save(admin);
		return updatedAdmin;
	}

	@DeleteMapping("/admins/{id}")
	public ResponseEntity<Admin> deleteUser(@PathVariable(value = "id") Long adminId) {
		Admin admin = adminRepository.findById(adminId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", adminId));

		adminRepository.delete(admin);

		return ResponseEntity.ok().build();
	}
}

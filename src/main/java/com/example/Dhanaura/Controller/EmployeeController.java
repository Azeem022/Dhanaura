package com.example.Dhanaura.Controller;


import com.example.Dhanaura.Entity.EmployeeEntity;
import com.example.Dhanaura.Repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping
    public String createNewEmployee (@RequestBody EmployeeEntity employee){
        employeeRepository.save(employee);
        return "Employee created in database";
    }

    @GetMapping
    public ResponseEntity<List<EmployeeEntity>> getAllEmployee(){
        List<EmployeeEntity> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employeeList::add);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeEntity> emp = employeeRepository.findById(id);
        return emp.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployeeById(@PathVariable Long id, @RequestBody EmployeeEntity employee) {
        Optional<EmployeeEntity> existingEmp = employeeRepository.findById(id);

        if (existingEmp.isPresent()) {
            EmployeeEntity empToUpdate = existingEmp.get();
            empToUpdate.setName(employee.getName());
            empToUpdate.setEmail(employee.getEmail());
            empToUpdate.setPassword(employee.getPassword());

            employeeRepository.save(empToUpdate);
            return ResponseEntity.ok("Employee updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public String deleteEmployeeById (@PathVariable Long id){
        employeeRepository.deleteById(id);
        return "Employee delete successfully";
    }

    @DeleteMapping("/all")
    public String deleteAllEmployee(){
        employeeRepository.deleteAll();
        return "All Employee Delete Successfully";
    }

}


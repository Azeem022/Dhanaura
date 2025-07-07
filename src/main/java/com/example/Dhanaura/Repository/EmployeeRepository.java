package com.example.Dhanaura.Repository;

import com.example.Dhanaura.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long > {
}

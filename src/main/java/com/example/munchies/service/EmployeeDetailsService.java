package com.example.munchies.service;

import com.example.munchies.model.entity.Employee;
import com.example.munchies.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public EmployeeDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findEmployeeByEmployeeEmail(email);
        if(employee.isEmpty())
            throw new UsernameNotFoundException("Employee not exist");
        return new MyEmployeePrincipal(employee.get());
    }
}

package com.tw.apistackbase.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeResources {
    @Autowired
    private Database db;

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return ResponseEntity.ok(db.getEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        for(Employee e : db.getEmployees()) {
            if(e.getId() == id) {
                return ResponseEntity.ok(e);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public ResponseEntity<List<Employee>> getEmployeesByPageAndPageSize(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        int start = (page - 1) * pageSize;
        int end = page * pageSize;
        if(db.getEmployees().size() > 0) {
            if(db.getEmployees().size() <= start) {
                start = db.getEmployees().size()-1;
            }
            if(db.getEmployees().size() <= end) {
                end = db.getEmployees().size();
            }
            return ResponseEntity.ok(db.getEmployees().subList(start, end));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/gender")
    public ResponseEntity<List<Employee>> getEmployeesByGender(@RequestParam("gender") String gender) {
        List<Employee> result = new ArrayList<Employee>();
        db.getEmployees().forEach((e) -> {
            if(gender.equals(e.getGender())) {
                result.add(e);
            }
        });
        if(result.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<List<Employee>> addEmployee(@RequestBody Employee employee){
        db.getEmployees().add(employee);
        return ResponseEntity.ok(db.getEmployees());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable int id){
        for(Employee e : db.getEmployees()) {
            if(e.getId() == id) {
                e.setAge(employee.getAge());
                e.setGender(employee.getGender());
                e.setName(employee.getName());
                e.setSalary(employee.getSalary());
                return ResponseEntity.ok(e);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Employee>> deleteEmployee(@PathVariable int id){
        Iterator<Employee> iterator = db.getEmployees().iterator();
        while (iterator.hasNext()) {
            if(iterator.next().getId() == id) {
                iterator.remove();
                return ResponseEntity.ok(db.getEmployees());
            }
        }
        return ResponseEntity.notFound().build();
    }
}

package com.cristeabianca.companyms.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable  Long id){
        return new ResponseEntity<>(companyService.getCompanyById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody  Company company){
        if(companyService.createCompany(company)){
            return new ResponseEntity<>("Company created.",HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Company could not be created.",HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable  Long id, @RequestBody Company company){
        if(companyService.updateCompany(id,company)){
            return new ResponseEntity<>("Company updated.",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Company could not be updated.",HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long id){
        if(companyService.deleteCompanyById(id)){
            return new ResponseEntity<>("Company deleted.",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Company could not be deleted.",HttpStatus.NOT_FOUND);
        }
    }

}
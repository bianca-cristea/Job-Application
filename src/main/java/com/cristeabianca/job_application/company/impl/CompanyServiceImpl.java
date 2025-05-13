package com.cristeabianca.job_application.company.impl;

import com.cristeabianca.job_application.company.Company;
import com.cristeabianca.job_application.company.CompanyRepository;
import com.cristeabianca.job_application.company.CompanyService;
import com.cristeabianca.job_application.job.JobRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
    @Override
    public Company getCompanyById(Long id){
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public boolean createCompany(Company company){
        try{
            companyRepository.save(company);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateCompany(Long id, Company updatedCompany) {

        Optional<Company> optionalCompany = companyRepository.findById(id);
        if(optionalCompany.isPresent()){
          Company company = optionalCompany.get();
          company.setName(updatedCompany.getName());
          company.setDescription(updatedCompany.getDescription());
          company.setJobs(updatedCompany.getJobs());
          companyRepository.save(company);
          return true;
        }
        return false;
    }

    @Override
    public boolean deleteCompanyById(Long id) {
        try{companyRepository.deleteById(id);
            return true;}
        catch (Exception e){
            return false;
        }

    }
}

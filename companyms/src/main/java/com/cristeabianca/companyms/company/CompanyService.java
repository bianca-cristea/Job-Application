package com.cristeabianca.companyms.company;

import java.util.List;


public interface CompanyService {

     List<Company> getAllCompanies();
     Company getCompanyById(Long id);
     boolean createCompany(Company company);
     boolean updateCompany(Long id,Company updatedCompany);
     boolean deleteCompanyById(Long id);
}

package com.cristeabianca.companyms.company;

import java.util.List;

public interface CompanyService {
     List<Company> getAllCompanies();

     Company getCompanyById(Long id);

     Company addCompany(Company company);

     Company updateCompany(Long id, Company company);

     boolean deleteCompany(Long id);
}

package com.cristeabianca.jobms.application;

import com.cristeabianca.jobms.application.ApplicationDTO;
import com.cristeabianca.jobms.user.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ApplicationClient {

    private final RestTemplate restTemplate;
    private final String applicationServiceUrl = "http://localhost:8082/applications/";
    private final String userServiceUrl = "http://localhost:8081/users/";  // adaugă aici URL corect

    public ApplicationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Long getUserIdByUsername(String username) {
        try {
            UserDTO user = restTemplate.getForObject(userServiceUrl + username, UserDTO.class);
            if (user != null) {
                return user.getId();
            }
        } catch (Exception e) {
            System.out.println("Error fetching user by username: " + e.getMessage());
        }
        return null;
    }

    public ApplicationDTO getApplicationById(Long applicationId) {
        try {
            return restTemplate.getForObject(applicationServiceUrl + applicationId, ApplicationDTO.class);
        } catch (Exception e) {
            System.out.println("Failed to fetch application with id " + applicationId + ": " + e.getMessage());
            return null;
        }
    }
    public List<ApplicationDTO> getApplicationsByUsername(String username) {
        try {
            ResponseEntity<ApplicationDTO[]> response = restTemplate.getForEntity(
                    "http://localhost:8082/applications/user/" + username, ApplicationDTO[].class);
            ApplicationDTO[] applications = response.getBody();
            return applications != null ? Arrays.asList(applications) : Collections.emptyList();
        } catch (Exception e) {
            System.out.println("Error fetching applications for user " + username + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

}

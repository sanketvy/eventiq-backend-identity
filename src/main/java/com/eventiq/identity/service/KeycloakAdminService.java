package com.eventiq.identity.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class KeycloakAdminService {

    private final String keycloakBaseUrl = "http://localhost:8001";
    private final String realm = "eventiq";
    private final String adminUsername = "admin";
    private final String adminPassword = "admin";
    private final String clientId = "admin-cli";

    private final RestTemplate restTemplate = new RestTemplate();

    // ✅ Assigns a realm role to a user
    public void assignRealmRoleToUser(String email, String roleName) {
        String accessToken = getAdminAccessToken();

        String userId = getUserIdByEmail(email, accessToken);
        Map<String, Object> role = getRealmRoleByName(roleName, accessToken);

        String url = keycloakBaseUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(List.of(role), headers);
        restTemplate.postForEntity(url, request, Void.class);
    }

    // ❌ Removes a realm role from a user
    public void removeRealmRoleFromUser(String email, String roleName) {
        String accessToken = getAdminAccessToken();

        String userId = getUserIdByEmail(email, accessToken);
        Map<String, Object> role = getRealmRoleByName(roleName, accessToken);

        String url = keycloakBaseUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(List.of(role), headers);
        restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
    }

    // 🔐 Get Keycloak admin access token
    private String getAdminAccessToken() {
        String tokenUrl = keycloakBaseUrl + "/realms/master/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=password&client_id=" + clientId +
                "&username=" + adminUsername +
                "&password=" + adminPassword;

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        return (String) response.getBody().get("access_token");
    }

    // 🔍 Get user ID by email
    private String getUserIdByEmail(String email, String token) {
        String url = keycloakBaseUrl + "/admin/realms/" + realm + "/users?email=" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        List<Map<String, Object>> users = response.getBody();

        if (users == null || users.isEmpty()) {
            throw new RuntimeException("User with email " + email + " not found.");
        }

        return (String) users.get(0).get("id");
    }

    // 🔍 Get role details by name
    private Map<String, Object> getRealmRoleByName(String roleName, String token) {
        String url = keycloakBaseUrl + "/admin/realms/" + realm + "/roles/" + roleName;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return response.getBody();
    }
}

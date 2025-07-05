package com.eventiq.identity.service.impl;

import com.eventiq.identity.dto.UserProfile;
import com.eventiq.identity.model.User;
import com.eventiq.identity.repository.UserRepository;
import com.eventiq.identity.service.IdentityService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdentityServiceImpl implements IdentityService {

    UserRepository userRepository;

    public IdentityServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserProfile getUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(value -> UserProfile.builder()
                .firstName(value.getFirstName())
                .lastName(value.getLastName())
                .role(value.getRole())
                .email(value.getEmail())
                .company(value.getCompany())
                .phoneNo(value.getPhoneNo())
                .build()).orElseThrow(
               ()-> new RuntimeException("User Not Found")
        );
    }

    @Override
    public void updateUser(String userId, UserProfile userProfile) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            user.get().setCompany(userProfile.getCompany());
            user.get().setPhoneNo(userProfile.getPhoneNo());
            user.get().setRole(userProfile.getRole());
            user.get().setFirstName(userProfile.getFirstName());
            user.get().setLastName(userProfile.getLastName());
            userRepository.save(user.get());
        }
    }

    @Override
    public UserProfile getCurrentUser(Jwt jwt) {
        Optional<User> user = userRepository.findById(jwt.getClaimAsString("sub"));
        if(user.isPresent()){
            return UserProfile.builder()
                    .firstName(user.get().getFirstName())
                    .lastName(user.get().getLastName())
                    .email(user.get().getEmail())
                    .build();
        } else {
            User newUser = new User();
            newUser.setFirstName(jwt.getClaimAsString("given_name"));
            newUser.setLastName(jwt.getClaimAsString("family_name"));
            newUser.setEmail(jwt.getClaimAsString("email"));
            newUser.setId(jwt.getClaimAsString("sub"));
            userRepository.save(newUser);

            return UserProfile.builder()
                    .firstName(jwt.getClaimAsString("given_name"))
                    .lastName(jwt.getClaimAsString("family_name"))
                    .email(jwt.getClaimAsString("email"))
                    .build();

        }
    }
}

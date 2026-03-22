package org.example.services;

import java.util.HashSet;
import java.util.UUID;
import java.util.Objects;
import org.example.entities.UserInfo;
import org.example.eventProducer.UserInfoProducer;
import org.example.models.UserInfoDTO;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
@AllArgsConstructor
@Data
public class UserDetailServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordencoder;

    private final UserInfoProducer userInfoProducer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("could not found user");
        }
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExist(UserInfoDTO userInfoDto) {
        return userRepository.findByUsername(userInfoDto.getUsername());
    }

    public boolean signupUser(UserInfoDTO userinfodto) {
        userinfodto.setPassword(passwordencoder.encode(userinfodto.getPassword()));
        if (Objects.nonNull(checkIfUserAlreadyExist(userinfodto))) {
            return false;
        }
        String userId = UUID.randomUUID().toString();
        userRepository
                .save(new UserInfo(userId, userinfodto.getUsername(), userinfodto.getPassword(), new HashSet<>()));
        // userInfoProducer.sendEventToKafka(userinfodto);
        return true;
    }

    // define function to check if userEmail, password is correct
    // password-min 8,mix
    // make util-
}
// A Bean = object created and managed by Spring container
// Difference- Bean is the actual object managed by Spring, while @Component is
// a way to tell Spring to create that Bean automatically.
// Component- "Create an object (bean) of this class and manage it
// automatically"
// Without Component- UserDetailServiceImp service = new UserDetailServiceImp();
// // ❌ manual
// Passwordencoder hash=password
// implement vs extends
// extends- inheritence
// implements- can do relationship -> used when class implements interface like
// we override methods
// Spring calls → loadUserByUsername()
// ↓
// Fetch user from DB
// ↓
// Convert to CustomUserDetails
// ↓
// Spring checks password
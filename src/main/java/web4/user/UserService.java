package web4.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web4.exceptions.ConflictException;
import web4.exceptions.UnprocessableEntityException;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не найден"));
    }

    private void validateRequest(UserRequest request) {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new ConflictException("Пользователь с таким именем уже зарегистрирован");
        }
        if (request.getUsername().length() < 3 && request.getPassword().length() < 3) {
            throw new UnprocessableEntityException("Пароль и логин должны содержать хотя бы 3 символа");
        }
    }

    public User registerUser(UserRequest request) {
        validateRequest(request);
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        return user;
    }

    public UserDetails authUser(UserRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return loadUserByUsername(request.getUsername());
    }
}

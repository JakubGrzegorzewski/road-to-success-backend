package grzegorzewski.roadtosuccesbackend.Service;

import grzegorzewski.roadtosuccesbackend.Model.AppUser;
import grzegorzewski.roadtosuccesbackend.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    @Autowired
    UserRepository userRepository;

    public AppUser getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
    }

    public AppUser save(AppUser user) {
        if (user.getId() == null) {
            return userRepository.save(user);
        } else {
            if (userRepository.existsById(user.getId())) {
                throw new IllegalArgumentException("User already exists with id " + user.getId());
            } else {
                return userRepository.save(user);
            }
        }
    }

    public AppUser update(AppUser user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null for update operation");
        }
        if (!userRepository.existsById(user.getId())) {
            throw new EntityNotFoundException("User not found with id " + user.getId());
        }
        return userRepository.save(user);
    }

    public void delete(long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User not found with id " + id);
        }
    }
}
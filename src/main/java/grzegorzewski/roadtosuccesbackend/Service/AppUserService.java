package grzegorzewski.roadtosuccesbackend.Service;

import grzegorzewski.roadtosuccesbackend.Dto.AppUserDto;
import grzegorzewski.roadtosuccesbackend.Dto.BasicAppUserDto;
import grzegorzewski.roadtosuccesbackend.Mapper.AppUserMapper;
import grzegorzewski.roadtosuccesbackend.Model.AppUser;
import grzegorzewski.roadtosuccesbackend.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppUserMapper userMapper;

    public AppUserDto getById(long id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        return userMapper.toDto(user);
    }

    public BasicAppUserDto getBasicById(long id){
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        return userMapper.toBasicDto(user);
    }

    public List<BasicAppUserDto> getAllAppUserBasic(){
        List<AppUser> userDtos = (List<AppUser>) userRepository.findAll();
        return userMapper.toBasicDtoList(userDtos);
    }

    public AppUserDto save(AppUserDto userDto) {
        if (userDto.getId() != null && userRepository.existsById(userDto.getId())) {
            throw new IllegalArgumentException("User already exists with id " + userDto.getId());
        }

        AppUser user = userMapper.toEntity(userDto);
        AppUser savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public AppUserDto update(AppUserDto userDto) {
        if (userDto.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null for update operation");
        }
        if (!userRepository.existsById(userDto.getId())) {
            throw new EntityNotFoundException("User not found with id " + userDto.getId());
        }

        AppUser user = userMapper.toEntity(userDto);
        AppUser updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public void delete(long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User not found with id " + id);
        }
    }
}
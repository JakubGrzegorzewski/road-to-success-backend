package grzegorzewski.roadtosuccesbackend.Controller;

import grzegorzewski.roadtosuccesbackend.Dto.AppUserDto;
import grzegorzewski.roadtosuccesbackend.Service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RestController
class AppUserController {
    @Autowired
    private AppUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AppUserDto> save(@RequestBody AppUserDto user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping
    public ResponseEntity<AppUserDto> update(@RequestBody AppUserDto user) {
        return ResponseEntity.ok(userService.update(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
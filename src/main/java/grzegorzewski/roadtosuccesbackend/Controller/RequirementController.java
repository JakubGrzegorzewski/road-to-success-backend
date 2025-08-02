package grzegorzewski.roadtosuccesbackend.Controller;

import grzegorzewski.roadtosuccesbackend.Dto.RequirementDto;
import grzegorzewski.roadtosuccesbackend.Service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/requirement")
@RestController
class RequirementController {
    @Autowired
    private RequirementService requirementService;

    @GetMapping("/{id}")
    public ResponseEntity<RequirementDto> findById(@PathVariable long id) {
        return ResponseEntity.ok(requirementService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RequirementDto> save(@RequestBody RequirementDto requirement) {
        return ResponseEntity.ok(requirementService.save(requirement));
    }

    @PutMapping
    public ResponseEntity<RequirementDto> update(@RequestBody RequirementDto requirement) {
        return ResponseEntity.ok(requirementService.update(requirement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        requirementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
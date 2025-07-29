package grzegorzewski.roadtosuccesbackend.Controller;

import grzegorzewski.roadtosuccesbackend.Model.Requirements;
import grzegorzewski.roadtosuccesbackend.Service.RequirementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/requirements")
@RestController
class RequirementsController {
    @Autowired
    private RequirementsService requirementsService;

    @GetMapping( "/{id}")
    public ResponseEntity<Requirements> findById(@PathVariable long id) {
        return ResponseEntity.ok(requirementsService.findById(id)) ;
    }

    @PostMapping
    public ResponseEntity<Requirements> save(@RequestBody Requirements requirements) {
        return ResponseEntity.ok(requirementsService.save(requirements));
    }

    @PutMapping
    public ResponseEntity<Requirements> update(@RequestBody Requirements requirements) {
        return ResponseEntity.ok(requirementsService.update(requirements));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        requirementsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package grzegorzewski.roadtosuccesbackend.Service;

import grzegorzewski.roadtosuccesbackend.Model.Requirements;
import grzegorzewski.roadtosuccesbackend.Repository.RequirementsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequirementsService {
    @Autowired
    private RequirementsRepository requirementsRepository;

    public Requirements findById(long id) {
        return requirementsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Requirements not found with id " + id));
    }

    public Requirements save(Requirements requirements) {
        if (!requirementsRepository.existsById(requirements.getId())) {
            return requirementsRepository.save(requirements);
        } else {
            throw new EntityNotFoundException("Requirements already exists with id " + requirements.getId());
        }
    }

    public Requirements update(Requirements requirements) {
        return requirementsRepository.save(requirements);
    }

    public void delete(long id) {
        if (requirementsRepository.existsById(id)) {
            requirementsRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Requirements not found with id " + id);
        }
    }

}

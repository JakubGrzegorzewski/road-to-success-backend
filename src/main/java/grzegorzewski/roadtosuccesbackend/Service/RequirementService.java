package grzegorzewski.roadtosuccesbackend.Service;

import grzegorzewski.roadtosuccesbackend.Dto.RequirementDto;
import grzegorzewski.roadtosuccesbackend.Mapper.RequirementMapper;
import grzegorzewski.roadtosuccesbackend.Model.Rank;
import grzegorzewski.roadtosuccesbackend.Model.Requirement;
import grzegorzewski.roadtosuccesbackend.Repository.RankRepository;
import grzegorzewski.roadtosuccesbackend.Repository.RequirementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequirementService {
    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private RequirementMapper requirementMapper;

    public RequirementDto findById(long id) {
        Requirement requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Requirement not found with id " + id));
        return requirementMapper.toDto(requirement);
    }

    public RequirementDto save(RequirementDto requirementDto) {
        if (requirementDto.getId() != null && requirementRepository.existsById(requirementDto.getId())) {
            throw new IllegalArgumentException("Requirement already exists with id " + requirementDto.getId());
        }

        Requirement requirement = requirementMapper.toEntity(requirementDto);

        // Set relationship
        if (requirementDto.getRankId() != null) {
            Rank rank = rankRepository.findById(requirementDto.getRankId())
                    .orElseThrow(() -> new EntityNotFoundException("Rank not found with id " + requirementDto.getRankId()));
            requirement.setRank(rank);
        }

        Requirement savedRequirement = requirementRepository.save(requirement);
        return requirementMapper.toDto(savedRequirement);
    }

    public RequirementDto update(RequirementDto requirementDto) {
        if (requirementDto.getId() == null) {
            throw new IllegalArgumentException("Requirement ID cannot be null for update operation");
        }
        if (!requirementRepository.existsById(requirementDto.getId())) {
            throw new EntityNotFoundException("Requirement not found with id " + requirementDto.getId());
        }

        Requirement existingRequirement = requirementRepository.findById(requirementDto.getId()).get();
        existingRequirement.setNumber(requirementDto.getNumber());
        existingRequirement.setContent(requirementDto.getContent());

        Requirement updatedRequirement = requirementRepository.save(existingRequirement);
        return requirementMapper.toDto(updatedRequirement);
    }

    public void delete(long id) {
        if (requirementRepository.existsById(id)) {
            requirementRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Requirement not found with id " + id);
        }
    }
}
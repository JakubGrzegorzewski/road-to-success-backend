package grzegorzewski.roadtosuccesbackend.Repository;

import grzegorzewski.roadtosuccesbackend.Model.Requirement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequirementRepository extends CrudRepository<Requirement, Long> {
}
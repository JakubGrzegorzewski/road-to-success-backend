package grzegorzewski.roadtosuccesbackend.Repository;

import grzegorzewski.roadtosuccesbackend.Model.Requirements;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequirementsRepository extends CrudRepository<Requirements, Long> {
}
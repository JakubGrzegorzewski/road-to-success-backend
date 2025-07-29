package grzegorzewski.roadtosuccesbackend.Repository;

import grzegorzewski.roadtosuccesbackend.Model.RankInProgress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankInProgressRepository extends CrudRepository<RankInProgress, Long> {
    Long id(long id);
}
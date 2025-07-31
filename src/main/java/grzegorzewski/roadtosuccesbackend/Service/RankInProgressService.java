package grzegorzewski.roadtosuccesbackend.Service;

import grzegorzewski.roadtosuccesbackend.Model.RankInProgress;
import grzegorzewski.roadtosuccesbackend.Model.AppUser;
import grzegorzewski.roadtosuccesbackend.Repository.RankInProgressRepository;
import grzegorzewski.roadtosuccesbackend.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankInProgressService {

    @Autowired
    private RankInProgressRepository rankInProgressRepository;

    @Autowired
    private UserRepository userRepository;

    public RankInProgress findById(long id) {
        return rankInProgressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RankInProgress not found with id " + id));
    }

    public List<RankInProgress> findAllRanksInProgressForUser(long userId) {
         AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        return user.getRanksInProgress();
    }

    public RankInProgress save(RankInProgress rankInProgress) {
        if (rankInProgress.getId() == null) {
            return rankInProgressRepository.save(rankInProgress);
        } else if (!rankInProgressRepository.existsById(rankInProgress.getId())) {
            return rankInProgressRepository.save(rankInProgress);
        } else {
            throw new IllegalArgumentException("RankInProgress already exists with id " + rankInProgress.getId());
        }
    }

    public RankInProgress update(RankInProgress rankInProgress) {
        RankInProgress rankToUpdate = rankInProgressRepository.findById(rankInProgress.getId())
                .orElseThrow(() -> new EntityNotFoundException("RankInProgress not found with id " + rankInProgress.getId()));
        rankToUpdate.setUser(rankInProgress.getUser());
        rankToUpdate.setRank(rankInProgress.getRank());
        return rankInProgressRepository.save(rankToUpdate);
    }

    public void delete(long id) {
        if (rankInProgressRepository.existsById(id)) {
            rankInProgressRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("RankInProgress not found with id " + id);
        }
    }

}

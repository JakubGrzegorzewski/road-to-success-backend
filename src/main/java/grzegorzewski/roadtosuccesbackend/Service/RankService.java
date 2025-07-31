package grzegorzewski.roadtosuccesbackend.Service;

import grzegorzewski.roadtosuccesbackend.Model.Rank;
import grzegorzewski.roadtosuccesbackend.Model.RankInProgress;
import grzegorzewski.roadtosuccesbackend.Repository.RankInProgressRepository;
import grzegorzewski.roadtosuccesbackend.Repository.RankRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankService {
    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private RankInProgressRepository rankInProgressRepository;

    public List<Rank> getRanks() {
        return (List<Rank>) rankRepository.findAll();
    }

    public Rank getById(long id) {
        return rankRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rank not found with id " + id));
    }

    public Rank findRankForRankInProgress(long rankInProgressId) {
        return rankInProgressRepository.findById(rankInProgressId).orElseThrow(
                () -> new EntityNotFoundException("RankInProgress not found with id " + rankInProgressId))
                .getRank();
    }

    public Rank save(Rank rank) {
        if (!rankRepository.existsById(rank.getId())) {
            return rankRepository.save(rank);
        } else {
            throw new EntityNotFoundException("Rank already exists with id " + rank.getId());
        }
    }

    public Rank update(Rank rank) {
        return rankRepository.save(rank);
    }

    public void delete(long id) {
        if (rankRepository.existsById(id)) {
            rankRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Rank not found with id " + id);
        }
    }



}

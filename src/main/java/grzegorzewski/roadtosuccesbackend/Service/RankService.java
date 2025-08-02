package grzegorzewski.roadtosuccesbackend.Service;

import grzegorzewski.roadtosuccesbackend.Dto.RankDto;
import grzegorzewski.roadtosuccesbackend.Mapper.RankMapper;
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

    @Autowired
    private RankMapper rankMapper;

    public List<RankDto> getRanks() {
        List<Rank> ranks = (List<Rank>) rankRepository.findAll();
        return rankMapper.toDtoList(ranks);
    }

    public RankDto getById(long id) {
        Rank rank = rankRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rank not found with id " + id));
        return rankMapper.toDto(rank);
    }

    public RankDto findRankForRankInProgress(long rankInProgressId) {
        RankInProgress rankInProgress = rankInProgressRepository.findById(rankInProgressId)
                .orElseThrow(() -> new EntityNotFoundException("RankInProgress not found with id " + rankInProgressId));
        return rankMapper.toDto(rankInProgress.getRank());
    }

    public RankDto save(RankDto rankDto) {
        if (rankDto.getId() != null && rankRepository.existsById(rankDto.getId())) {
            throw new IllegalArgumentException("Rank already exists with id " + rankDto.getId());
        }

        Rank rank = rankMapper.toEntity(rankDto);
        Rank savedRank = rankRepository.save(rank);
        return rankMapper.toDto(savedRank);
    }

    public RankDto update(RankDto rankDto) {
        if (rankDto.getId() == null) {
            throw new IllegalArgumentException("Rank ID cannot be null for update operation");
        }
        if (!rankRepository.existsById(rankDto.getId())) {
            throw new EntityNotFoundException("Rank not found with id " + rankDto.getId());
        }

        Rank rank = rankMapper.toEntity(rankDto);
        Rank updatedRank = rankRepository.save(rank);
        return rankMapper.toDto(updatedRank);
    }

    public void delete(long id) {
        if (rankRepository.existsById(id)) {
            rankRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Rank not found with id " + id);
        }
    }
}
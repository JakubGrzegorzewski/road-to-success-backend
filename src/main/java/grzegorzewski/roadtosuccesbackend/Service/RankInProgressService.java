package grzegorzewski.roadtosuccesbackend.Service;

import grzegorzewski.roadtosuccesbackend.Dto.RankInProgressDto;
import grzegorzewski.roadtosuccesbackend.Mapper.RankInProgressMapper;
import grzegorzewski.roadtosuccesbackend.Model.AppUser;
import grzegorzewski.roadtosuccesbackend.Model.Rank;
import grzegorzewski.roadtosuccesbackend.Model.RankInProgress;
import grzegorzewski.roadtosuccesbackend.Repository.RankInProgressRepository;
import grzegorzewski.roadtosuccesbackend.Repository.RankRepository;
import grzegorzewski.roadtosuccesbackend.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankInProgressService {

    @Autowired
    private RankInProgressRepository rankInProgressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private RankInProgressMapper rankInProgressMapper;

    public RankInProgressDto findById(long id) {
        RankInProgress rankInProgress = rankInProgressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RankInProgress not found with id " + id));
        return rankInProgressMapper.toDto(rankInProgress);
    }

    public List<RankInProgressDto> findAllRanksInProgressForUser(long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        return rankInProgressMapper.toDtoList(user.getRanksInProgress());
    }

    public RankInProgressDto save(RankInProgressDto rankInProgressDto) {
        if (rankInProgressDto.getId() != null && rankInProgressRepository.existsById(rankInProgressDto.getId())) {
            throw new IllegalArgumentException("RankInProgress already exists with id " + rankInProgressDto.getId());
        }

        RankInProgress rankInProgress = rankInProgressMapper.toEntity(rankInProgressDto);

        // Set relationships
        if (rankInProgressDto.getUserId() != null) {
            AppUser user = userRepository.findById(rankInProgressDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id " + rankInProgressDto.getUserId()));
            rankInProgress.setUser(user);
        }

        if (rankInProgressDto.getRankId() != null) {
            Rank rank = rankRepository.findById(rankInProgressDto.getRankId())
                    .orElseThrow(() -> new EntityNotFoundException("Rank not found with id " + rankInProgressDto.getRankId()));
            rankInProgress.setRank(rank);
        }

        RankInProgress savedRankInProgress = rankInProgressRepository.save(rankInProgress);
        return rankInProgressMapper.toDto(savedRankInProgress);
    }

    public RankInProgressDto update(RankInProgressDto rankInProgressDto) {
        if (rankInProgressDto.getId() == null) {
            throw new IllegalArgumentException("RankInProgress ID cannot be null for update operation");
        }
        if (!rankInProgressRepository.existsById(rankInProgressDto.getId())) {
            throw new EntityNotFoundException("RankInProgress not found with id " + rankInProgressDto.getId());
        }

        RankInProgress existingRankInProgress = rankInProgressRepository.findById(rankInProgressDto.getId()).get();
        existingRankInProgress.setStatus(rankInProgressDto.getStatus());

        RankInProgress updatedRankInProgress = rankInProgressRepository.save(existingRankInProgress);
        return rankInProgressMapper.toDto(updatedRankInProgress);
    }

    public void delete(long id) {
        if (rankInProgressRepository.existsById(id)) {
            rankInProgressRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("RankInProgress not found with id " + id);
        }
    }
}
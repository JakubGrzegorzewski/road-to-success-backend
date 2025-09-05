package grzegorzewski.roadtosuccesbackend.Service;

import grzegorzewski.roadtosuccesbackend.Document.Write.AdvancementDocument;
import grzegorzewski.roadtosuccesbackend.Document.Write.TaskData;
import grzegorzewski.roadtosuccesbackend.Document.Write.UserData;
import grzegorzewski.roadtosuccesbackend.Dto.BasicRankInProgressDto;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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

    public byte[] generateFile(long id) {
        RankInProgress rankInProgress = rankInProgressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RankInProgress not found with id " + id));


        UserData userData = new UserData();

        userData.setMenteeName(rankInProgress.getUser().getFullName());
        userData.setMentorName(rankInProgress.getMentor().getFullName());
        userData.setIdea(rankInProgress.getRank().getIdea());

        List<TaskData> tasks = new ArrayList<>();
        rankInProgress.getTasks().forEach(task -> {
            TaskData taskData = new TaskData();
            taskData.setTitle(task.getRequirement().getNumber() + ". " + task.getRequirement().getContent());
            taskData.setIdeaPart(task.getPartIdea());
            taskData.setTask(task.getContent());
            tasks.add(taskData);
        });
        userData.setTasks(tasks);
        userData.setAdvancement(rankInProgress.getRank().getShortName());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            AdvancementDocument advancementDocument = new AdvancementDocument(userData);
            advancementDocument.generateDocument(baos);

        }catch (Exception e) {
            throw new RuntimeException("Error generating document: " + e.getMessage(), e);
        }
        return baos.toByteArray();
    }

    public List<RankInProgressDto> findAllRanksInProgressForUser(long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        return rankInProgressMapper.toDtoList(user.getRanksInProgress());
    }

    public List<RankInProgressDto> findAllRanksInProgressForMentor(long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        return rankInProgressMapper.toDtoList(user.getMentorRanksInProgress());
    }

    public BasicRankInProgressDto findBasicById(long id) {
        RankInProgress rankInProgress = rankInProgressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RankInProgress not found with id " + id));
        return RankInProgressMapper.toBasicDto(rankInProgress);
    }

    public List<BasicRankInProgressDto> findAllBasicRanksInProgressForUser(long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        List<RankInProgress> ranksInProgress = user.getRanksInProgress();
        List<BasicRankInProgressDto> basicDtos = new ArrayList<>();
        for (RankInProgress rank : ranksInProgress) {
            basicDtos.add(RankInProgressMapper.toBasicDto(rank));
        }
        return basicDtos;
    }

    public List<BasicRankInProgressDto> findAllBasicRanksInProgressForMentor(long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        List<RankInProgress> ranksInProgress = user.getMentorRanksInProgress();
        List<BasicRankInProgressDto> basicDtos = new ArrayList<>();
        for (RankInProgress rank : ranksInProgress) {
            basicDtos.add(RankInProgressMapper.toBasicDto(rank));
        }
        return basicDtos;
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

        if (rankInProgressDto.getMentorId() != null) {
            AppUser mentor = userRepository.findById(rankInProgressDto.getMentorId())
                    .orElseThrow(() -> new EntityNotFoundException("Mentor not found with id " + rankInProgressDto.getMentorId()));
            rankInProgress.setMentor(mentor);
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
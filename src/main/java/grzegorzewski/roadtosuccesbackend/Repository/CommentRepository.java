package grzegorzewski.roadtosuccesbackend.Repository;

import grzegorzewski.roadtosuccesbackend.Model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
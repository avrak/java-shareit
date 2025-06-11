package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Comment;

import java.util.Collection;

public interface CommentRepository  extends JpaRepository<Comment, Long> {

    public Collection<Comment> findCommentsByItemId(Long itemId);

    public Collection<Comment> findCommentsByUser(Long userId);
}

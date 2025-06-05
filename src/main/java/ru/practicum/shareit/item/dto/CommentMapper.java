package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.Comment;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setItem(comment.getItem());
        commentDto.setUser(comment.getUser());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setComment(comment.getComment());

        return commentDto;
    }

    public static Comment toComment(CommentDto commentDto) {
        Comment comment = new Comment();

        comment.setId(commentDto.getId());
        comment.setItem(commentDto.getItem());
        comment.setUser(commentDto.getUser());
        comment.setCreatedAt(commentDto.getCreatedAt());
        comment.setComment(commentDto.getComment());

        return comment;
    }
}

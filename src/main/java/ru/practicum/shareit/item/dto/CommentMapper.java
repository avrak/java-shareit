package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.Comment;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setItemId(comment.getItemId());
        commentDto.setUser(comment.getUser());
        commentDto.setCreated(comment.getCreatedAt());
        commentDto.setText(comment.getText());
        commentDto.setAuthorName(comment.getAuthor() != null ? comment.getAuthor().getName() : null);

        return commentDto;
    }

    public static Comment toComment(CommentDto commentDto) {
        Comment comment = new Comment();

        comment.setId(commentDto.getId());
        comment.setItemId(commentDto.getItemId());
        comment.setUser(commentDto.getUser());
        comment.setCreatedAt(commentDto.getCreated());
        comment.setText(commentDto.getText());

        return comment;
    }

}

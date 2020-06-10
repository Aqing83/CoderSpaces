package life.qing.community.dto;

import life.qing.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long LikeCount;
    private String content;
    private Integer commentCount;
    private User user;
}

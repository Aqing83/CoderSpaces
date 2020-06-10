package life.qing.community.mapper;

import life.qing.community.model.Comment;
import life.qing.community.model.CommentExample;
import life.qing.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment record);
}
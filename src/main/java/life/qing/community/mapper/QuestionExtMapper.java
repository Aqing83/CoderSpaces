package life.qing.community.mapper;

import life.qing.community.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);

}
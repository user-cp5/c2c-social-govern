package com.zhss.c2c.social.govern.reviewer.mapper;

import com.zhss.c2c.social.govern.reviewer.domain.ReviewerTaskStatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: LiaoLingJian
 * @Description: ${description}
 * @Date: 2021/7/23 21:04
 * @Version: 1.0
 */

@Mapper
public interface ReviewMapper {

    /**
     * 通过任务id找评审员
     * @param taskId
     * @return
     */

    @Select("select userId from user where taskId=${taskId}")
    List<Long> getReview(Long taskId);


    @Insert("insert  into `review_task`(`id`,`reviewid`,`reportTaskId`,`status`) values" +
            "(${id},${reviewid},${reportTaskId},${status})")
    void isertReviewTask(ReviewerTaskStatus reviewerTaskStatus);

}

package com.zhss.c2c.social.govern.reviewer.dao;

import com.zhss.c2c.social.govern.reviewer.domain.ReviewerTaskStatus;

import java.util.List;

/**
 * @Author: LiaoLingJian
 * @Description: ${description}
 * @Date: 2021/7/23 21:08
 * @Version: 1.0
 */
public interface ReviewDao {
    /**
     * 通过任务id找评审员
     * @param taskId
     * @return
     */
    List<Long> getReviewListToTaskId(Long taskId);


    /**
     * 插入评审员任务
     * @param reviewerTaskStatus
     */
    void isertReviewTask(ReviewerTaskStatus reviewerTaskStatus);

}

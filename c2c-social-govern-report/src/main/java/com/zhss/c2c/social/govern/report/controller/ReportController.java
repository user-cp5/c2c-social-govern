package com.zhss.c2c.social.govern.report.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.zhss.c2c.social.govern.report.domain.ReportTask;
import com.zhss.c2c.social.govern.report.domain.ReportTaskVote;
import com.zhss.c2c.social.govern.report.service.ReportTaskService;
import com.zhss.c2c.social.govern.report.service.ReportTaskVoteService;
import com.zhss.c2c.social.govern.reviewer.api.ReviewerService;
import com.zhss.c2c.social.govern.reward.api.RewardService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 举报服务的接口
 */
@RestController
public class ReportController {

    static {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("ReviewerServiceResource");
        rule.setCount(200);
        rule.setSlowRatioThreshold(0.5);
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

    /**
     * 举报任务Service组件
     */
    @Autowired
    private ReportTaskService reportTaskService;
    /**
     * 举报任务投票Service组件
     */
    @Autowired
    private ReportTaskVoteService reportTaskVoteService;

    /**
     * 评审员服务
     */
    @Reference(version = "1.0.0",
            interfaceClass = ReviewerService.class,
            cluster = "failfast")
    private ReviewerService reviewerService;

    /**
     * 奖励服务
     */
    @Reference(version = "1.0.0",
            interfaceClass = RewardService.class,
            cluster = "failfast")
    private RewardService rewardService;

    /**
     * 提交举报接口
     * @param reportTask 举报任务
     * @return
     */
    @GetMapping("/report")
    public String report(ReportTask reportTask) {
        // 在本地数据库增加一个举报任务
        reportTaskService.add(reportTask);

<<<<<<< HEAD
       // ConcurrentHashMap
=======
        //ConcurrentHashMap
>>>>>>> feature/001

        List<Long> reviewerIds = null;

        Entry entry = null;
        try {
            entry = SphU.entry("ReviewerServiceResource");
            // 调用评审员服务，选择一批评审员
            reviewerIds = reviewerService.selectReviewers(reportTask.getId());
        } catch (BlockException e1) {
            System.out.println("系统熔断或者限流，无法正常运行......");
            return "fail";
        } catch (Exception e) {
            System.out.println("系统出现异常，无法正常运行......");
            return "fail";
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }

        // 在本地数据库初始化这批评审员对举报任务的投票状态
        if(reviewerIds != null) {
            reportTaskVoteService.initVotes(reviewerIds, reportTask.getId());
        }

        // 模拟发送push消息给评审员
        System.out.println("模拟发送push消息给评审员.....");

        return "success";
    }

    /**
     * 查询举报任务
     * @param id 举报任务id
     * @return
     */
    @GetMapping("/report/query/{id}")
    public ReportTask queryReportTaskId(
            @PathVariable("id") Long id) {
        return reportTaskService.queryById(id);
    }

    /**
     * 对举报任务进行投票
     * @param reviewerId 评审员id
     * @param reportTaskId 举报任务id
     * @param voteResult 投票结果
     * @return
     */
    @GetMapping("/report/vote")
    public String vote(
            Long reviewerId,
            Long reportTaskId,
            Integer voteResult) {
        // 本地数据库记录投票
        reportTaskVoteService.vote(reviewerId, reportTaskId, voteResult);
        // 调用评审员服务，标记本次投票结束
        reviewerService.finishVote(reviewerId, reportTaskId);

        // 对举报任务进行归票
        Boolean hasFinishedVote = reportTaskVoteService
                .calculateVotes(reportTaskId);

        // 如果举报任务得到归票结果
        if(hasFinishedVote) {
            // 发放奖励
            List<ReportTaskVote> reportTaskVotes = reportTaskVoteService
                    .queryByReportTaskId(reportTaskId);
            List<Long> reviewerIds = new ArrayList<Long>();

            for(ReportTaskVote reportTaskVote : reportTaskVotes) {
                reviewerIds.add(reportTaskVote.getReviewerId());
            }

            rewardService.giveReward(reviewerIds);

            // 推送消息到MQ，告知其他系统，本次评审结果
            System.out.println("推送消息到MQ，告知其他系统，本次评审结果");
        }

        return "success";
    }

}

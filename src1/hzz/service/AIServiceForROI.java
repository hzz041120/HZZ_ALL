package hzz.service;

import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.JobType;
import hzz.domain.Machine;
import hzz.util.ExecuteResultComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 寻找最优利润
 * 
 * @author hzz041120 2014年4月20日 下午5:04:51
 */
public class AIServiceForROI {

    /** 初始条件 */
    // 加工日期限制
    public static int                   worktime      = 0;
    // 工件类型及生产数量
    public static Map<JobType, Integer> jobType$count = new HashMap<JobType, Integer>();
    // 机器列表
    public static List<Machine>         machineList   = new ArrayList<Machine>();
    /** 算法精细化参数 */
    // 初始化解集大小 必须是偶数个便于交叉 改进方案再说
    private int                         initResSize   = 5 * 2;
    // 遗传代数
    private int                         generation    = 10000;

    private JobSelection                jobSelection  = new JobSelection(jobType$count.keySet());

    /**
     * 初始化生产环境
     */
    public void init() {
        // FIXME 初始化
    }

    /**
     * 产生生产计划
     */
    public void process() {
        /** 获得自我加工的产品流水 */
        ExecuteResult selfRes = findSelfJobList();
        /** 获得外包加工的产品流水 */
        ExecuteResult outSourcingRes = findOutSourcingJobList(selfRes);
        /** 获得拒绝加工的产品流水 */
        ExecuteResult rejectRes = findRejectJobList(selfRes, outSourcingRes);
        /** 打印生产计划表 */
        // FIXME
    }

    private ExecuteResult findRejectJobList(ExecuteResult selfRes, ExecuteResult outSourcingRes) {
        //FIXME
        return null;
    }

    private ExecuteResult findOutSourcingJobList(ExecuteResult selfRes) {
        //除掉自产的剩余工件类型和数量
        Map<JobType, Integer> jobType$count = new HashMap<JobType, Integer>();
        //这里又特么要一个遗传算法计算 不过这个简单很多 FIXME
        return null;
    }

    private ExecuteResult findSelfJobList() {
        /** 初始化解集合 */
        List<ExecuteResult> resultList = initOriginalResult(initResSize);
        for (int i = 0; i < generation; i++) {
            /** 交叉 */
            resultList = recombination(resultList);
            /** 变异 */
            mutation(resultList);
        }
        return getBestResult(resultList);
    }

    /**
     * 执行变异，当前采用的是随机单工件变异
     * 
     * @param resultList
     * @return
     */
    private void mutation(List<ExecuteResult> resultList) {
        MutationService.mutation(resultList);
    }

    /**
     * 执行交叉计算
     * 
     * @param resultList
     * @return
     */
    private List<ExecuteResult> recombination(List<ExecuteResult> resultList) {
        return RecombinationService.doIntermediateRecobination(resultList);
    }

    private ExecuteResult getBestResult(List<ExecuteResult> result) {
        Collections.sort(result, new ExecuteResultComparator());
        return result.get(0);
    }

    /*
     * 产生初始化解集合
     * @param initResSize
     * @return
     */
    private List<ExecuteResult> initOriginalResult(int initResSize) {
        List<ExecuteResult> resList = new ArrayList<ExecuteResult>();
        for (int i = 0; i < initResSize; i++) {
            ExecuteResult res = new ExecuteResult(worktime);
            while (true) {
                //如果工件数量已经达到要求则取消掉该类型工件的份额
                JobType jobType = jobSelection.getRandomJobByRoi();
                Integer currentJobCount = res.getJobType$count().get(jobType);
                if (currentJobCount > jobType$count.get(jobType)) {
                    Collection<JobType> jobs = jobSelection.getJobs();
                    jobs.remove(jobType);
                    jobSelection = new JobSelection(jobs);
                    continue;
                }
                Job job = jobType.getInstance();
                if (!res.addJob(job)) {
                    break;
                }

            }
            resList.add(res);
        }
        return resList;
    }

}

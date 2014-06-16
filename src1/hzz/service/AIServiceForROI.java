package hzz.service;

import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.JobType;
import hzz.domain.Machine;
import hzz.domain.OutSourcingCorp;
import hzz.util.ExecuteResultComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 寻找最优利润
 * 
 * @author hzz041120 2014年4月20日 下午5:04:51
 */
public class AIServiceForROI {

    /** 初始条件 */
    /** 加工日期限制 */
    public static int                           worktime                = 0;
    /** 工件类型及生产数量 */
    public static Map<JobType, Integer>         jobType$count           = new HashMap<JobType, Integer>();
    /** 工件类型及其外包商 */
    public static Map<JobType, OutSourcingCorp> jobType$outSourcingCorp = new HashMap<JobType, OutSourcingCorp>();
    /** 算法精细化参数 */
    /** 初始化解集大小 必须是偶数个便于交叉 改进方案再说 */
    private int                                 initResSize             = 5 * 2;
    /** 遗传代数 */
    private int                                 generation              = 10000;

    /**
     * 初始化生产环境
     */
    public void init() {
        worktime = 400;
        // 初始化工件表
        jobType$count = new HashMap<JobType, Integer>();
        LinkedHashMap<Machine, Integer> j1Machine$time = new LinkedHashMap<Machine, Integer>();
        j1Machine$time.put(Machine.newMachine("m1"), 5);
        j1Machine$time.put(Machine.newMachine("m2"), 5);
        j1Machine$time.put(Machine.newMachine("m3"), 7);
        jobType$count.put(JobType.newJobType("J1", 78, -9, 53, j1Machine$time), 100);

        LinkedHashMap<Machine, Integer> j2Machine$time = new LinkedHashMap<Machine, Integer>();
        j2Machine$time.put(Machine.newMachine("m1"), 4);
        j2Machine$time.put(Machine.newMachine("m2"), 6);
        j2Machine$time.put(Machine.newMachine("m3"), 8);
        jobType$count.put(JobType.newJobType("J2", 95, -10, 61, j2Machine$time), 90);

        LinkedHashMap<Machine, Integer> j3Machine$time = new LinkedHashMap<Machine, Integer>();
        j3Machine$time.put(Machine.newMachine("m1"), 2);
        j3Machine$time.put(Machine.newMachine("m2"), 4);
        j3Machine$time.put(Machine.newMachine("m3"), 10);
        jobType$count.put(JobType.newJobType("J3", 102, -7, 43, j3Machine$time), 80);

        LinkedHashMap<Machine, Integer> j4Machine$time = new LinkedHashMap<Machine, Integer>();
        j4Machine$time.put(Machine.newMachine("m1"), 7);
        j4Machine$time.put(Machine.newMachine("m2"), 9);
        j4Machine$time.put(Machine.newMachine("m3"), 5);
        jobType$count.put(JobType.newJobType("J4", 89, -13, 67, j4Machine$time), 70);

        LinkedHashMap<Machine, Integer> j5Machine$time = new LinkedHashMap<Machine, Integer>();
        j5Machine$time.put(Machine.newMachine("m1"), 9);
        j5Machine$time.put(Machine.newMachine("m2"), 3);
        j5Machine$time.put(Machine.newMachine("m3"), 5);
        jobType$count.put(JobType.newJobType("J5", 93, -5, 55, j5Machine$time), 60);
        // 初始化外包商表

    }

    /**
     * 产生生产计划
     */
    public void process() {
        /** FIXME 只考虑了自我加工收益>外包>拒绝加工的场景 */
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
        // FIXME
        return null;
    }

    private ExecuteResult findOutSourcingJobList(ExecuteResult selfRes) {
        // 除掉自产的剩余工件类型和数量
        Map<JobType, Integer> jobType$count = new HashMap<JobType, Integer>();
        // 这里又特么要一个遗传算法计算 不过这个简单很多 FIXME
        return null;
    }

    private ExecuteResult findSelfJobList() {
        //FIXME 时间足够充裕需要按照纯粹的高ROI工件填充法处理
        /** 初始化解集合 */
        List<ExecuteResult> resultList = initOriginalResult(initResSize);
        System.out.println("ProfitAndLoss: " + getBestResult(resultList).getProfitAndLoss());
        for (int i = 0; i < generation; i++) {
            /** 交叉 */
            resultList = recombination(resultList);
            System.out.println("ProfitAndLoss" + i + ": " + getBestResult(resultList).getProfitAndLoss());
            /** 变异 */
            mutation(resultList);
            System.out.println("ProfitAndLoss" + i + ": " + getBestResult(resultList).getProfitAndLoss());
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
        JobSelection jobSelection = new JobSelection(jobType$count.keySet());
        List<ExecuteResult> resList = new ArrayList<ExecuteResult>();
        for (int i = 0; i < initResSize; i++) {
            ExecuteResult res = new ExecuteResult(worktime);
            System.out.println("Execute " + i + "Start init!");
            while (true) {
                // 如果工件数量已经达到要求则取消掉该类型工件的份额
                JobType jobType = jobSelection.getRandomJobByRoi();
                Integer currentJobCount = res.getJobType$count().get(jobType);
                if (currentJobCount == null) {
                    currentJobCount = 0;
                }
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

    public static void main(String[] args) {
        AIServiceForROI service = new AIServiceForROI();
        service.init();
        service.generation = 1000;
        service.initResSize = 10;
        
        service.process();
    }

}

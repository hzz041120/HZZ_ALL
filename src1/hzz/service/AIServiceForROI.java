package hzz.service;

import hzz.constants.WorkflowType;
import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.JobType;
import hzz.domain.Machine;
import hzz.domain.OutSourcingCorp;
import hzz.service.init.InitJobService;
import hzz.util.ExecuteResultComparator;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 寻找最优利润
 * 
 * @author hzz041120 2014年4月20日 下午5:04:51
 */
public class AIServiceForROI {

    /** 初始条件 */
    /** 加工日期限制 */
    public static int                                 worktime                 = 0;
    /** 工件类型及生产数量 */
    public static Map<JobType, Integer>               jobType$count            = new HashMap<JobType, Integer>();
    /** 工件类型及其外包商 */
    public static Map<JobType, List<OutSourcingCorp>> jobType$outSourcingCorps = new HashMap<JobType, List<OutSourcingCorp>>();
    /** 算法精细化参数 */
    /** 初始化解集大小 必须是偶数个便于交叉 改进方案再说 */
    public static final int                           initResSize              = 5 * 2;
    /** 遗传代数 */
    public static final int                           generation               = 10000;

    /**
     * 初始化生产环境
     */
    public void init() {
        worktime = 50;
        // 初始化工件表
        jobType$count = new HashMap<JobType, Integer>();
        LinkedHashMap<Machine, Integer> j1Machine$time = new LinkedHashMap<Machine, Integer>();
        j1Machine$time.put(Machine.newMachine("m1"), 5);
        j1Machine$time.put(Machine.newMachine("m2"), 5);
        j1Machine$time.put(Machine.newMachine("m3"), 7);
        jobType$count.put(JobType.newJobType("J1", 78, -9, 53, j1Machine$time), 2);

        LinkedHashMap<Machine, Integer> j2Machine$time = new LinkedHashMap<Machine, Integer>();
        j2Machine$time.put(Machine.newMachine("m1"), 4);
        j2Machine$time.put(Machine.newMachine("m2"), 6);
        j2Machine$time.put(Machine.newMachine("m3"), 8);
        jobType$count.put(JobType.newJobType("J2", 95, -10, 61, j2Machine$time), 2);

        LinkedHashMap<Machine, Integer> j3Machine$time = new LinkedHashMap<Machine, Integer>();
        j3Machine$time.put(Machine.newMachine("m1"), 2);
        j3Machine$time.put(Machine.newMachine("m2"), 4);
        j3Machine$time.put(Machine.newMachine("m3"), 10);
        jobType$count.put(JobType.newJobType("J3", 102, -7, 43, j3Machine$time), 2);

        LinkedHashMap<Machine, Integer> j4Machine$time = new LinkedHashMap<Machine, Integer>();
        j4Machine$time.put(Machine.newMachine("m1"), 7);
        j4Machine$time.put(Machine.newMachine("m2"), 9);
        j4Machine$time.put(Machine.newMachine("m3"), 5);
        jobType$count.put(JobType.newJobType("J4", 89, -13, 67, j4Machine$time), 2);

        LinkedHashMap<Machine, Integer> j5Machine$time = new LinkedHashMap<Machine, Integer>();
        j5Machine$time.put(Machine.newMachine("m1"), 9);
        j5Machine$time.put(Machine.newMachine("m2"), 3);
        j5Machine$time.put(Machine.newMachine("m3"), 5);
        jobType$count.put(JobType.newJobType("J5", 93, -5, 55, j5Machine$time), 2);
        // 初始化外包商表

    }

    /**
     * 产生生产计划
     */
    public void process() {
        /** FIXME 只考虑了自我加工收益>外包>拒绝加工的场景 */
        /** 获得自我加工的产品流水 */
        ExecuteResult selfRes = findSelfJobList(jobType$count);
        /** 获得外包加工的产品流水 */
        Map<JobType, Integer> _jobType$count = new HashMap<JobType, Integer>();
        if (needTodo(selfRes, _jobType$count)) {
            ExecuteResult afterOutSourcingRes = findOutSourcingJobList(selfRes);
            /** 获得拒绝加工的产品流水 */
            _jobType$count = new HashMap<JobType, Integer>();
            if (needTodo(afterOutSourcingRes, _jobType$count)) {
                ExecuteResult result = findRejectJobList(afterOutSourcingRes);
            }
            /** 打印生产计划表 */
            // FIXME
        }

    }

    protected boolean needTodo(ExecuteResult res, Map<JobType, Integer> _jobType$count) {
        Set<Entry<JobType, Integer>> selfDoCount = res.getJobType$count().entrySet();
        boolean needTodo = false;
        for (Entry<JobType, Integer> selfDoTypeCount : selfDoCount) {
            JobType type = selfDoTypeCount.getKey();
            Integer needDo = _jobType$count.get(type) == null ? 0 : _jobType$count.get(type);
//            System.out.println(type.getJobName() + " >>> "  + needDo  + " |||| " + selfDoTypeCount.getValue());
            int todoCount = needDo - selfDoTypeCount.getValue();
            if (todoCount > 0) needTodo = true;
            _jobType$count.put(type, todoCount);
        }
        return needTodo;
    }

    private ExecuteResult findRejectJobList(ExecuteResult afterOutSourcingRes) {
        Map<JobType, Integer> currentJobTypeCount = afterOutSourcingRes.getJobType$count();
        for (Map.Entry<JobType, Integer> entry : jobType$count.entrySet()) {
            JobType jobType = entry.getKey();
            Integer currentjc = currentJobTypeCount.get(jobType);
            Integer needCount = entry.getValue();
            while (currentjc < needCount) {
                Job job = jobType.getInstance(WorkflowType.reject);
                afterOutSourcingRes.addJob(job);
                currentjc++;
            }
        }
        return afterOutSourcingRes;
    }

    private ExecuteResult findOutSourcingJobList(ExecuteResult selfRes) {
        /** 初始化解集合 */
        List<ExecuteResult> resultList = InitJobService.initOutSourcingOriginalResult(selfRes);
        ExecuteResult bestResult = getBestResult(resultList);
        System.out.println("ProfitAndLoss: " + bestResult.getProfitAndLoss());
        for (int i = 0; i < generation; i++) {
            /** 交叉 */
            resultList = recombination(resultList, WorkflowType.outSourcing);
            bestResult = getBestResult(resultList);
            System.out.println("A1 ProfitAndLoss" + i + ": " + bestResult.getProfitAndLoss());
            /** 变异 */
            mutation(resultList, WorkflowType.outSourcing);
            bestResult = getBestResult(resultList);
            System.out.println("A2 ProfitAndLoss" + i + ": " + bestResult.getProfitAndLoss());
        }
        return bestResult;
    }

    private ExecuteResult findSelfJobList(Map<JobType, Integer> _jobType$count) {
        // FIXME 时间足够充裕需要按照纯粹的高ROI工件填充法处理
        /** 初始化解集合 */
        List<ExecuteResult> resultList = InitJobService.initOriginalResult();
        ExecuteResult bestResult = getBestResult(resultList);
        System.out.println("ProfitAndLoss: " + bestResult.getProfitAndLoss());
        for (int i = 0; i < generation; i++) {
            /** 交叉 */
            resultList = recombination(resultList, WorkflowType.selfDo);
            bestResult = getBestResult(resultList);
            System.out.println("A1 ProfitAndLoss" + i + ": " + bestResult.getProfitAndLoss());
            /** 变异 */
            mutation(resultList, WorkflowType.selfDo);
            bestResult = getBestResult(resultList);
            System.out.println("A2 ProfitAndLoss" + i + ": " + bestResult.getProfitAndLoss());
        }
        return bestResult;
    }

    /**
     * 执行变异，当前采用的是随机单工件变异
     * 
     * @param resultList
     * @return
     */
    private void mutation(List<ExecuteResult> resultList, WorkflowType workflowType) {
        MutationService.mutation(resultList, workflowType);
    }

    /**
     * 执行交叉计算
     * 
     * @param resultList
     * @return
     */
    private List<ExecuteResult> recombination(List<ExecuteResult> resultList, WorkflowType workflowType) {
        return RecombinationService.doIntermediateRecobination(resultList, workflowType);
    }

    private ExecuteResult getBestResult(List<ExecuteResult> result) {
        Collections.sort(result, new ExecuteResultComparator());
        return result.get(0);
    }

    public static boolean checkCountOver(JobType jobType, Integer currentJobCount) {
        return currentJobCount >= AIServiceForROI.jobType$count.get(jobType);
    }

    public static void main(String[] args) {
        AIServiceForROI service = new AIServiceForROI();
        service.init();
        service.process();
    }

}

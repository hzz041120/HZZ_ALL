package hzz.service;

import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.JobType;
import hzz.domain.Machine;

import java.util.ArrayList;
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
    // 初始化解集大小
    private int                         initResSize   = 10;
    // 遗传代数
    private int                         generation    = 10000;

    private JobSelection                jobSelection  = new JobSelection(jobType$count.keySet());

    /**
     * 初始化生产环境
     */
    public void init() {
        // FIXME 初始化
    }

    public void process() {
        /** 获得自我加工的产品流水 */
        ExecuteResult selfRes = findSelfJobList();
        /** 获得外包加工的产品流水 */
        ExecuteResult outSourcingRes = findOutSourcingJobList(selfRes);
        /** 获得拒绝加工的产品流水 */
        ExecuteResult rejectRes = findRejectJobList(selfRes, outSourcingRes);
    }

    private ExecuteResult findRejectJobList(ExecuteResult selfRes, ExecuteResult outSourcingRes) {
        // TODO Auto-generated method stub
        return null;
    }

    private ExecuteResult findOutSourcingJobList(ExecuteResult selfRes) {
        // TODO Auto-generated method stub
        return null;
    }

    private ExecuteResult findSelfJobList() {
        /** 初始化解集合 */
        List<ExecuteResult> resultList = initOriginalResult(initResSize);
        for (int i = 0; i < generation; i++) {
            /** 交叉 */
            resultList = crossover(resultList);
            /** 变异 */
            resultList = mutation(resultList);
        }
        return getBestResult(resultList);
    }

    /**
     * 执行变异，当前采用的是随机单工件变异
     * 
     * @param resultList
     * @return
     */
    private List<ExecuteResult> mutation(List<ExecuteResult> resultList) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 执行交叉计算，当前采用的方案是实值重组的中间重组
     * 
     * @param resultList
     * @return
     */
    private List<ExecuteResult> crossover(List<ExecuteResult> resultList) {
        // TODO Auto-generated method stub
        return null;
    }

    private ExecuteResult getBestResult(List<ExecuteResult> originalResult) {
        // TODO Auto-generated method stub
        return null;
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
                JobType jobType = jobSelection.getRandomJobByRoi();
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

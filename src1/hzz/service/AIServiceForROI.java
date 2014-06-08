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

    // 加工日期限制
    public static int                   worktime      = 0;
    // 工件类型及生产数量
    public static Map<JobType, Integer> jobType$count = new HashMap<JobType, Integer>();
    // 机器列表
    public static List<Machine>   machineList   = new ArrayList<Machine>();
    // 初始化解集大小
    private int                   initResSize   = 10;

    private JobSelection          jobSelection  = new JobSelection(jobType$count.keySet());

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
        List<ExecuteResult> originalResult = initOriginalResult(initResSize);
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
            ExecuteResult res = new ExecuteResult();
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

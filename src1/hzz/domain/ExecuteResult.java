package hzz.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class ExecuteResult {

    // 总执行时间
    private Integer   timeCost;
    // 当前任务队列需要的工作时间
    private Integer   currentTimeCost;
    // 总损益
    private Integer   profitAndLoss;
    // 执行过行
    private List<Job> jobList = new ArrayList<Job>();
    // 以时间为维度的任务中间节点的任务下标,用于交叉计算
    private int       middlePoint;

    private boolean isAllowAddJob(Job job) {
        LinkedHashMap<Machine, Integer> machine$time = job.getJobType().getMachine$time();
        for (Entry<Machine, Integer> entry : machine$time.entrySet()) {
            Machine m = entry.getKey();
            int startWorkDelay = m.getStartWorkDelay();
            TimeEntry timeEntry = new TimeEntry();
            timeEntry.setStartTime(startWorkDelay);
            int endTime = startWorkDelay + entry.getValue();
            timeEntry.setEndTime(endTime);
            //更新机器的工作计划
            m.setStartWorkDelay(endTime);
            //FIXME 更新中间节点
        }
        return false;
    }

    /**
     * 添加一个任务进工作队列
     * 
     * @param job
     * @return true 表示添加成功，false表示无法添加了
     */
    public boolean addJob(Job job) {
        if (isAllowAddJob(job)) {
            jobList.add(job);
            return true;
        } else {
            return false;
        }
    }
}

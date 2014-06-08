package hzz.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class ExecuteResult {

    // y预期的可执行时间
    private Integer   worktime;
    // 当前任务队列需要的工作时间
    private Integer   currentTimeCost;
    // 总损益
    private Integer   profitAndLoss;
    // 执行过行
    private List<Job> jobList = new ArrayList<Job>();
    // 以时间为维度的任务中间节点的任务下标,用于交叉计算
    private double    middlePoint;

    public ExecuteResult(int worktime) {
        this.worktime = worktime;
    }

    private boolean isAllowAddJob(Job job) {
        LinkedHashMap<Machine, Integer> machine$time = job.getJobType().getMachine$time();
        // 上一台机器的当前工作时间点
        int preMachineDelay = 0;
        double middleTime = worktime / 2d;
        for (Entry<Machine, Integer> entry : machine$time.entrySet()) {
            Machine m = entry.getKey();
            int startWorkDelay = m.getStartWorkDelay();
            // 拿到当前机器的可开工时间点，在对比以上当前工件上个机器的完成时间，取MAX作为开工时间
            Integer jobMachineTimeCost = entry.getValue();
            if (preMachineDelay > startWorkDelay) {
                startWorkDelay = preMachineDelay;
            }
            TimeEntry timeEntry = new TimeEntry();
            timeEntry.setStartTime(startWorkDelay);
            int endTime = startWorkDelay + jobMachineTimeCost;
            if (endTime > worktime) {
                return false;
            }
            preMachineDelay = endTime;
            timeEntry.setEndTime(endTime);
            // 更新机器的工作计划
            m.setStartWorkDelay(endTime);
            if (startWorkDelay < middleTime && endTime >= middleTime) {
                setMiddlePoint(jobList.size());
            }
            job.getWorkDetails().put(m, timeEntry);
        }
        return true;
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

    public double getMiddlePoint() {
        return middlePoint;
    }

    public void setMiddlePoint(double middlePoint) {
        this.middlePoint = middlePoint;
    }
}

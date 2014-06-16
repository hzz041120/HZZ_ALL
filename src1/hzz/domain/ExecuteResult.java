package hzz.domain;

import hzz.service.AIServiceForROI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ExecuteResult {

    private String                resultName;
    // 预期的可执行时间
    private Integer               worktime;
    // 总损益
    private Integer               profitAndLoss = 0;
    // 当前任务的类型和数量
    private Map<JobType, Integer> jobType$count = new HashMap<JobType, Integer>();

    public Integer getProfitAndLoss() {
        return profitAndLoss;
    }

    public Map<JobType, Integer> getJobType$count() {
        return jobType$count;
    }

    // 执行过行
    private List<Job> jobList = new ArrayList<Job>();
    // 以时间为维度的任务中间节点的任务下标,用于交叉计算
    private int       middlePoint;

    public ExecuteResult(int worktime) {
        this.worktime = worktime;
    }

    private boolean isAllowAddJob(Job job) {
        LinkedHashMap<Machine, Integer> machine$time = job.getJobType().getMachine$time();
        // 上一台机器的当前工作时间点
        int preMachineDelay = 0;
        double middleTime = getWorktime() / 2d;
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
            if (endTime > getWorktime()) {
                resetAllMachine();
                return false;
            }
            preMachineDelay = endTime;
            timeEntry.setEndTime(endTime);
            // 更新机器的工作计划
            m.setStartWorkDelay(endTime);
            if (startWorkDelay < middleTime && endTime >= middleTime) {
                // System.out.println("startWorkDelay: " + startWorkDelay + ", middleTime:" + middleTime + ", endTime:"
                // + endTime);
                // 只要有一个机器触及中间时间节点则将当前节点设置为中间
                // System.out.println("Middle Point is : " + getJobList().size());
                if (middlePoint < 0) {
                    setMiddlePoint(getJobList().size());
                }
            }
            // System.out.println("---- Machine " + m.getMachineName() + " : start->" + timeEntry.getStartTime()
            // + ", end-->" + timeEntry.getEndTime());
            job.getWorkDetails().put(m, timeEntry);
            profitAndLoss += job.getJobType().getRev();
            Integer jobCount = jobType$count.get(job.getJobType());
            if (jobCount == null) {
                jobCount = 1;
            } else {
                jobCount++;
            }
        }
        return true;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public void resetAllMachine() {
        for (Machine m : Machine.machineMap.values()) {
            m.reset();
        }
    }

    /**
     * 添加一个任务进工作队列
     * 
     * @param job
     * @return true 表示添加成功，false表示无法添加了
     */
    public boolean addJob(Job job) {
        if (isAllowAddJob(job)) {
            getJobList().add(job);
            return true;
        } else {
            return false;
        }
    }

    public int getMiddlePoint() {
        return middlePoint;
    }

    public void setMiddlePoint(int middlePoint) {
        this.middlePoint = middlePoint;
    }

    public List<Job> getJobList() {
        return jobList;
    }

    public Integer getWorktime() {
        return worktime;
    }

    public Collection<JobType> getAvaliableJobTypeList() {
        Collection<JobType> avaliable = new ArrayList<JobType>();
        for (Map.Entry<JobType, Integer> entry : AIServiceForROI.jobType$count.entrySet()) {
            JobType key = entry.getKey();
            Integer currentCnt = jobType$count.get(key);
            currentCnt = currentCnt == null ? 0 : currentCnt;
            if (currentCnt <= entry.getValue()) {
                avaliable.add(key);
            }
        }
        return avaliable;
    }
    
    public String toString() {
        return this.getResultName() + " : " + this.getProfitAndLoss();
    }
}

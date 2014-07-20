package hzz.domain;

import hzz.constants.WorkflowType;
import hzz.service.AIServiceForROI;
import hzz.service.select.JobSelection;
import hzz.service.select.OutSourcingSelection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ExecuteResult {

    private String                        resultName;
    // 预期的可执行时间
    private Integer                       worktime;
    // 记录每个外包公司的已消耗时间
    private Map<OutSourcingCorp, Integer> outSourcingCorpTimeMap = new HashMap<OutSourcingCorp, Integer>();
    // 总损益
    private Integer                       profitAndLoss          = 0;
    // 当前任务的类型和数量
    private Map<JobType, Integer>         jobType$count          = new HashMap<JobType, Integer>();

    public Integer getProfitAndLoss() {
        return profitAndLoss;
    }

    public Map<JobType, Integer> getJobType$count() {
        return jobType$count;
    }

    // 执行过行
    private List<Job>                       jobList           = new ArrayList<Job>();
    // 外包任务队列
    private Map<OutSourcingCorp, List<Job>> outSourcingJobMap = new HashMap<OutSourcingCorp, List<Job>>();
    // 拒绝加工任务队列
    private List<Job>                       rejectJobList     = new ArrayList<Job>();
    // 以时间为维度的任务中间节点的任务下标,用于交叉计算
    private int                             middlePoint;

    public ExecuteResult(int worktime) {
        this(worktime, null);
    }

    public Map<OutSourcingCorp, List<Job>> getOutSourcingJobMap() {
        return outSourcingJobMap;
    }

    public void setOutSourcingJobMap(Map<OutSourcingCorp, List<Job>> outSourcingJobMap) {
        this.outSourcingJobMap = outSourcingJobMap;
    }

    public List<Job> getRejectJobList() {
        return rejectJobList;
    }

    public ExecuteResult(int worktime, ExecuteResult selfRes) {
        this.worktime = worktime;
        if (selfRes != null) {
            this.jobType$count = new HashMap<JobType, Integer>(selfRes.getJobType$count());
            this.jobList = new ArrayList<Job>(selfRes.getJobList());
            this.outSourcingJobMap = new HashMap<OutSourcingCorp, List<Job>>(selfRes.getOutSourcingJobMap());
            this.rejectJobList = new ArrayList<Job>(selfRes.getRejectJobList());
            this.middlePoint = selfRes.getMiddlePoint();
            this.profitAndLoss = selfRes.getProfitAndLoss();
            this.resultName = selfRes.getResultName();
        }
    }

    protected boolean preSelfDoAdd(Job job) {
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
                // 只要有一个机器触及中间时间节点则将当前节点设置为中间
                if (middlePoint < 0) {
                    setMiddlePoint(getJobList().size());
                }
            }
            job.getWorkDetails().put(m, timeEntry);
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
     * 添加一个任务进工作队列 FIXME 把外部的数量不允许的异常放入这里统一处理
     * 
     * @param job
     * @return true 表示添加成功，false表示无法添加了
     */
    public boolean addJob(Job job) {
        JobType jobType = job.getJobType();
        Integer jobCount = this.jobType$count.get(jobType);
        if (jobCount == null) {
            jobCount = 1;
        } else {
            jobCount++;
        }
        if (jobCount > AIServiceForROI.jobType$count.get(jobType)) {
            return false;
        }
        jobType$count.put(jobType, jobCount);
        boolean allowAdd = true;
        WorkflowType workflowType = job.getWorkflowType();
        switch (workflowType) {
            case selfDo:
                allowAdd = preSelfDoAdd(job);
                if (allowAdd) {
                    getJobList().add(job);
                    profitAndLoss += jobType.getRev();
                } else return false;
                break;
            case outSourcing:
                if (allowAdd = preOutSourcingAdd(job)) {
                    final OutSourcingCorp _outSourcingCorp = job.getOutSourcingCorp();
                    List<Job> jobs = outSourcingJobMap.get(_outSourcingCorp);
                    if (jobs == null) {
                        jobs = new ArrayList<Job>();
                        outSourcingJobMap.put(_outSourcingCorp, jobs);
                    }
                    profitAndLoss += _outSourcingCorp.getOutSourcingMap().get(jobType).getRev();
                } else return false;
                break;
            case reject:
                rejectJobList.add(job);
                profitAndLoss += jobType.getRejectRev();
                break;
            default:
                throw new IllegalArgumentException("Unkown workflow type!" + job);
        }
        if (!allowAdd) return false;

        return true;
    }

    private boolean preOutSourcingAdd(Job job) {
        OutSourcingCorp osc = job.getOutSourcingCorp();
        JobType jobType = job.getJobType();
        Integer startTime = outSourcingCorpTimeMap.get(osc);
        startTime = startTime == null ? 0 : startTime;
        Integer jobTimeCost = osc.gettIimeCostByJobType(jobType);
        int endTime = startTime + jobTimeCost;
        if (endTime > worktime) return false;
        outSourcingCorpTimeMap.put(osc, endTime);
        return true;
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
            Integer currentCnt = this.jobType$count.get(key);
            currentCnt = currentCnt == null ? 0 : currentCnt;
            if (currentCnt <= entry.getValue()) {
                avaliable.add(key);
            }
        }
        return avaliable;
    }

    public Collection<JobTypeOutSourcingEntry> getAvaliableOutSourcingJobTypeList() {
        Collection<JobTypeOutSourcingEntry> avaliable = new ArrayList<JobTypeOutSourcingEntry>();
        for (Map.Entry<JobType, Integer> entry : AIServiceForROI.jobType$count.entrySet()) {
            JobType key = entry.getKey();
            Integer currentCnt = this.jobType$count.get(key);
            currentCnt = currentCnt == null ? 0 : currentCnt;
            if (currentCnt <= entry.getValue()) {
                List<OutSourcingCorp> corps = AIServiceForROI.jobType$outSourcingCorps.get(key);
                for (OutSourcingCorp osc : corps) {
                    avaliable.add(new JobTypeOutSourcingEntry(key, osc));
                }
            }
        }
        return avaliable;
    }

    public Job getJobByWorkflow(WorkflowType workflowType, Object excludeRandomItem) {
        Job job = null;
        switch (workflowType) {
            case selfDo:
                Collection<JobType> avaliableJobTypeList = getAvaliableJobTypeList();
                if (avaliableJobTypeList == null || avaliableJobTypeList.isEmpty()) return null;
                if (excludeRandomItem != null) {
                    avaliableJobTypeList.remove(excludeRandomItem);
                }
                if (avaliableJobTypeList == null || avaliableJobTypeList.isEmpty()) {
                    return null;
                }
                JobSelection js = new JobSelection(avaliableJobTypeList);
                job = js.getRandomJobByRoi().getInstance(workflowType);
                break;
            case outSourcing:
                Collection<JobTypeOutSourcingEntry> avaliableOutSourcingJobTypeList = getAvaliableOutSourcingJobTypeList();
                if (avaliableOutSourcingJobTypeList == null || avaliableOutSourcingJobTypeList.isEmpty()) {
                    return null;
                }
                if (excludeRandomItem != null) {
                    avaliableOutSourcingJobTypeList.remove(excludeRandomItem);
                }
                if (avaliableOutSourcingJobTypeList == null || avaliableOutSourcingJobTypeList.isEmpty()) {
                    return null;
                }
                OutSourcingSelection outSourcingSelection = new OutSourcingSelection(avaliableOutSourcingJobTypeList);
                JobTypeOutSourcingEntry jobTypeOutSourcingEntry = outSourcingSelection.getRandomJobByRoi();
                job = jobTypeOutSourcingEntry.getJobType().getInstance(workflowType);
                job.setOutSourcingCorp(jobTypeOutSourcingEntry.getOutSourcingCorp());
                break;
            default:
                throw new IllegalArgumentException("Unsupport workflowtype! workflowType:" + workflowType);
        }
        return job;
    }

    public String toString() {
        return this.getResultName() + " : " + this.getProfitAndLoss();
    }
}

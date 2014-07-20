package hzz2.domain;

import hzz2.constants.WorkAddResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ExecuteResult {

    private String               resultName;
    // 总损益
    private Integer              profitAndLoss         = 0;
    private Map<String, Job>     jobTypeName$job       = new HashMap<String, Job>();
    private List<WorkType>       avaliableWorkTypeList = new ArrayList<WorkType>();
    private Map<String, Integer> machine$startTime     = new HashMap<String, Integer>();
    private List<Job>            jobResult             = new ArrayList<Job>();

    public List<Job> getJobResult() {
        return jobResult;
    }

    public void setJobResult(List<Job> jobResult) {
        this.jobResult = jobResult;
    }

    public Integer getProfitAndLoss() {
        return profitAndLoss;
    }

    public ExecuteResult(Collection<WorkType> validWorkTypes) {
        this.avaliableWorkTypeList = new ArrayList<WorkType>(validWorkTypes);
    }

    public Map<String, Job> getJobTypeName$job() {
        return jobTypeName$job;
    }

    public Collection<WorkType> getValidWorkTypes() {
        return avaliableWorkTypeList;
    }

    public void setProfitAndLoss(Integer profitAndLoss) {
        this.profitAndLoss = profitAndLoss;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    /**
     * 添加一个任务进工作队列
     * 
     * @param job
     * @return true 表示添加成功，false表示无法添加了
     */
    public WorkAddResult addWorkType(Job job) {
        WorkType workType = job.getWorkType();
        if (jobTypeName$job.containsKey(workType.getJobTypeName())) {
            return WorkAddResult.exception;
        }
        WorkAddResult result = WorkAddResult.success;
        Map<String, Integer> _machine$startTime = new HashMap<String, Integer>();
        if (workType.getOurSourcingName() == null) {
            LinkedHashMap<String, Integer> machine$time = workType.getMachine$time();
            for (Entry<String, Integer> entry : machine$time.entrySet()) {
                String mName = entry.getKey();
                Integer startWorkDelay = machine$startTime.get(mName);
                if (startWorkDelay == null) {
                    machine$startTime.put(mName, 0);
                    startWorkDelay = 0;
                }
                // 拿到当前机器的可开工时间点，在对比以上当前工件上个机器的完成时间，取MAX作为开工时间
                Integer jobMachineTimeCost = entry.getValue();
                TimeEntry timeEntry = new TimeEntry();
                timeEntry.setStartTime(startWorkDelay);
                int endTime = startWorkDelay + jobMachineTimeCost;
                timeEntry.setEndTime(endTime);
                // 更新机器的工作计划
                _machine$startTime.put(mName, endTime);
                job.addWorkItem(mName, timeEntry);
            }
        }
        if (job.getRealTimeCost() <= workType.getMaxWait()) {
            result = WorkAddResult.success;
            machine$startTime.putAll(_machine$startTime);
            profitAndLoss += workType.getRev();
            removeAvaliable(workType, true);
            jobResult.add(job);
        } else {
            if (removeAvaliable(workType, false)) {
                profitAndLoss += workType.getRejectRev();
                job.setReject(true);
                removeAvaliable(workType, true);
                jobResult.add(job);
            }
        }
        return result;
    }

    protected boolean removeAvaliable(WorkType workType, boolean removeJobTypeAll) {
        Iterator<WorkType> iter = avaliableWorkTypeList.iterator();
        boolean needReject = true;
        while (iter.hasNext()) {
            WorkType _workType = iter.next();
            if (_workType.getJobTypeName().equals(workType.getJobTypeName())) {
                if (removeJobTypeAll) {
                    iter.remove();
                    needReject = false;
                } else if (_workType.getOurSourcingName().equals(workType.getOurSourcingName())) {
                    iter.remove();
                } else {
                    needReject = false;
                }
            }
        }
        return needReject;
    }

    public String toString() {
        return this.getResultName() + " : " + this.getProfitAndLoss();
    }
}

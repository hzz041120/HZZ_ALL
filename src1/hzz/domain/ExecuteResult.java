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
    // Ԥ�ڵĿ�ִ��ʱ��
    private Integer               worktime;
    // ������
    private Integer               profitAndLoss = 0;
    // ��ǰ��������ͺ�����
    private Map<JobType, Integer> jobType$count = new HashMap<JobType, Integer>();

    public Integer getProfitAndLoss() {
        return profitAndLoss;
    }

    public Map<JobType, Integer> getJobType$count() {
        return jobType$count;
    }

    // ִ�й���
    private List<Job> jobList = new ArrayList<Job>();
    // ��ʱ��Ϊά�ȵ������м�ڵ�������±�,���ڽ������
    private int       middlePoint;

    public ExecuteResult(int worktime) {
        this.worktime = worktime;
    }

    private boolean isAllowAddJob(Job job) {
        LinkedHashMap<Machine, Integer> machine$time = job.getJobType().getMachine$time();
        // ��һ̨�����ĵ�ǰ����ʱ���
        int preMachineDelay = 0;
        double middleTime = getWorktime() / 2d;
        for (Entry<Machine, Integer> entry : machine$time.entrySet()) {
            Machine m = entry.getKey();
            int startWorkDelay = m.getStartWorkDelay();
            // �õ���ǰ�����Ŀɿ���ʱ��㣬�ڶԱ����ϵ�ǰ�����ϸ����������ʱ�䣬ȡMAX��Ϊ����ʱ��
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
            // ���»����Ĺ����ƻ�
            m.setStartWorkDelay(endTime);
            if (startWorkDelay < middleTime && endTime >= middleTime) {
                // System.out.println("startWorkDelay: " + startWorkDelay + ", middleTime:" + middleTime + ", endTime:"
                // + endTime);
                // ֻҪ��һ�����������м�ʱ��ڵ��򽫵�ǰ�ڵ�����Ϊ�м�
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
     * ���һ���������������
     * 
     * @param job
     * @return true ��ʾ��ӳɹ���false��ʾ�޷������
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

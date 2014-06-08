package hzz.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class ExecuteResult {

    // yԤ�ڵĿ�ִ��ʱ��
    private Integer worktime;
    // ������
    private Integer profitAndLoss;

    public Integer getProfitAndLoss() {
        return profitAndLoss;
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
                return false;
            }
            preMachineDelay = endTime;
            timeEntry.setEndTime(endTime);
            // ���»����Ĺ����ƻ�
            m.setStartWorkDelay(endTime);
            if (startWorkDelay < middleTime && endTime >= middleTime) {
                setMiddlePoint(getJobList().size());
            }
            job.getWorkDetails().put(m, timeEntry);
            profitAndLoss += job.getJobType().getRev();
        }
        return true;
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

}

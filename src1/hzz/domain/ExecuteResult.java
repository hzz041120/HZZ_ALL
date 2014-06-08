package hzz.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class ExecuteResult {

    // yԤ�ڵĿ�ִ��ʱ��
    private Integer   worktime;
    // ��ǰ���������Ҫ�Ĺ���ʱ��
    private Integer   currentTimeCost;
    // ������
    private Integer   profitAndLoss;
    // ִ�й���
    private List<Job> jobList = new ArrayList<Job>();
    // ��ʱ��Ϊά�ȵ������м�ڵ�������±�,���ڽ������
    private double    middlePoint;

    public ExecuteResult(int worktime) {
        this.worktime = worktime;
    }

    private boolean isAllowAddJob(Job job) {
        LinkedHashMap<Machine, Integer> machine$time = job.getJobType().getMachine$time();
        // ��һ̨�����ĵ�ǰ����ʱ���
        int preMachineDelay = 0;
        double middleTime = worktime / 2d;
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
            if (endTime > worktime) {
                return false;
            }
            preMachineDelay = endTime;
            timeEntry.setEndTime(endTime);
            // ���»����Ĺ����ƻ�
            m.setStartWorkDelay(endTime);
            if (startWorkDelay < middleTime && endTime >= middleTime) {
                setMiddlePoint(jobList.size());
            }
            job.getWorkDetails().put(m, timeEntry);
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

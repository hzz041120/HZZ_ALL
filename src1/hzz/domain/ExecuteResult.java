package hzz.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class ExecuteResult {

    // ��ִ��ʱ��
    private Integer   timeCost;
    // ��ǰ���������Ҫ�Ĺ���ʱ��
    private Integer   currentTimeCost;
    // ������
    private Integer   profitAndLoss;
    // ִ�й���
    private List<Job> jobList = new ArrayList<Job>();
    // ��ʱ��Ϊά�ȵ������м�ڵ�������±�,���ڽ������
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
            //���»����Ĺ����ƻ�
            m.setStartWorkDelay(endTime);
            //FIXME �����м�ڵ�
        }
        return false;
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
}

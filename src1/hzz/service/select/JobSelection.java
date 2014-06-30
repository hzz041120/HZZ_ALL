package hzz.service.select;

import hzz.domain.JobType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * ���̶Ĳ�������������
 * 
 * @author hzz041120 2014��4��21�� ����12:15:27
 */
public class JobSelection {

    private double                randomArea;
    private Map<Integer, JobType> randomJobMark = new HashMap<Integer, JobType>();
    private int                   scale         = 100;
    private static final Random   random        = new Random();
    private Collection<JobType>   jobs;

    public JobSelection(Collection<JobType> jobs) {
        if (jobs == null || jobs.isEmpty()) throw new IllegalArgumentException("jobs can't be null!");
        this.jobs = new ArrayList<JobType>(jobs);
        for (JobType job : jobs) {
            randomArea += job.getRoi();
        }
        /* ����100% �����ʵ����� */
        int percent = 0;
        for (JobType job : jobs) {
            int newPercent = (int) (scale * (job.getRoi() / randomArea)) + percent;
            for (int i = percent; i < newPercent; i++) {
                randomJobMark.put(i, job);
            }
            percent = newPercent;
        }
        // ���ڲ��ܺܺõķ��侫�ȱ���������С������΢��������ȷ�Χ������
        scale = percent;
    }

    /**
     * ����ROIȨ�������ȡһ������
     * 
     * @return
     */
    public JobType getRandomJobByRoi() {
        int nextInt = random.nextInt(scale);
        // System.out.println(nextInt);
        return randomJobMark.get(nextInt);
    }

    public Collection<JobType> getJobs() {
        return jobs;
    }

    public static void main(String[] args) {
        List<JobType> jobs = new ArrayList<JobType>();
        jobs.add(JobType.newJobType("J1", 100, -9, 100, null));
        jobs.add(JobType.newJobType("J2", 100, -9, 50, null));
        jobs.add(JobType.newJobType("J2", 100, -9, 50, null));
        JobSelection js = new JobSelection(jobs);
        System.out.println(js.randomJobMark);
        // while (true) {
        // JobType res = js.getRandomJobByRoi();
        // if (res == null) {
        // System.out.println("===================>");
        // break;
        // }
        // }
    }

}

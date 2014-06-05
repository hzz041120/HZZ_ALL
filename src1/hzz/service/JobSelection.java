package hzz.service;

import hzz.domain.JobType;

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

    private double            randomArea;
    private Map<Integer, JobType> randomJobMark = new HashMap<Integer, JobType>();
    private int               scale         = 100;
    private Random            random        = new Random();

    public JobSelection(List<JobType> jobs) {
        for (JobType job : jobs) {
            randomArea += job.getRoi();
        }
        /* ����100% �����ʵ����� */
        int percent = 0;
        for (JobType job : jobs) {
            int newPercent = (int) (scale * (job.getRoi() / randomArea));
            for (int i = percent; i < newPercent; i++) {
                randomJobMark.put(i, job);
            }
            percent += newPercent;
        }
    }

    /**
     * ����ROIȨ�������ȡһ������
     * 
     * @return
     */
    public JobType getRandomJobByRoi() {
        return randomJobMark.get(random.nextInt(scale));
    }
}

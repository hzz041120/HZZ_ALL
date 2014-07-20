package hzz2.service.select;

import hzz2.domain.WorkType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * ���̶Ĳ�������������
 * 
 * @author hzz041120 2014��4��21�� ����12:15:27
 */
public class JobSelection {

    private double                 randomArea;
    private Map<Integer, WorkType> randomJobMark = new HashMap<Integer, WorkType>();
    private int                    scale         = 100;
    private static final Random    random        = new Random();

    public JobSelection(Collection<WorkType> workTypes) {
        if (workTypes == null || workTypes.size() == 0) {
            throw new IllegalArgumentException("jobs can't be null!");
        }
        for (WorkType work : workTypes) {
            randomArea += work.getRoi();
        }
        /* ����100% �����ʵ����� */
        int percent = 0;
        for (WorkType job : workTypes) {
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
    public WorkType getRandomJobByRoi() {
        int nextInt = random.nextInt(scale);
        // System.out.println(nextInt);
        return randomJobMark.get(nextInt);
    }

}

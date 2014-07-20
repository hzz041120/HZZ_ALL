package hzz2.service.select;

import hzz2.domain.WorkType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 轮盘赌博法随机任务产生
 * 
 * @author hzz041120 2014年4月21日 上午12:15:27
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
        /* 精度100% 可以适当调高 */
        int percent = 0;
        for (WorkType job : workTypes) {
            int newPercent = (int) (scale * (job.getRoi() / randomArea)) + percent;
            for (int i = percent; i < newPercent; i++) {
                randomJobMark.put(i, job);
            }
            percent = newPercent;
        }
        // 由于不能很好的分配精度比例，会有小数所以微调随机精度范围至整数
        scale = percent;
    }

    /**
     * 按照ROI权重随机获取一个工件
     * 
     * @return
     */
    public WorkType getRandomJobByRoi() {
        int nextInt = random.nextInt(scale);
        // System.out.println(nextInt);
        return randomJobMark.get(nextInt);
    }

}

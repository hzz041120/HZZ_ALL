package hzz.service;

import hzz.domain.JobType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 轮盘赌博法随机任务产生
 * 
 * @author hzz041120 2014年4月21日 上午12:15:27
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
        /* 精度100% 可以适当调高 */
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
     * 按照ROI权重随机获取一个工件
     * 
     * @return
     */
    public JobType getRandomJobByRoi() {
        return randomJobMark.get(random.nextInt(scale));
    }
}

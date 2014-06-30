package hzz.service.select;

import hzz.domain.JobType;

import java.util.ArrayList;
import java.util.Collection;
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
        /* 精度100% 可以适当调高 */
        int percent = 0;
        for (JobType job : jobs) {
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

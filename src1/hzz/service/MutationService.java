package hzz.service;

import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.JobType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MutationService {

    private static final Random random = new Random();

    public static List<ExecuteResult> mutation(List<ExecuteResult> resultList) {
        List<ExecuteResult> mutationResList = new ArrayList<ExecuteResult>(resultList.size());
        for (ExecuteResult execRes : resultList) {
            mutationResList.add(mutation(execRes));
        }
        return mutationResList;
    }

    private static ExecuteResult mutation(ExecuteResult execRes) {

        ExecuteResult mutationRes = new ExecuteResult(execRes.getWorktime());
        List<Job> jobList = execRes.getJobList();
        int mutationIndex = random.nextInt(jobList.size());
        Job job = jobList.get(mutationIndex);
        //获取除当前任务外的其他任务，做轮盘随机
        Collection<JobType> keySet = execRes.getAvaliableJobTypeList();
        JobType jobType = job.getJobType();
        keySet.remove(jobType);
        JobSelection jobSelection = new JobSelection(keySet);
        JobType newJobType = jobSelection.getRandomJobByRoi();
        jobList.set(mutationIndex, newJobType.getInstance());
        for (Job j : jobList) {
            if (!mutationRes.addJob(j)) {
                return execRes;
            }
        }
        return mutationRes;
    }
}

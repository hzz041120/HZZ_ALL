package hzz.service;

import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.JobType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MutationService {

    private static final Random random = new Random();

    public static List<ExecuteResult> mutation(List<ExecuteResult> resultList, JobSelection jobSelection) {
        List<ExecuteResult> mutationResList = new ArrayList<ExecuteResult>(resultList.size());
        for (ExecuteResult execRes : resultList) {
            mutationResList.add(mutation(execRes, jobSelection));
        }
        return mutationResList;
    }

    private static ExecuteResult mutation(ExecuteResult execRes, JobSelection jobSelection) {
        ExecuteResult mutationRes = new ExecuteResult(execRes.getWorktime());
        List<Job> jobList = execRes.getJobList();
        int mutationIndex = random.nextInt(jobList.size());
        Job job = jobList.get(mutationIndex);
        JobType jobType = jobSelection.getRandomJobByRoi();
        while (jobType.equals(job.getJobType())) {
            jobType = jobSelection.getRandomJobByRoi();
        }
        jobList.set(mutationIndex, jobType.getInstance());
        for (Job j : jobList) {
            if (!mutationRes.addJob(j)) {
                return execRes;
            }
        }
        return mutationRes;
    }
}

package hzz.service;

import hzz.constants.WorkflowType;
import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.JobType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MutationService {

    private static final Random random = new Random();

    public static List<ExecuteResult> mutation(List<ExecuteResult> resultList, WorkflowType workflowType) {
        List<ExecuteResult> mutationResList = new ArrayList<ExecuteResult>(resultList.size());
        for (ExecuteResult execRes : resultList) {
            mutationResList.add(mutation(execRes, workflowType));
        }
        return mutationResList;
    }

    private static ExecuteResult mutation(ExecuteResult execRes, WorkflowType workflowType) {

        ExecuteResult mutationRes = new ExecuteResult(execRes.getWorktime(), execRes);
        List<Job> jobList = execRes.getJobList();
        int mutationIndex = random.nextInt(jobList.size());
        Job job = jobList.get(mutationIndex);
        // 获取除当前任务外的其他任务，做轮盘随机
        Collection<JobType> keySet = execRes.getAvaliableJobTypeList();
        if (keySet == null || keySet.isEmpty()) return execRes;
        JobType jobType = job.getJobType();
        keySet.remove(jobType);
        if (keySet == null || keySet.isEmpty()) return execRes;
        jobList.set(mutationIndex, mutationRes.getJobByWorkflow(workflowType, null));
        for (Job j : jobList) {
            if (!mutationRes.addJob(j)) {
                return execRes;
            }
        }
        return mutationRes;
    }
}

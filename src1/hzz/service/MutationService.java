package hzz.service;

import hzz.constants.WorkflowType;
import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.JobType;
import hzz.domain.JobTypeOutSourcingEntry;
import hzz.domain.OutSourcingCorp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        switch (workflowType) {
            case selfDo:
                return selfDoMutation(execRes);
            case outSourcing:
                return outSourcingMutation(execRes);
            default:
                throw new IllegalArgumentException("Mutation Service can't support this workflowType:" + workflowType);
        }

    }

    private static ExecuteResult outSourcingMutation(ExecuteResult execRes) {
        ExecuteResult mutationRes = new ExecuteResult(execRes.getWorktime(), execRes);
        mutationRes.setOutSourcingJobMap(new HashMap<OutSourcingCorp, List<Job>>());
        Map<OutSourcingCorp, List<Job>> outSourcingJobMap = execRes.getOutSourcingJobMap();
        OutSourcingCorp osc = OutSourcingCorp.getCorpByRandom();
        List<Job> jobList = new ArrayList<Job>(outSourcingJobMap.get(osc));
        int mutationIndex = random.nextInt(jobList.size());
        Job job = jobList.get(mutationIndex);
        // 获取除当前任务外的其他任务，做轮盘随机
        JobType jobType = job.getJobType();
        JobTypeOutSourcingEntry jtose = new JobTypeOutSourcingEntry(jobType, osc);
        Job newjob = mutationRes.getJobByWorkflow(WorkflowType.outSourcing, jtose);
        if (newjob != null) {
            jobList.set(mutationIndex, newjob);
            outSourcingJobMap.put(osc, jobList);
        }
        for(List<Job> jList : outSourcingJobMap.values()) {
            for (Job j : jList) {
                if (!mutationRes.addJob(j)) {
                    return execRes;
                }
            }
        }
        return mutationRes;
    }

    private static ExecuteResult selfDoMutation(ExecuteResult execRes) {
        ExecuteResult mutationRes = new ExecuteResult(execRes.getWorktime(), execRes);
        List<Job> jobList = execRes.getJobList();
        if(jobList.size() == 0) return execRes;
        int mutationIndex = random.nextInt(jobList.size());
        Job job = jobList.get(mutationIndex);
        // 获取除当前任务外的其他任务，做轮盘随机
        Job newjob = mutationRes.getJobByWorkflow(WorkflowType.selfDo, job.getJobType());
        if (newjob == null) return execRes;
        jobList.set(mutationIndex, newjob);
        for (Job j : jobList) {
            if (!mutationRes.addJob(j)) {
                return execRes;
            }
        }
        return mutationRes;
    }
}

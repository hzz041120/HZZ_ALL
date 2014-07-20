package hzz2.service;

import hzz2.constants.WorkAddResult;
import hzz2.domain.ExecuteResult;
import hzz2.domain.Job;
import hzz2.domain.WorkType;
import hzz2.service.select.JobSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MutationService {

    private static final Random random = new Random();

    public static List<ExecuteResult> mutation(List<ExecuteResult> resultList) {
        List<ExecuteResult> mutationResList = new ArrayList<ExecuteResult>(resultList.size());
        for (ExecuteResult execRes : resultList) {
            mutationResList.add(mutationItem(execRes));
        }
        return mutationResList;
    }

    private static ExecuteResult mutationItem(ExecuteResult execRes) {
        ExecuteResult mutationRes = new ExecuteResult(AIServiceForROI.workTypeList);
        List<Job> jobList = execRes.getJobResult();
        if (jobList.size() == 0) return execRes;
        int mutationIndex = random.nextInt(jobList.size());
        Job job = jobList.get(mutationIndex);
        // 获取除当前任务外的其他任务，做轮盘随机
        String jobTypeName = job.getWorkType().getJobTypeName();
        List<WorkType> avaliableWorkTypeList = new ArrayList<WorkType>();
        for (WorkType workType : AIServiceForROI.workTypeList) {
            String ourSourcingName = workType.getOurSourcingName() == null ? "_null" : workType.getOurSourcingName();
            String ourSourcingName2 = job.getWorkType().getOurSourcingName() == null ? "_null" : job.getWorkType().getOurSourcingName();
            if (workType.getJobTypeName().equals(jobTypeName) && ourSourcingName.equals(ourSourcingName2)) {
                avaliableWorkTypeList.add(workType);
            }
        }
        JobSelection js = new JobSelection(avaliableWorkTypeList);
        WorkType newWorkType = js.getRandomJobByRoi();
        Job newJob = new Job();
        newJob.setWorkType(newWorkType);
        jobList.set(mutationIndex, newJob);
        for (Job j : jobList) {
            mutationRes.addWorkType(j);
        }
        fillRes(mutationRes);
        if (mutationRes.getProfitAndLoss() > execRes.getProfitAndLoss()) {
            return mutationRes;
        } else {
            return execRes;
        }
    }

    private static void fillRes(ExecuteResult mutationRes) {
        while (!mutationRes.getValidWorkTypes().isEmpty()) {
            JobSelection jobSelection = new JobSelection(mutationRes.getValidWorkTypes());
            // 如果工件数量已经达到要求则取消掉该类型工件的份额
            WorkType workType = jobSelection.getRandomJobByRoi();
            Job job = new Job();
            job.setWorkType(workType);
            if (WorkAddResult.over == mutationRes.addWorkType(job)) {
                break;
            }
        }
    }
}

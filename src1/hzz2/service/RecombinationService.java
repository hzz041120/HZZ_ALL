package hzz2.service;

import hzz2.constants.WorkAddResult;
import hzz2.domain.ExecuteResult;
import hzz2.domain.Job;
import hzz2.domain.WorkType;
import hzz2.service.select.JobSelection;
import hzz2.service.util.ExecuteResultComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交叉计算服务
 * 
 * @author hzz041120 2014年6月8日 下午10:46:53
 */
public class RecombinationService {

    // intermediate recombination
    public static List<ExecuteResult> doIntermediateRecobination(List<ExecuteResult> resultList) {
        // 进行相邻分组 可改进
        int i = 0;
        List<ExecuteResult> resList = new ArrayList<ExecuteResult>(resultList.size());
        while (i < resultList.size()) {
            ExecuteResult firstRes = resultList.get(i++);
            ExecuteResult secondRes = resultList.get(i++);
            List<ExecuteResult> crossResList = doCross(firstRes, secondRes);
            resList.addAll(crossResList);
        }
        return resList;
    }

    private static List<ExecuteResult> doCross(ExecuteResult firstRes, ExecuteResult secondRes) {
        List<ExecuteResult> res = new ArrayList<ExecuteResult>();
        firstRes.setResultName("firstRes");
        res.add(firstRes);
        secondRes.setResultName("secondRes");
        res.add(secondRes);
        ExecuteResult child1 = new ExecuteResult(AIServiceForROI.workTypeList);
        ExecuteResult child2 = new ExecuteResult(AIServiceForROI.workTypeList);

        Map<String, Job> tempFirstJobMap = new HashMap<String, Job>();
        for (Job job : firstRes.getJobResult()) {
            tempFirstJobMap.put(job.getWorkType().getJobTypeName(), job);
        }
        for (Job job : secondRes.getJobResult()) {
            String jobTypeName = job.getWorkType().getJobTypeName();
            Job newJob1 = new Job();
            Job job1 = tempFirstJobMap.get(jobTypeName);
            newJob1.setWorkType(job1.getWorkType());
            child1.addWorkType(newJob1);
            Job newJob2 = new Job();
            newJob2.setWorkType(job.getWorkType());
            child2.addWorkType(newJob2);
        }
        fillRes(child1);
        fillRes(child2);
        List<ExecuteResult> sortRes = sortByRevAndGetBest2(res);
        return sortRes;
    }

    private static void fillRes(ExecuteResult child1) {
        while (!child1.getValidWorkTypes().isEmpty()) {
            JobSelection jobSelection = new JobSelection(child1.getValidWorkTypes());
            // 如果工件数量已经达到要求则取消掉该类型工件的份额
            WorkType workType = jobSelection.getRandomJobByRoi();
            Job job = new Job();
            job.setWorkType(workType);
            if (WorkAddResult.over == child1.addWorkType(job)) {
                break;
            }
        }
    }

    private static List<ExecuteResult> sortByRevAndGetBest2(List<ExecuteResult> res) {
        Collections.sort(res, new ExecuteResultComparator());
        return res.subList(0, 2);
    }

    public static void main(String[] args) {
        int a = 10;// 0101
        int b = 5;// 0100
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
        System.out.println(a >> 1);
        System.out.println(b >> 1);
        System.out.println(a & 1);
        System.out.println(b & 1);
    }

}

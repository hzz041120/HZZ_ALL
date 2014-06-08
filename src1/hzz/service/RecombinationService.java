package hzz.service;

import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.util.ExecuteResultComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        List<ExecuteResult> resList = new ArrayList<ExecuteResult>();
        while (i < resList.size()) {
            ExecuteResult firstRes = resultList.get(i++);
            ExecuteResult secondRes = resultList.get(i++);
            List<ExecuteResult> crossResList = doCross(firstRes, secondRes);
            resList.addAll(crossResList);
        }
        return resList;
    }

    private static List<ExecuteResult> doCross(ExecuteResult firstRes, ExecuteResult secondRes) {
        List<ExecuteResult> res = new ArrayList<ExecuteResult>();
        res.add(firstRes);
        res.add(secondRes);
        int fmp = firstRes.getMiddlePoint();
        List<Job> fJobList = firstRes.getJobList();
        List<Job> f11 = fJobList.subList(0, fmp);
        List<Job> f12 = fJobList.subList(fmp, fJobList.size());

        int smp = secondRes.getMiddlePoint();
        List<Job> sJobList = secondRes.getJobList();
        List<Job> f21 = sJobList.subList(0, smp);
        List<Job> f22 = sJobList.subList(smp, fJobList.size());

        ExecuteResult child1 = getCrossResult(firstRes, f11, f21);
        if (child1 != null) res.add(child1);
        ExecuteResult child2 = getCrossResult(firstRes, f11, f22);
        if (child2 != null) res.add(child2);
        ExecuteResult child3 = getCrossResult(firstRes, f12, f21);
        if (child3 != null) res.add(child3);
        ExecuteResult child4 = getCrossResult(firstRes, f12, f21);
        if (child4 != null) res.add(child4);
        return sortByRevAndGetBest2(res);
    }

    private static List<ExecuteResult> sortByRevAndGetBest2(List<ExecuteResult> res) {
        Collections.sort(res, new ExecuteResultComparator());
        return res.subList(0, 2);
    }

    private static ExecuteResult getCrossResult(ExecuteResult firstRes, List<Job> x, List<Job> y) {
        ExecuteResult child = new ExecuteResult(firstRes.getWorktime());
        for (Job j : x) {
            if (!child.addJob(j)) {
                child = null;
                break;
            }
        }
        for (Job j : y) {
            if (!child.addJob(j)) {
                return null;
            }
        }
        return child;
    }
}

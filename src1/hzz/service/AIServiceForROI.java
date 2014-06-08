package hzz.service;

import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.JobType;
import hzz.domain.Machine;
import hzz.util.ExecuteResultComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ѱ����������
 * 
 * @author hzz041120 2014��4��20�� ����5:04:51
 */
public class AIServiceForROI {

    /** ��ʼ���� */
    // �ӹ���������
    public static int                   worktime      = 0;
    // �������ͼ���������
    public static Map<JobType, Integer> jobType$count = new HashMap<JobType, Integer>();
    // �����б�
    public static List<Machine>         machineList   = new ArrayList<Machine>();
    /** �㷨��ϸ������ */
    // ��ʼ���⼯��С ������ż�������ڽ��� �Ľ�������˵
    private int                         initResSize   = 5 * 2;
    // �Ŵ�����
    private int                         generation    = 10000;

    private JobSelection                jobSelection  = new JobSelection(jobType$count.keySet());

    /**
     * ��ʼ����������
     */
    public void init() {
        // FIXME ��ʼ��
    }

    /**
     * ���������ƻ�
     */
    public void process() {
        /** ������Ҽӹ��Ĳ�Ʒ��ˮ */
        ExecuteResult selfRes = findSelfJobList();
        /** �������ӹ��Ĳ�Ʒ��ˮ */
        ExecuteResult outSourcingRes = findOutSourcingJobList(selfRes);
        /** ��þܾ��ӹ��Ĳ�Ʒ��ˮ */
        ExecuteResult rejectRes = findRejectJobList(selfRes, outSourcingRes);
        /** ��ӡ�����ƻ��� */
        // FIXME
    }

    private ExecuteResult findRejectJobList(ExecuteResult selfRes, ExecuteResult outSourcingRes) {
        //FIXME
        return null;
    }

    private ExecuteResult findOutSourcingJobList(ExecuteResult selfRes) {
        //�����Բ���ʣ�๤�����ͺ�����
        Map<JobType, Integer> jobType$count = new HashMap<JobType, Integer>();
        //��������ôҪһ���Ŵ��㷨���� ��������򵥺ܶ� FIXME
        return null;
    }

    private ExecuteResult findSelfJobList() {
        /** ��ʼ���⼯�� */
        List<ExecuteResult> resultList = initOriginalResult(initResSize);
        for (int i = 0; i < generation; i++) {
            /** ���� */
            resultList = recombination(resultList);
            /** ���� */
            mutation(resultList);
        }
        return getBestResult(resultList);
    }

    /**
     * ִ�б��죬��ǰ���õ����������������
     * 
     * @param resultList
     * @return
     */
    private void mutation(List<ExecuteResult> resultList) {
        MutationService.mutation(resultList);
    }

    /**
     * ִ�н������
     * 
     * @param resultList
     * @return
     */
    private List<ExecuteResult> recombination(List<ExecuteResult> resultList) {
        return RecombinationService.doIntermediateRecobination(resultList);
    }

    private ExecuteResult getBestResult(List<ExecuteResult> result) {
        Collections.sort(result, new ExecuteResultComparator());
        return result.get(0);
    }

    /*
     * ������ʼ���⼯��
     * @param initResSize
     * @return
     */
    private List<ExecuteResult> initOriginalResult(int initResSize) {
        List<ExecuteResult> resList = new ArrayList<ExecuteResult>();
        for (int i = 0; i < initResSize; i++) {
            ExecuteResult res = new ExecuteResult(worktime);
            while (true) {
                //������������Ѿ��ﵽҪ����ȡ���������͹����ķݶ�
                JobType jobType = jobSelection.getRandomJobByRoi();
                Integer currentJobCount = res.getJobType$count().get(jobType);
                if (currentJobCount > jobType$count.get(jobType)) {
                    Collection<JobType> jobs = jobSelection.getJobs();
                    jobs.remove(jobType);
                    jobSelection = new JobSelection(jobs);
                    continue;
                }
                Job job = jobType.getInstance();
                if (!res.addJob(job)) {
                    break;
                }

            }
            resList.add(res);
        }
        return resList;
    }

}

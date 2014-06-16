package hzz.service;

import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.JobType;
import hzz.domain.Machine;
import hzz.domain.OutSourcingCorp;
import hzz.util.ExecuteResultComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Ѱ����������
 * 
 * @author hzz041120 2014��4��20�� ����5:04:51
 */
public class AIServiceForROI {

    /** ��ʼ���� */
    /** �ӹ��������� */
    public static int                           worktime                = 0;
    /** �������ͼ��������� */
    public static Map<JobType, Integer>         jobType$count           = new HashMap<JobType, Integer>();
    /** �������ͼ�������� */
    public static Map<JobType, OutSourcingCorp> jobType$outSourcingCorp = new HashMap<JobType, OutSourcingCorp>();
    /** �㷨��ϸ������ */
    /** ��ʼ���⼯��С ������ż�������ڽ��� �Ľ�������˵ */
    private int                                 initResSize             = 5 * 2;
    /** �Ŵ����� */
    private int                                 generation              = 10000;

    /**
     * ��ʼ����������
     */
    public void init() {
        worktime = 400;
        // ��ʼ��������
        jobType$count = new HashMap<JobType, Integer>();
        LinkedHashMap<Machine, Integer> j1Machine$time = new LinkedHashMap<Machine, Integer>();
        j1Machine$time.put(Machine.newMachine("m1"), 5);
        j1Machine$time.put(Machine.newMachine("m2"), 5);
        j1Machine$time.put(Machine.newMachine("m3"), 7);
        jobType$count.put(JobType.newJobType("J1", 78, -9, 53, j1Machine$time), 100);

        LinkedHashMap<Machine, Integer> j2Machine$time = new LinkedHashMap<Machine, Integer>();
        j2Machine$time.put(Machine.newMachine("m1"), 4);
        j2Machine$time.put(Machine.newMachine("m2"), 6);
        j2Machine$time.put(Machine.newMachine("m3"), 8);
        jobType$count.put(JobType.newJobType("J2", 95, -10, 61, j2Machine$time), 90);

        LinkedHashMap<Machine, Integer> j3Machine$time = new LinkedHashMap<Machine, Integer>();
        j3Machine$time.put(Machine.newMachine("m1"), 2);
        j3Machine$time.put(Machine.newMachine("m2"), 4);
        j3Machine$time.put(Machine.newMachine("m3"), 10);
        jobType$count.put(JobType.newJobType("J3", 102, -7, 43, j3Machine$time), 80);

        LinkedHashMap<Machine, Integer> j4Machine$time = new LinkedHashMap<Machine, Integer>();
        j4Machine$time.put(Machine.newMachine("m1"), 7);
        j4Machine$time.put(Machine.newMachine("m2"), 9);
        j4Machine$time.put(Machine.newMachine("m3"), 5);
        jobType$count.put(JobType.newJobType("J4", 89, -13, 67, j4Machine$time), 70);

        LinkedHashMap<Machine, Integer> j5Machine$time = new LinkedHashMap<Machine, Integer>();
        j5Machine$time.put(Machine.newMachine("m1"), 9);
        j5Machine$time.put(Machine.newMachine("m2"), 3);
        j5Machine$time.put(Machine.newMachine("m3"), 5);
        jobType$count.put(JobType.newJobType("J5", 93, -5, 55, j5Machine$time), 60);
        // ��ʼ������̱�

    }

    /**
     * ���������ƻ�
     */
    public void process() {
        /** FIXME ֻ���������Ҽӹ�����>���>�ܾ��ӹ��ĳ��� */
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
        // FIXME
        return null;
    }

    private ExecuteResult findOutSourcingJobList(ExecuteResult selfRes) {
        // �����Բ���ʣ�๤�����ͺ�����
        Map<JobType, Integer> jobType$count = new HashMap<JobType, Integer>();
        // ��������ôҪһ���Ŵ��㷨���� ��������򵥺ܶ� FIXME
        return null;
    }

    private ExecuteResult findSelfJobList() {
        //FIXME ʱ���㹻��ԣ��Ҫ���մ���ĸ�ROI������䷨����
        /** ��ʼ���⼯�� */
        List<ExecuteResult> resultList = initOriginalResult(initResSize);
        System.out.println("ProfitAndLoss: " + getBestResult(resultList).getProfitAndLoss());
        for (int i = 0; i < generation; i++) {
            /** ���� */
            resultList = recombination(resultList);
            System.out.println("ProfitAndLoss" + i + ": " + getBestResult(resultList).getProfitAndLoss());
            /** ���� */
            mutation(resultList);
            System.out.println("ProfitAndLoss" + i + ": " + getBestResult(resultList).getProfitAndLoss());
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
        JobSelection jobSelection = new JobSelection(jobType$count.keySet());
        List<ExecuteResult> resList = new ArrayList<ExecuteResult>();
        for (int i = 0; i < initResSize; i++) {
            ExecuteResult res = new ExecuteResult(worktime);
            System.out.println("Execute " + i + "Start init!");
            while (true) {
                // ������������Ѿ��ﵽҪ����ȡ���������͹����ķݶ�
                JobType jobType = jobSelection.getRandomJobByRoi();
                Integer currentJobCount = res.getJobType$count().get(jobType);
                if (currentJobCount == null) {
                    currentJobCount = 0;
                }
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

    public static void main(String[] args) {
        AIServiceForROI service = new AIServiceForROI();
        service.init();
        service.generation = 1000;
        service.initResSize = 10;
        
        service.process();
    }

}

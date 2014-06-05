package hzz.service;

import hzz.domain.JobType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/** ��ʼ��������� **/
public class JobEncode {

    /**
     * FFFF(FF){0,N}FFFFFF
     * 
     * @param jobList
     * @param limitDays
     * @return
     */
    public int[][] encode(List<JobType> jobList, int limitDays) {
        //��ȡ��������ʱ���������ٵ��Ǹ���������ֵ�б���
        int minTimeCost = getMinTimeCost(jobList);
        //���������ӹ�����������
        sortJobByTimeCost(jobList);
        List<List<JobType>> initRes = new ArrayList<List<JobType>>();
        Integer maxJobLength = limitDays/minTimeCost;
        // ��������������볤��
        for (int i = 0; i < jobList.size(); i++) {
            int rIndex = selection(JobList);
            jobList.get(rIndex);
           
            List<JobType> resi = new ArrayList<JobType>(limitDays / timeCost);
        }
        return null;
    }

    private int getMinTimeCost(List<JobType> jobList) {
        int minTimeCost = Integer.MAX_VALUE;
        for(JobType job : jobList) {
            if(minTimeCost > job.getTimeCost()) {
                minTimeCost = job.getTimeCost();
            }
        }
        return minTimeCost;
    }

    private void sortJobByTimeCost(List<JobType> jobList) {
        Collections.sort(jobList, new Comparator<JobType>() {

            public int compare(JobType o1, JobType o2) {
                return o1.getTimeCost() < o2.getTimeCost() ? 1 : 0;
            }
        });
    }
}

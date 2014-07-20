package hzz2.service.init;

import hzz2.constants.WorkAddResult;
import hzz2.domain.ExecuteResult;
import hzz2.domain.Job;
import hzz2.domain.WorkType;
import hzz2.service.AIServiceForROI;
import hzz2.service.select.JobSelection;

import java.util.ArrayList;
import java.util.List;

public class InitJobService {

    /*
     * ������ʼ���⼯��
     * @param initResSize
     * @return
     */
    public static List<ExecuteResult> initOriginalResult() {
        List<ExecuteResult> resList = new ArrayList<ExecuteResult>();
        for (int i = 0; i < AIServiceForROI.initResSize; i++) {
            ExecuteResult res = new ExecuteResult(AIServiceForROI.workTypeList);
            System.out.println("Execute " + i + "Start init!");
            while (res.getValidWorkTypes().size() > 0) {
                JobSelection jobSelection = new JobSelection(res.getValidWorkTypes());
                // ������������Ѿ��ﵽҪ����ȡ���������͹����ķݶ�
                WorkType workType = jobSelection.getRandomJobByRoi();
                Job job = new Job();
                job.setWorkType(workType);
                if (WorkAddResult.over == res.addWorkType(job)) {
                    break;
                }

            }
            System.out.println("Init " + i + " Rev:" + res.getProfitAndLoss());
            resList.add(res);
        }
        return resList;
    }
}

package hzz.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * ����
 * 
 * @author hzz041120 2014��4��6�� ����8:12:38
 */
public class Machine {

    // ��������
    private String                   machineName;
    /**
     * ���������ʱ��
     */
    private Map<JobType, JobExecuteInfo> jobExecMap = new HashMap<JobType, JobExecuteInfo>();

    public String getMachineName() {
        return machineName;
    }

    public Map<JobType, JobExecuteInfo> getJobExecMap() {
        return jobExecMap;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public void setJobExecMap(Map<JobType, JobExecuteInfo> jobExecMap) {
        this.jobExecMap = jobExecMap;
    }

}

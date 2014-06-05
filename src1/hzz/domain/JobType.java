package hzz.domain;


/**
 * 工件类型
 * 
 * @author hzz041120 2014年4月6日 下午8:12:02
 */
public class JobType {

    public JobType(Integer rev, Integer timeCost) {
        roi = rev / timeCost;
    }

    // 自主加工的利润
    private Integer rev;
    // 自主加工需要的时间
    private Integer timeCost;
    private double  roi;

    // 外包加工的时间
    // 外包加工的利润
    // 拒绝的利润

    public double getRoi() {
        return roi;
    }

    public Integer getRev() {
        return rev;
    }

    public Integer getTimeCost() {
        return timeCost;
    }
        
}

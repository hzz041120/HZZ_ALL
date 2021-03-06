package hzz.util;

import hzz.domain.ExecuteResult;

import java.util.Comparator;

public class ExecuteResultComparator implements Comparator<ExecuteResult>{

    public int compare(ExecuteResult o1, ExecuteResult o2) {
        if(o1.getProfitAndLoss() >= o2.getProfitAndLoss()) return -1;
        return 1;
    }

}

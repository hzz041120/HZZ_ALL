package hzz.domain;

public class JobTypeOutSourcingEntry {

    private JobType         jobType;
    private OutSourcingCorp outSourcingCorp;
    private String          hashName;

    public boolean equals(Object obj) {
        if (obj == null) return false;
        return hashName.equals(((JobTypeOutSourcingEntry) obj).hashName);
    }

    public int hashCode() {
        return hashName.hashCode();
    }

    public JobTypeOutSourcingEntry(JobType jobType, OutSourcingCorp outSourcingCorp) {
        this.jobType = jobType;
        this.outSourcingCorp = outSourcingCorp;
        hashName = jobType.getJobName() + "_______hzz_______" + outSourcingCorp.getOutSourcingName();
    }

    public JobType getJobType() {
        return jobType;
    }

    public OutSourcingCorp getOutSourcingCorp() {
        return outSourcingCorp;
    }

    public String toString() {
        return "{jobType: " + jobType.getJobName() + ", outSourcingCorp:" + outSourcingCorp.getOutSourcingName() + "}";
    }
}

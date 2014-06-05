package gantt;

public class Squence {
	public int JobNumber;
	public int MachineNum;
	public int StartTime;
	public int time;

	/**
	 * 一个单元
	 * 
	 * @param JobNumber
	 *            Job编号
	 * @param MachineNum
	 *            机床编号
	 * @param StartTime
	 *            开始时间
	 * @param time
	 *            加工所需时间
	 */
	public Squence(int JobNumber, int MachineNum, int StartTime, int time) {
		this.JobNumber = JobNumber;
		this.MachineNum = MachineNum;
		this.StartTime = StartTime;
		this.time = time;
	}

	public int getStartTime() {
		return StartTime;
	}

	public void setStartTime(int startTime) {
		StartTime = startTime;
	}

	public int getJobNumber() {
		return JobNumber;
	}

	public void setJobNumber(int jobNumber) {
		JobNumber = jobNumber;
	}

	public int getMachineNum() {
		return MachineNum;
	}

	public void setMachineNum(int machineNum) {
		MachineNum = machineNum;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

}

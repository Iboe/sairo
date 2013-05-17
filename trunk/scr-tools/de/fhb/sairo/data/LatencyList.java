package de.fhb.sairo.data;

import java.util.ArrayList;

public class LatencyList extends ArrayList<Latency> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long averageLatency=0;
	private long maxLatency=0;
	private long minLatency=0;

	@Override
	public boolean add(Latency arg0) {
		boolean addValue= super.add(arg0);
		return addValue;
	}

	private void calculateLatencyValues(){
		System.out.println("Calculate latency values");
		for (int i=0;i<this.size();i++){
		if(maxLatency<this.get(i).getTime()){
			maxLatency=this.get(i).getTime();
		}
		if(minLatency>this.get(i).getTime()){
			minLatency=this.get(i).getTime();
		}
		averageLatency = averageLatency + this.get(i).getTime();
		}
		averageLatency = averageLatency / this.size();
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<this.size();i++){
			sb.append(this.get(i).toString());
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	public long getAverageLatency() {
		calculateLatencyValues();
		return averageLatency;
	}

	public void setAverageLatency(long averageLatency) {
		this.averageLatency = averageLatency;
	}

	public long getMaxLatency() {
		calculateLatencyValues();
		return maxLatency;
	}

	public void setMaxLatency(long maxLatency) {
		this.maxLatency = maxLatency;
	}

	public long getMinLatency() {
		calculateLatencyValues();
		return minLatency;
	}

	public void setMinLatency(long minLatency) {
		this.minLatency = minLatency;
	}

}

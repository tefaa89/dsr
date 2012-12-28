package com.dces.instances;

public class EvaluationResults {
	private double evalAccuracy;
	private int tp;
	private int fp;
	private int tn;
	private int fn;
	
	public EvaluationResults() {
		
	}

	public double getEvalAccuracy() {
		return evalAccuracy;
	}

	public void setEvalAccuracy(double evalAccuracy) {
		this.evalAccuracy = evalAccuracy;
	}

	public int getTp() {
		return tp;
	}

	public void setTp(int tp) {
		this.tp = tp;
	}

	public int getFp() {
		return fp;
	}

	public void setFp(int fp) {
		this.fp = fp;
	}

	public int getTn() {
		return tn;
	}

	public void setTn(int tn) {
		this.tn = tn;
	}

	public int getFn() {
		return fn;
	}

	public void setFn(int fn) {
		this.fn = fn;
	}
}

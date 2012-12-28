package com.dces.classifier;

import com.dces.instances.DEInstances;

public class EvalClassifiers {
	private DEInstances deInstances;
	
	public EvalClassifiers(DEInstances deInstances) {
		this.deInstances = deInstances;
	}

	public DEInstances getNextDEInstances() {
		return deInstances;
	}

	public boolean hasNext() {
		return true;
	}

	public void build() {

	}
}

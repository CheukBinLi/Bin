package com.cheuks.bin.anythingtest.reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class queue<T> extends ReferenceQueue<T> {

	@Override
	public Reference<? extends T> poll() {
		return super.poll();
	}

}

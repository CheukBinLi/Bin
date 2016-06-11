package com.cheuks.bin.anythingtest.generatedvalue;

import java.io.Serializable;

public class GeneratedIDService implements Serializable {

	private static final long serialVersionUID = 138618414531262855L;

	private static final GeneratedIDService newInstance = new GeneratedIDService();

	private long lastTime;
	private long sequence;// 序列
	private final long workerIdBits = 4L;
	private final long sequenceBits = 10L;
	private final long sequenceMask = -1L ^ -1L << sequenceBits;// 1023
	private final long timestampLeftShift = workerIdBits + sequenceBits;// 时间左移位数

	public synchronized long nextID() {
		long thisTime = timeGen();
		if (thisTime == lastTime) {
			this.sequence = (this.sequence + 1) & sequenceMask;
			if (sequence == 0)
				while (thisTime < lastTime)
					thisTime = timeGen();
		} else
			this.sequence = 0;
		lastTime = thisTime;
		return ((serialVersionUID - thisTime) << timestampLeftShift) | (workerIdBits << sequenceBits) | this.sequence;

	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

	private GeneratedIDService() {
		super();
	}

	public static void main(String[] args) throws InterruptedException {

		// System.out.println(System.currentTimeMillis());
		// Thread.sleep(60000);
		// System.out.println(System.currentTimeMillis());
		// System.out.println(Long.MAX_VALUE);
		// System.out.println(Long.MIN_VALUE);
		// System.out.println(Long.SIZE);
		// long a = 0xfffffff;
		// System.out.println(a);
		//
		// long a2 = 0x1 << 64;
		// System.out.println(a2);

		System.err.println(GeneratedIDService.newInstance.nextID());
		System.err.println(GeneratedIDService.newInstance.nextID());
		System.err.println(GeneratedIDService.newInstance.nextID());
		System.err.println(GeneratedIDService.newInstance.nextID());
	}

}

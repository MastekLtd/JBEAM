/*
 * Copyright (c) 2014 Mastek Ltd. All rights reserved.
 * 
 * This file is part of JBEAM. JBEAM is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation.
 *
 * JBEAM is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for the specific language governing permissions and 
 * limitations.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 */
package com.stgmastek.birt.report.utils;

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractObjectPool<T> {

	private Stack<T> poolObjects = new Stack<T>();

	private Stack<T> usedObjects = new Stack<T>();

	private Lock lockPoolObjects = new ReentrantLock(true);

	private Condition lockPoolObjectCondition = lockPoolObjects.newCondition();

	protected abstract int getMaxPoolSize();

	public final T getObject() {
		lockPoolObjects.lock();
		try {
			if (poolObjects.isEmpty() && usedObjects.size() == getMaxPoolSize()) {
				try {
					lockPoolObjectCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if ((poolObjects.size() + usedObjects.size() < getMaxPoolSize())
					&& poolObjects.isEmpty()) {
				T newObjectToPool = createObject();
				poolObjects.push(newObjectToPool);
			}
			T peek = poolObjects.pop();
			usedObjects.push(peek);
			return peek;
		} finally {
			lockPoolObjects.unlock();
		}
	}

	public final void releaseObject(T object) {
		lockPoolObjects.lock();
		usedObjects.remove(object);
		poolObjects.add(0, object);
		lockPoolObjectCondition.signal();
		lockPoolObjects.unlock();
	}

	protected abstract T createObject();

	/*		private static class TestIntegerPool extends AbstractObjectPool<Integer> {

		@Override
		protected Integer createObject() {
			return new Integer((int) (Math.random() * 100));
		}

		@Override
		protected int getMaxPoolSize() {
			return 2;
		}

	}

public static void main(String[] args) throws InterruptedException {
		final TestIntegerPool tep = new TestIntegerPool();
		int count = 15;
		final List<Integer> pooledObject = Collections
				.synchronizedList(new ArrayList<Integer>());
		for (int i = 0; i <= count; i++) {
			new GetterThread<Integer>(i, tep, pooledObject).start();
		}
		int totalThreadsRelease = 0;
		do{  
			Thread.sleep(1000);
			for (int i = 0; i < pooledObject.size() ; i++) {
				new ReleaseThread<Integer>(pooledObject.get(i), tep).start();
				totalThreadsRelease++;
			}
		}while(totalThreadsRelease <= count + 1- tep.getMaxPoolSize());
	}

	private static class GetterThread<T> extends Thread {

		private AbstractObjectPool<T> pool;
		private List<T> st;

		public GetterThread(int tc, AbstractObjectPool<T> pool, List<T> st) {
			this.tc = tc;
			this.pool = pool;
			this.st = st;
		}

		private int tc;

		@Override
		public void run() {
			System.out.println("Before Getter thread : TC -  " + tc);
			T fourth = pool.getObject();
			System.out.println("After Getter thread : TC -  " + tc
					+ ", object : " + fourth);
			st.add(fourth);
		}
	}

	private static class ReleaseThread<T> extends Thread {

		private AbstractObjectPool<T> pool;

		private T objectToRelease;

		public ReleaseThread(T otr, AbstractObjectPool<T> pool) {
			this.objectToRelease = otr;
			this.pool = pool;
		}

		@Override
		public void run() {
			System.out.println("Before Release thread : " + objectToRelease);
			pool.releaseObject(objectToRelease);
			System.out.println("After Release thread : " + objectToRelease);
		}
	}*/

}

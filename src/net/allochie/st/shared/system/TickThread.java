package net.allochie.st.shared.system;

import java.util.ArrayList;

public class TickThread {

	private static class BeatThread extends Thread {
		private final boolean isServer;
		private boolean interrupt = false;

		private final ArrayList<IThink> thinkers = new ArrayList<IThink>();
		private final ArrayList<IThink> inThinkers = new ArrayList<IThink>();

		public BeatThread(boolean isServer) {
			this.isServer = isServer;
		}

		public void sendInterrupt() {
			interrupt = true;
		}

		public void addThinker(IThink thinker) {
			synchronized (inThinkers) {
				inThinkers.add(thinker);
			}
		}

		@Override
		public void run() {
			while (!interrupt && !isInterrupted()) {
				synchronized (inThinkers) {
					if (inThinkers.size() > 0) {
						for (IThink thinker : inThinkers)
							thinkers.add(thinker);
						inThinkers.clear();
					}
				}
				for (IThink thinker : thinkers)
					if (isServer)
						thinker.thinkServer();
					else
						thinker.thinkClient();
			}
		}
	}

	private BeatThread thread;

	public TickThread() {
	}

	public void startThread(boolean server) {
		if (thread != null && thread.isAlive())
			return;
		thread = new BeatThread(server);
		thread.start();
	}

	public void addThinker(IThink thinker) {
		thread.addThinker(thinker);
	}

	public void abort() {
		if (thread != null && thread.isAlive())
			thread.sendInterrupt();
	}
}

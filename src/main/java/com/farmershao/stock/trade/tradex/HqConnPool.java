package com.farmershao.stock.trade.tradex;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.farmershao.stock.trade.enums.ExchangeId;
import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.util.Conf;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.sun.jna.Native;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.ShortByReference;

/**
 * 初始化连接池
 * <p>
 * Created by kongkp on 2017/1/23.
 */
class HqConnPool implements Closeable {
	private static final Logger LOGGER = LoggerFactory.getLogger(HqConnPool.class);

	private static final int MAX_PER_SERVER = 158;

	private Map<Integer, ArrayBlockingQueue<Integer>> conns = new ConcurrentHashMap<>();
	private Map<Integer, Set<Integer>> loaned = new ConcurrentHashMap<>();
	private Map<Integer, Object> locks = new ConcurrentHashMap<>();

	private List<String> hosts = new ArrayList<>();
	private List<Short> ports = new ArrayList<>();
	private List<Integer> connSizes = new ArrayList<>();
	private int connsPerServer = 1;
	private TdxLibrary tdxLibrary;

	private static AtomicInteger serverLoop = new AtomicInteger(0);

	HqConnPool(TdxLibrary tdxLibrary) {
		this.tdxLibrary = tdxLibrary;
		restart();
	}

	HqConnPool(TdxLibrary tdxLibrary, int connsPerServer) {
		Preconditions.checkArgument(connsPerServer > 0, "connsPerServer必须大于0");
		Preconditions.checkArgument(connsPerServer <= MAX_PER_SERVER, "connsPerServer必须小于" + MAX_PER_SERVER);
		this.tdxLibrary = tdxLibrary;
		this.connsPerServer = connsPerServer;
		restart();
	}

	<T> T call(Apply<Integer, T> function) throws TradeException {
		int connId = this.borrow();
		try {
			for (int i = 0; i < hosts.size(); i++) {
				try {
					if (connId > 0) {
						return function.apply(connId);
					} else {
						// 没连接可用，通知monitor创建连接
						monitor.interrupt();
						connId = this.borrow(8000);
					}
				} catch (TradeException te) {
					te.printStackTrace();
					String msg = te.getMessage();
					if (msg != null
							&& (StringUtils.indexOf(msg, "请重新连接服务器") > -1 || StringUtils.indexOf(msg, "重连服务器") > -1)) {
						this.shit(connId);
						connId = this.borrow(8000);
						throw new TradeException("连接服务器超时，一般是不支持该接口！");
					}
				}
			}
			LOGGER.warn("暂无可用连接，等待0.2秒后重试！");
			try {
				Thread.sleep(200);
			} catch (Exception e) {
			}

			for (int i = 0; i < hosts.size(); i++) {
				try {
					if (connId > 0) {
						System.out.println("Use connecting id: " + connId);
						return function.apply(connId);
					} else {
						// 没连接可用，通知monitor创建连接
						monitor.interrupt();
						connId = this.borrow(8000);
					}
				} catch (TradeException te) {
					te.printStackTrace();
					String msg = te.getMessage();
					if (msg != null
							&& (StringUtils.indexOf(msg, "请重新连接服务器") > -1 || StringUtils.indexOf(msg, "重连服务器") > -1)) {
						this.shit(connId);
						connId = this.borrow(8000);
						throw new TradeException("连接服务器超时，一般是不支持该接口！");
					}
				}
			}
			LOGGER.warn("暂无可用连接，等待0.4秒后重试！");
			try {
				Thread.sleep(400);
			} catch (Exception e) {
			}

			for (int i = 0; i < hosts.size(); i++) {
				try {
					if (connId > 0) {
						System.out.println("Use connecting id: " + connId);
						return function.apply(connId);
					} else {
						// 没连接可用，通知monitor创建连接
						monitor.interrupt();
						connId = this.borrow(8000);
					}
				} catch (TradeException te) {
					te.printStackTrace();
					String msg = te.getMessage();
					if (msg != null
							&& (StringUtils.indexOf(msg, "请重新连接服务器") > -1 || StringUtils.indexOf(msg, "重连服务器") > -1)) {
						this.shit(connId);
						connId = this.borrow(8000);
						throw new TradeException("连接服务器超时，一般是不支持该接口！");
					}
				}
			}
			LOGGER.warn("暂无可用连接，等待0.6秒后重试！");
			try {
				Thread.sleep(600);
			} catch (Exception e) {
			}

			for (int i = 0; i < hosts.size(); i++) {
				try {
					if (connId > 0) {
						System.out.println("Use connecting id: " + connId);
						return function.apply(connId);
					} else {
						// 没连接可用，通知monitor创建连接
						monitor.interrupt();
						connId = this.borrow(8000);
					}
				} catch (TradeException te) {
					te.printStackTrace();
					String msg = te.getMessage();
					if (msg != null
							&& (StringUtils.indexOf(msg, "请重新连接服务器") > -1 || StringUtils.indexOf(msg, "重连服务器") > -1)) {
						this.shit(connId);
						connId = this.borrow(8000);
						throw new TradeException("连接服务器超时，一般是不支持该接口！");
					}
				}
			}
			LOGGER.warn("暂无可用连接，等待0.8秒后重试！");
			try {
				Thread.sleep(800);
			} catch (Exception e) {
			}

			for (int i = 0; i < hosts.size(); i++) {
				try {
					if (connId > 0) {
						System.out.println("Use connecting id: " + connId);
						return function.apply(connId);
					} else {
						// 没连接可用，通知monitor创建连接
						monitor.interrupt();
						connId = this.borrow(8000);
					}
				} catch (TradeException te) {
					te.printStackTrace();
					String msg = te.getMessage();
					if (msg != null
							&& (StringUtils.indexOf(msg, "请重新连接服务器") > -1 || StringUtils.indexOf(msg, "重连服务器") > -1)) {
						this.shit(connId);
						connId = this.borrow(8000);
						throw new TradeException("连接服务器超时，一般是不支持该接口！");
					}
				}
			}
			LOGGER.warn("暂无可用连接，等待1秒后重试！");
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}

			for (int i = 0; i < hosts.size(); i++) {
				try {
					if (connId > 0) {
						System.out.println("Use connecting id: " + connId);
						return function.apply(connId);
					} else {
						// 没连接可用，通知monitor创建连接
						monitor.interrupt();
						connId = this.borrow(8000);
					}
				} catch (TradeException te) {
					te.printStackTrace();
					String msg = te.getMessage();
					if (msg != null
							&& (StringUtils.indexOf(msg, "请重新连接服务器") > -1 || StringUtils.indexOf(msg, "重连服务器") > -1)) {
						this.shit(connId);
						connId = this.borrow(8000);
						throw new TradeException("连接服务器超时，一般是不支持该接口！");
					}
				}
			}
			throw new TradeException("暂无可用连接，请求失败");
		} finally {
			this.giveBack(connId);
		}
	}

	@FunctionalInterface
	interface Apply<T, R> {
		R apply(T t) throws TradeException;
	}

	private volatile MThread monitor;

	private class MThread extends Thread {
		public MThread(String name) {
			super(name);
		}

		public void run() {
			while (!closed) {
				int errs = checkConns();
				if (errs > 0) {
					createConns();
				} else {
					sleep(60000);
					int created = createConns();
					if (created == 0) {
						sleep(60000);
					}
				}
			}
			System.out.println("HqConnPool-Conn-Monitor exists.");
		}

		private void sleep(int time) {
			onOffLock.readLock().lock();
			try {
				if (!closed) {
					try {
						Thread.sleep(time);
					} catch (InterruptedException e) {
						System.out.println("Conn-Monitor 线程被唤醒");
					}
				}
			} finally {
				onOffLock.readLock().unlock();
			}
		}

		private int checkConns() {
			onOffLock.readLock().lock();
			try {
				AtomicInteger errs = new AtomicInteger(0);
				conns.forEach((sIdx, queue) -> {
					queue.forEach(connId -> {
						synchronized (getLock(connId)) {
							if (!isLoaned(connId) && !checkConnection(connId)) {
								queue.remove(connId);
								tdxLibrary.TdxHq_Disconnect(connId);
								errs.addAndGet(1);
							}
						}
					});
				});
				return errs.get();
			} finally {
				onOffLock.readLock().unlock();
			}
		}

		public void turnoff() {
			try {
				this.interrupt();
				this.join();
			} catch (InterruptedException ie) {
				ie.printStackTrace(System.out);
			}
		}
	}

	private void startMonitor() {
		monitor = new MThread("HqConnPool-Conn-Monitor");
		monitor.setDaemon(true);
		monitor.start();
	}

	// TODO 目前使用业务接口测试是否联通，后续底层提供的连接检测方法后替换该实现
	private boolean checkConnection(int connId) {
		byte[] errData = new byte[256];
		ShortByReference count = new ShortByReference((short) 0);
		tdxLibrary.TdxHq_GetSecurityCount(connId, ExchangeId.SZ.getByteId(), count, errData);
		String error = Native.toString(errData, "GBK");
		boolean connected = StringUtils.isBlank(error);
		System.out.println("Testing conn-" + connId + ": status:" + connected);
		return connected;
	}

	private int borrow() {
		return borrow(-1);
	}

	private int borrow(long timeout) {
		onOffLock.readLock().lock();
		try {
			if (!closed) {
				int servers = hosts.size();
				for (int i = 0; i < servers; i++) {
					int idx = Math.abs(serverLoop.getAndIncrement() % servers);
					Integer connId = pollConn(timeout, idx);
					if (connId != null) {
						synchronized (getLock(connId)) {
							loaned.get(idx).add(connId);
							return connId;
						}
					}
				}
			}
			return 0;
		} finally {
			onOffLock.readLock().unlock();
		}
	}

	private Integer pollConn(long timeout, int idx) {
		ArrayBlockingQueue<Integer> connsPerServer = conns.get(idx);
		Integer connId = null;
		try {
			connId = timeout > 0 ? connsPerServer.poll(timeout, TimeUnit.MICROSECONDS) : connsPerServer.poll();
		} catch (InterruptedException ie) {
			ie.printStackTrace(System.out);
		}
		return connId;
	}

	private void giveBack(int connId) {
		onOffLock.readLock().lock();
		try {
			if (!closed) {
				synchronized (getLock(connId)) {
					if (isLoaned(connId)) {
						loanedToConns(connId);
					}
				}
			}
		} finally {
			onOffLock.readLock().unlock();
		}
	}

	private void shit(int connId) {
		onOffLock.readLock().lock();
		try {
			if (!closed) {
				synchronized (getLock(connId)) {
					if (isLoaned(connId)) {
						removeFromLoaned(connId);
						tdxLibrary.TdxHq_Disconnect(connId);
						monitor.interrupt();
					}
				}
			}
		} finally {
			onOffLock.readLock().unlock();
		}
	}

	private Object getLock(int connId) {
		Object obj = locks.get(connId);
		if (obj == null) {
			synchronized (this) {
				obj = locks.get(connId);
				if (obj == null) {
					obj = new Object();
					locks.put(connId, obj);
				}
			}
		}
		return obj;
	}

	private volatile boolean closed = true;

	public boolean isClosed() {
		return closed;
	}

	private final ReentrantReadWriteLock onOffLock = new ReentrantReadWriteLock();

	public void close() {
		if (!closed) {
			closed = true;
			monitor.turnoff();
			onOffLock.writeLock().lock();
			try {
				conns.forEach((k, queue) -> {
					queue.forEach(connId -> {
						tdxLibrary.TdxHq_Disconnect(connId);
					});
					conns.remove(k);
				});
				loaned.forEach((k, set) -> {
					set.forEach(connId -> {
						tdxLibrary.TdxHq_Disconnect(connId);
					});
					conns.remove(k);
				});
				hosts.clear();
				ports.clear();
			} finally {
				onOffLock.writeLock().unlock();
			}
		}
	}

	public void restart() {
		close();
		initConfAndConns();
		startMonitor();
	}

	private void initConfAndConns() {
		initializeConf();
		if (hosts.size() == 0) {
			System.err.println("hq.server没配置或配置错误");
		} else {
			closed = false;
			createConns();
		}
	}

	private void initializeConf() {
		if (connsPerServer == 1) {
			String conns = StringUtils.trim(Conf.get("tdx.hq.connsPerServer"));
			if (StringUtils.isNumeric(conns)) {
				connsPerServer = Short.valueOf(conns);
			}
		}

		String server = Conf.get("tdx.hq.server");
		String[] ss = StringUtils.split(server, ",;| ");
		int count = 0;
		for (String s : ss) {
			String[] hostPort = StringUtils.split(s, ':');
			if (hostPort.length >= 2) {
				conns.put(count, new ArrayBlockingQueue<>(MAX_PER_SERVER));
				loaned.put(count, new ConcurrentSkipListSet<>());
				hosts.add(StringUtils.trim(hostPort[0]));
				ports.add(Short.valueOf(StringUtils.trim(hostPort[1])));
				if (hostPort.length >= 3 && StringUtils.isNumeric(hostPort[2])) {
					connSizes.add(Integer.valueOf(StringUtils.trim(hostPort[2])));
				} else {
					connSizes.add(connsPerServer);
				}
				count++;
			} else {
				System.err.println("hq.server(" + s + ")" + "配置错误,正确格式为: host:port[,host:port]");
			}
		}
	}

	private int createConns() {
		byte[] rltData = new byte[256];
		byte[] errData = new byte[256];
		int created = 0;
		for (int i = 0, len = hosts.size(); i < len; i++) {
			int exists = connsOfServer(i);
			int needed = connSizes.get(i);
			onOffLock.readLock().lock();
			try {
				if (!closed) {
					for (int j = 0, toCreate = (needed - exists); j < toCreate; j++) {
						int connId = doConnect(i, rltData, errData);
						if (connId > 0) {
							created++;
							ArrayBlockingQueue<Integer> connQueueOfServer = conns.get(i);
							connQueueOfServer.offer(connId);
						}
					}
				}
			} finally {
				onOffLock.readLock().unlock();
			}
		}
		return created;
	}

	private int connsOfServer(int server) {
		onOffLock.writeLock().lock();
		try {
			int cnt = conns.get(server).size();
			cnt += loaned.get(server).size();
			return cnt;
		} finally {
			onOffLock.writeLock().unlock();
		}
	}

	private int doConnect(int serverIdx, byte[] rltData, byte[] errData) {
		String host = hosts.get(serverIdx);
		short port = ports.get(serverIdx);
		int connId = tdxLibrary.TdxHq_Connect(host, port, rltData, errData);
		if (connId > 0) {
			System.out.println("连接行情服务器连接" + host + ":" + port + ")成功");
		} else {
			System.out.println("连接行情服务器(" + host + ":" + port + ")失败，再次尝试！");
			connId = tdxLibrary.TdxHq_Connect(host, port, rltData, errData);
			if (connId > 0) {
				System.out.println("连接行情服务器连接" + host + ":" + port + ")成功");
			} else {
				System.out.println("连接行情服务器(" + host + ":" + port + ")失败，再次尝试失败！");
			}
		}
		return connId;
	}

	private void removeFromLoaned(int connId) {
		loaned.forEach((k, v) -> {
			v.remove(connId);
		});
	}

	private void loanedToConns(int connId) {
		loaned.forEach((k, v) -> {
			if (v.contains(connId)) {
				v.remove(connId);
				conns.get(k).offer(connId);
			}
		});
	}

	private boolean isLoaned(int connId) {
		ByteByReference bbr = new ByteByReference((byte) 0);
		loaned.forEach((k, v) -> {
			if (v.contains(connId)) {
				bbr.setValue((byte) 1);
			}
		});
		return bbr.getValue() == 1;
	}

}

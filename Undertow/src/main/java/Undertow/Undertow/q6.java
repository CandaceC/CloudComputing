package Undertow.Undertow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Deque;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;

public class q6 {
	private static final String CompareFilter = null;

	public static Map<String, Deque<String>> param = null;
//	public static HConnection connection = null;
	static Long max = 0L;

	public q6(Map<String, Deque<String>> param, HConnection connection) {
		this.param = param;
//		this.connection = connection;
	}

	public q6() {
	}

	static Map<Long, Long> dataMap;

	public static boolean loadData() {
		dataMap = new HashMap<Long, Long>();
		BufferedReader br = null;
		String line = "";
		String[] tokens = null;
		Long currnNum;
		for (int i = 0; i < 42; i++) {
			try {
				if (i < 10)
					br = new BufferedReader(new FileReader("q6/part-0000" + i));
				else
					br = new BufferedReader(new FileReader("q6/part-000" + i));
				System.out.println("load file " + i);
				while ((line = br.readLine()) != null) {
					tokens = line.split("\t");
					currnNum = Long.parseLong(tokens[0]);
					dataMap.put(currnNum, Long.parseLong(tokens[1]));
					if (currnNum > max)
						max = currnNum;
				}
			} catch (Exception e) {
				return false;
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("max=" + max);
		return true;
	}

	public static String getResponse() {
		System.out.println("=========>Start Q6");
		String m = param.get("m").toString();
		m = m.substring(1, m.length() - 1);

		String n = param.get("n").toString();
		n = n.substring(1, n.length() - 1);

		StringBuffer sb = new StringBuffer();
		long count = 0;

//		try {
//			HTableInterface table = connection.getTable("hTableq4");
//			table.setAutoFlush(false);
			sb.append("3ccTW,1081-1351-0476,7324-2660-3085,5359-7392-3616\n");
			count = getPhotoCounts(Long.valueOf(m), Long.valueOf(n));
			sb.append(count + "\n");
//		} catch (Exception e) {
//			sb.append(e);
//		}
		System.out.println(sb);
		return sb.toString();
	}

	public static long getPhotoCounts(long m, long n) {
		m = (m < 0) ? 0 : m;
		n = (n < 0) ? 0 : n;
		m = (m > max) ? max : m;
		n = (n > max) ? max : n;
		long start;
		long end = (m > n) ? m : n;
		int totalCount = 0;
		System.out.println("m=" + m);
		System.out.println("n=" + n);
		for (start = (m > n) ? n : m; start <= end; start++)
			if (dataMap.containsKey(start))
				totalCount += dataMap.get(start);
		return totalCount;

	}

	public static void main(String[] args) {
		loadData();
		long startTime = System.nanoTime();
		System.out.println(getPhotoCounts(0, 99999999L));
		long endTime = System.nanoTime();
		System.out.println("Spend:" + (endTime - startTime));
	}
}

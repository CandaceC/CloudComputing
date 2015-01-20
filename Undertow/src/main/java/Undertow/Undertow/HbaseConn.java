package Undertow.Undertow;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

public class HbaseConn {
	
	Configuration config=null;
	private HConnection connection = null;
	
	public HbaseConn(){ 
		config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "localhost");
		config.set("hbase.zookeeper.property.clientPort","2181");

		try {
			 connection = HConnectionManager.createConnection(config);
			 } catch (ZooKeeperConnectionException ex) {
	            ex.printStackTrace();
	        }
    }
	
	public HConnection getConn(){
		System.out.println("Start connection..............");
		return connection;
	}
}

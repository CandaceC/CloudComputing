package Undertow.Undertow;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;




public class q2 {
	Map<String, Deque<String>> param=null;
	HConnection connection=null;
	public q2(Map<String, Deque<String>> param, HConnection connection){
		this.param=param;
		this.connection=connection;
	}
	
	public String getResponse(){
		System.out.println("=========>Start Q2");
		String userid=param.get("userid").toString();
		userid=userid.substring(1, userid.length()-1);
		
		String tweet_time=param.get("tweet_time").toString();
		tweet_time=tweet_time.substring(1, tweet_time.length()-1);
        
        StringBuffer sb = new StringBuffer("");
   
        sb.append("3ccTW,1081-1351-0476,7324-2660-3085,5359-7392-3616\n");

        try{
        	HTableInterface table = connection.getTable("hTableq2");
        	table.setAutoFlush(false);
        	
        	String row=userid+"|___|"+tweet_time;
        	System.out.println("key="+row);
        	Get g = new Get(Bytes.toBytes(row));
        	Result rs = table.get(g);  
        	
        	String splitString="";
        	for(KeyValue kv : rs.raw()){
                splitString=Bytes.toString(kv.getValue());
                //System.out.println(splitString);
                String[] result;
        		Pattern pattern = Pattern.compile(Pattern.quote("|___|"));
        		result = pattern.split(splitString);
//        		for(String token : result) {
//        		      System.out.println(token);
//        		} 
                sb.append(result[0]+":"+result[2]+":"+result[1]+"\n");
            }
        }catch(Exception e){
            sb.append(e);
            System.out.println(e);
        }
        //System.out.print("sb="+sb);
        return sb.toString();
    }
}

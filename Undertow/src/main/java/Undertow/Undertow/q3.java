package Undertow.Undertow;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;




public class q3 {
	Map<String, Deque<String>> param=null;
	HConnection connection=null;
	
	public q3(Map<String, Deque<String>> param, HConnection connection){
		this.param=param;
		this.connection=connection;
	}
	

	public String getResponse(){
		System.out.println("=========>Start Q3");
		String userid=param.get("userid").toString();
		userid=userid.substring(1, userid.length()-1);
        
        StringBuffer sb = new StringBuffer();

        sb.append("3ccTW,1081-1351-0476,7324-2660-3085,5359-7392-3616\n");
        
        try{
        	HTableInterface table = connection.getTable("hTableq3");
        	 table.setAutoFlush(false);


	        String row=userid;
	        Get g = new Get(Bytes.toBytes(row));
	        Result rs = table.get(g);  
	        table.setAutoFlush(false);
	        String splitString="";
	        for(KeyValue kv : rs.raw()){
	                splitString=Bytes.toString(kv.getValue());
	                System.out.println(splitString);
	                String[] result;
	        		Pattern pattern = Pattern.compile(Pattern.quote(","));
	        		result = pattern.split(splitString);
	        		for(String token : result) {
	        		      //System.out.println("in loop"+token);
	        		      sb.append(token+"\n");
	        		} 
	            }
        }catch(Exception e){
            sb.append(e);
            System.out.println(e);
        }
       // System.out.print("sb="+sb);
        return sb.toString();
    }
}

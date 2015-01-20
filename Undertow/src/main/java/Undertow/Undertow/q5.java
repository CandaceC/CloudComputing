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



public class q5 {
	Map<String, Deque<String>> param=null;
	HConnection connection=null;
	public q5(Map<String, Deque<String>> param, HConnection connection){
		this.param=param;
		this.connection=connection;
	}
   
   
	public String getResponse(){
		System.out.println("=========>Start Q5");
		String userid_a=param.get("m").toString();
		userid_a=userid_a.substring(1, userid_a.length()-1);
		
		String userid_b=param.get("n").toString();
		userid_b=userid_b.substring(1, userid_b.length()-1);
        
        StringBuffer sb = new StringBuffer();
        sb.append("3ccTW,1081-1351-0476,7324-2660-3085,5359-7392-3616\n");
        
        try{
	        HTableInterface table = connection.getTable("hTableq5");
	        table.setAutoFlush(false);

	       //System.out.println(userid_a+"_"+userid_b);
	       Get g_a = new Get(Bytes.toBytes(userid_a));
	       Get g_b = new Get(Bytes.toBytes(userid_b));
	       
	       Result rs_a = table.get(g_a);
	       Result rs_b = table.get(g_b); 
	       
	       String[] result_a = null;
	       String[] result_b=null;
	       
	       table.setAutoFlush(false);
	       
	       String splitString="";
	       String splitString_1="";
	       
	        for(KeyValue kv : rs_a.raw()){
	                splitString=Bytes.toString(kv.getValue());
	                System.out.println(splitString);
	        		Pattern pattern = Pattern.compile(Pattern.quote("_"));
	        		result_a = pattern.split(splitString);
            }
        
	        for(KeyValue kv : rs_b.raw()){
	    		splitString_1=Bytes.toString(kv.getValue());
	            System.out.println(splitString_1);
	            Pattern pattern = Pattern.compile(Pattern.quote("_"));
	            result_b = pattern.split(splitString_1);
	         }
       
        String winner, winner1, winner2, winner3;
        // total
        if  (Integer.valueOf(result_a[0]) > Integer.valueOf(result_b[0])){
            winner = userid_a;
        }     
        else if(Integer.valueOf(result_a[0]) < Integer.valueOf(result_b[0])){
            winner = userid_b;
        }
        else{
            winner = "x";
        }
        // WINNER FORMAT
        sb.append(userid_a + "\t" + userid_b + "\tWINNER\n");
        // score 1
        if  (Integer.valueOf(result_a[1]) > Integer.valueOf(result_b[1]))
            winner1 = userid_a;
        else if(Integer.valueOf(result_a[1]) < Integer.valueOf(result_b[1]))
            winner1 = userid_b;
        else
            winner1 = "x";
        sb.append(result_a[1] + "\t" + result_b[1] + "\t" + winner1 + "\n");

         
         // score 2
        if  (Integer.valueOf(result_a[2]) > Integer.valueOf(result_b[2]))
            winner2 = userid_a;
        else if(Integer.valueOf(result_a[2]) < Integer.valueOf(result_b[2]))
            winner2 = userid_b;
        else
            winner2 = "x";
        sb.append(result_a[2] + "\t" + result_b[2] + "\t" + winner2 + "\n");

        // score 3
        if  (Integer.valueOf(result_a[3]) > Integer.valueOf(result_b[3]))
            winner3 = userid_a;
        else if(Integer.valueOf(result_a[3]) < Integer.valueOf(result_b[3]))
            winner3 = userid_b;
        else
            winner3 = "x";
        sb.append(result_a[3] + "\t" + result_b[3] + "\t" + winner3 + "\n");
        sb.append(result_a[0] + "\t" + result_b[0] + "\t" + winner + "\n");
        }catch(Exception e){
            sb.append(e);
        }
        //System.out.print("sb="+sb);
        return sb.toString();
    }
}

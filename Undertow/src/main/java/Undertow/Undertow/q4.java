package Undertow.Undertow;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Hashtable;
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
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;




public class q4 {
	Map<String, Deque<String>> param=null;
	HConnection connection=null;
	public q4(Map<String, Deque<String>> param, HConnection connection){
		this.param=param;
		this.connection=connection;
	}
	
   
    public String getResponse(){
    	System.out.println("=========>Start Q4");
		String location=param.get("location").toString();
		location=location.substring(1, location.length()-1);
		
		String date=param.get("date").toString();
		date=date.substring(1, date.length()-1);
		
		String m=param.get("m").toString();
		m=m.substring(1, m.length()-1);
		
		String n=param.get("n").toString();
		n=n.substring(1, n.length()-1);
		
    	StringBuffer sb=new StringBuffer();

        try{
        HTableInterface table = connection.getTable("hTableq4");
        table.setAutoFlush(false);
        
        String row=date+"_"+location;

        System.out.println("key="+row);
       // Get g = new Get(Bytes.toBytes(row));
        
        byte[] prefix=Bytes.toBytes(row);
        Scan scan = new Scan(prefix);
        PrefixFilter prefixFilter = new PrefixFilter(prefix);
        scan.setFilter(prefixFilter);
        ResultScanner rs = table.getScanner(scan);
        
        String value="";
        String tmp="";
        String strRank="";
        int rank=0;
        
        sb.append("3ccTW,1081-1351-0476,7324-2660-3085,5359-7392-3616\n");
        
        Hashtable<Integer,String> valueSet=new Hashtable<Integer,String>();
        
        for (Result result : rs) {
            for(KeyValue keyValue : result.list()) {
                //System.out.println("Qualifier : " + keyValue.getKeyString() + " : Value : " + Bytes.toString(keyValue.getValue()));
                tmp=Bytes.toString(keyValue.getKey());
                System.out.println("key value -- tmp: "+tmp.trim());
                strRank=tmp.substring(tmp.lastIndexOf("_")+1).substring(0,7);
                //System.out.println("the length="+strRank.length());
                //System.out.println("rank:"+strRank);
                rank=Integer.valueOf(strRank);
                value=Bytes.toString(keyValue.getValue());
                String[] arrValue=value.split("_");
                value=arrValue[0]+":"+arrValue[1];
                valueSet.put(rank, value);
            }
        }
        
        for(int i=Integer.valueOf(m); i<=Integer.valueOf(n); i++){
        	if(!valueSet.get(i).isEmpty()){
        		
        		if(i!=Integer.valueOf(n)){
        			sb.append(valueSet.get(i)+"\n");
        		}else{
        			sb.append(valueSet.get(i));
        		}
        	}
        }

        }catch(Exception e){
            sb.append(e);
            System.out.println(e);
        }
        System.out.print("sb="+sb);
        return sb.toString();
    }
}

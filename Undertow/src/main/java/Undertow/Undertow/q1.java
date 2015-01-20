package Undertow.Undertow;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.math.BigInteger;
import java.text.SimpleDateFormat;



public class q1 {
	Map<String, Deque<String>> param=null;
	
	public q1(Map<String, Deque<String>> param){
		this.param=param;
	}

	public String getResponse(){
		System.out.println("=========>Start Q1");
	   	StringBuffer sb=new StringBuffer("");
        BigInteger pub_key=new BigInteger("6876766832351765396496377534476050002970857483815262918450355869850085167053394672634315391224052153");
        BigInteger numY=new BigInteger("0");
        String strKey=param.get("key").toString();
        strKey=strKey.substring(1, strKey.length()-1);
        //System.out.println(strKey.substring(1, strKey.length()-1));
        BigInteger key=new BigInteger(strKey);
        //cache
        ConcurrentHashMap myMap = new ConcurrentHashMap();
        if (myMap.isEmpty()){
            numY=key.divide(pub_key);
            myMap.put(key, numY);
        }
        else if (!myMap.containsKey(key)){
            numY=key.divide(pub_key);
            myMap.put(key, numY);
        }
        else
            numY = (BigInteger)myMap.get(key);
        System.out.println("q1 numY="+numY);
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sb=sb.append(numY+"\n");
        sb=sb.append("3ccTW,1081-1351-0476,7324-2660-3085,5359-7392-3616,"+date.format(new Date())+"\n");
        //System.out.println(sb);
        return sb.toString();
    }
}

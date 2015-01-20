import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
/**
 *
 * @author candace
 */
public class q2 extends HttpServlet {
    static Configuration config = HBaseConfiguration.create();
    static HConnection connection = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userid=request.getParameter("userid");
        String tweet_time=request.getParameter("tweet_time");
        response.setContentType("text/plain");
        
        //Start Database
        System.out.println("<----------------Start HB------------------->");
        PrintWriter out = response.getWriter();
        
       
        out.println("3ccTW,1081-1351-0476,7324-2660-3085,5359-7392-3616\n");
        System.out.println("Start connection..............");
        config.addResource("/home/hadoop/hbase/conf/hbase-site.xml");

        config.set("hbase.zookeeper.quorum", "ec2-54-172-187-21.compute-1.amazonaws.com");
        config.set("hbase.zookeeper.property.clientPort","2181");
        
        // HBaseAdmin.checkHBaseAvailable(config);        
        try {
            connection = HConnectionManager.createConnection(config);
            out.println("Connected");
        } catch (ZooKeeperConnectionException ex) {
            out.println("Check connected"+ex);
        }
//        try{
//        HBaseAdmin hbase = new HBaseAdmin(config);
//        HTableDescriptor desc = new HTableDescriptor("TEST");
//        HColumnDescriptor meta = new HColumnDescriptor("personal".getBytes());
//        HColumnDescriptor prefix = new HColumnDescriptor("account".getBytes());
//        desc.addFamily(meta);
//        desc.addFamily(prefix);
//        hbase.createTable(desc);
//        }catch(Exception e){
//            out.println(e);
//        }
        
           
         try {
             HBaseAdmin.checkHBaseAvailable(config);
             System.out.println("HBASE conneted");
         } catch (MasterNotRunningException e) {
        	 p("HBase is not running.");
             System.exit(1);
         }
         out.close();

	
	

        
        out.println("----End file----");
        try{
            HTable table = new HTable(config, "myLittleHBaseTable");
            Put p = new Put(Bytes.toBytes("myLittleRow"));
        }catch(Exception e){
            out.println("in hbase put: "+e);
            e.printStackTrace();
        }
       
        try{
             HTable table = new HTable(config,"hTable");
             Scan s = new Scan();
             ResultScanner ss = table.getScanner(s);
             for(Result r:ss){
                 for(KeyValue kv : r.raw()){
                    System.out.print(new String(kv.getRow()) + " ");
                    System.out.print(new String(kv.getFamily()) + ":");
                    System.out.print(new String(kv.getQualifier()) + " ");
                    System.out.print(kv.getTimestamp() + " ");
                    System.out.println(new String(kv.getValue()));
                 }
             }
        } catch (IOException e){
            e.printStackTrace();
        }
        
        
        StringBuffer sb = new StringBuffer();
        try{
        //HTableInterface table = connection.getTable("hTable");
        HTable table = new HTable(config,"hTable");
        out.println("HTable is created!\n");
//            
        //String time = "2014-03-21 15:14:46";
        //String id = "514914419";
        String row="i:";
        Get g = new Get(Bytes.toBytes(row));
        Result rs = table.get(g);  
//             
        for(KeyValue kv : rs.raw()){
                //sb.append(Bytes.toString(kv.getQualifier()));
               // sb.append(":");
                sb.append(Bytes.toString(kv.getValue()));
            }
        }catch(Exception e){
            sb.append(e);
            out.println(e);
        }
        
         out.close();
    }
        
    	 private static void p(String msg) {
	        System.out.println(msg);
	    }
}

package Undertow.Undertow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import org.apache.hadoop.hbase.client.HConnection;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.examples.UndertowExample;
import io.undertow.examples.servlet.MessageServlet;
import io.undertow.examples.servlet.ServletServer;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.util.Headers;
import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;
import static io.undertow.servlet.Servlets.servlet;
 
@UndertowExample("Servlet")
public class App {
    public static final String MYAPP = "/";

    public static void main(String[] args) {
//        try {

//            DeploymentInfo servletBuilder = deployment()
//                    .setClassLoader(ServletServer.class.getClassLoader())
//                    .setContextPath(MYAPP)
//                    .setDeploymentName("q3Tw3cc.war")
//                    .addServlets(
//                    		servlet("q2", q2H.class)
//                             .addMapping("/q2"),
//                             servlet("q4", q4H.class)
//                             .addMapping("/q4"),
////                    		servlet("entrance", entrance.class)
////                            .addMapping("/*"),
//                            servlet("MyServlet", MessageServlet.class)
//                                    .addInitParam("message", "MyServlet")
//                                   .addMapping("/myservlet")
//                                    );
//
//            DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
//            manager.deploy();
            System.out.println("Executing Undertow");
//            HttpHandler servletHandler = manager.start();
//            PathHandler path = Handlers.path(Handlers.redirect(MYAPP))
//                    .addPrefixPath(MYAPP, servletHandler);
//            Undertow server = Undertow.builder()
//            		//.addHttpListener(80,"ec2-54-174-29-54.compute-1.amazonaws.com")//entrance
//            		.addHttpListener(80,"ec2-54-174-72-115.compute-1.amazonaws.com")
//            		.setHandler(path)
//                    .setWorkerThreads(4096)
//					.setIoThreads(Runtime.getRuntime().availableProcessors() * 2)
//					.setServerOption(UndertowOptions.ALWAYS_SET_KEEP_ALIVE, false)
//					.setBufferSize(1024*16)
//                    .build();
            HbaseConn hconn=new HbaseConn();
            final HConnection connection=hconn.getConn();
            
            //start loading data of Q6
//            System.out.println("========Loading Q6 data========");
//            final q6 q6=new q6();
//            q6.loadData();
          //.addHttpListener(80, "ec2-54-174-72-115.compute-1.amazonaws.com 
            //.addHttpListener(80,"ec2-54-174-88-45.compute-1.amazonaws.com")
            Undertow server = Undertow.builder()      
            		.addHttpListener(80, "ec2-54-174-72-115.compute-1.amazonaws.com")
                    .setHandler(new HttpHandler() {
                        @Override
                        public void handleRequest(final HttpServerExchange exchange) throws Exception {
                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain; charset=UTF-8");
                            String URI=exchange.getRequestURI();
                            //System.out.println(exchange.getQueryString());
                            Map<String, Deque<String>> q  = exchange.getQueryParameters();
                            
                            switch (URI) {
                    			case "/q1":
                    				q1 q1=new q1(q);
                    				exchange.getResponseSender().send(q1.getResponse());
                    				break;
                    			case "/q2":
                    				q2 q2=new q2(q,connection);
                    				exchange.getResponseSender().send(q2.getResponse());
                    				break;
                    			case "/q3":
                    				q3 q3=new q3(q,connection);
                    				exchange.getResponseSender().send(q3.getResponse());
                    				break;
                    			case "/q4":
                    				q4 q4=new q4(q,connection);
                    				exchange.getResponseSender().send(q4.getResponse());
                    				break;
                    			case "/q5":
                    				q5 q5=new q5(q,connection);
                    				exchange.getResponseSender().send(q5.getResponse());
                    				break;
//                    			case "/q6":
//                    				q6.param=q;
//                    				exchange.getResponseSender().send(q6.getResponse());
//                    				break;
                            }
                            //exchange.getResponseSender().send("Hello World");
                        }
                    })
                    .setWorkerThreads(4096)
					.setIoThreads(Runtime.getRuntime().availableProcessors() * 2)
					.setServerOption(UndertowOptions.ALWAYS_SET_KEEP_ALIVE, false)
					.setBufferSize(1024*4)
                    .build();

            server.start();
//        } catch (ServletException e) {
//            throw new RuntimeException(e);
//        }
    }
    

}
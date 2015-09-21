package com.zhu;

import java.awt.TextArea;
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
  

import java.io.OutputStream;

import javax.swing.JTextArea;

import com.zhu.StressGUI.runinfo;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;  
import ch.ethz.ssh2.Session;  
import ch.ethz.ssh2.StreamGobbler; 

public class StressTesting {
	InputStream in;
	InputStream inerr;
	OutputStream out;
	JTextArea ConsoleArea;
    Connection conn;  
    Session ssh; 
	public StressTesting(JTextArea ConsoleArea){
		this.ConsoleArea=ConsoleArea;
	}
	  public  void runssh(runinfo runflag) {  
          
/*	        String hostname = "172.20.8.116"; 
	        String username = "root";  
	        String password = "ufsoft"; */
		    String hostname = "192.168.28.6";  
	        String username = "root";  
	        String password = "zhukai"; 
	        String line;
	        byte[] buff = new byte[8192];
	        int len;
	        //指明连接主机的IP地址  
	         conn = new Connection(hostname);  
	         ssh = null;  
	        try {  
	            //连接到主机  
	        	ConsoleArea.append("连接主机\n");
	            conn.connect();  
	            //使用用户名和密码校验  
	            boolean isconn = conn.authenticateWithPassword(username, password);  
	            if(!isconn){  
	                System.out.println("用户名称或者是密码不正确");  
	            }else{  
	                //System.out.println("已经连接OK");  
	                ssh = conn.openSession();
	                ssh.requestPTY("bash");
	                ssh.startShell();
	    			in = ssh.getStdout();
	    			inerr=ssh.getStderr();
	    			out = ssh.getStdin();
	                //使用多个命令用分号隔开   
	               //ssh.execCommand("ps -aux|grep nginx|awk  '{print $3 \" \t \" $4}'");
	               // ssh.execCommand("ls");
	    	
	    			//String comString=" ps -aux|grep nginx|awk  '{print $3 \" \t \" $4}'\n";
	    			String comString="ps \n";
	    			byte[] command=comString.getBytes();
	    			out.write(command);
	    			out.flush();
/*         			comString="ls\n";
	    			command=comString.getBytes();
	    			out.write(command);
	    			out.flush();*/
	                //只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常  
//	              ssh.execCommand("mkdir hb");  
	                //将屏幕上的文字全部打印出来  
	                InputStream  is = new StreamGobbler(in); 
	                InputStream  iserr = new StreamGobbler(inerr);  
	                BufferedReader brs = new BufferedReader(new InputStreamReader(is));  
	                while(true){  	                		    					
	    					line = brs.readLine();
	 	                    if(runflag.getRunflag()==false){  
	                        break;  
	                    }  
	                    ConsoleArea.append(line+"\n");
	                    ConsoleArea.selectAll();
	                   // System.out.println(line);  
   
	                }  
	                
		                
	                  
	            }  
	            //连接的Session和Connection对象都需要关闭  
	            if(in!=null&&out!=null&&ssh!=null&&conn!=null){ 
	            in.close();
	            out.close();
	            ssh.close();  
	            conn.close();  
	            }
	              
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	          
	    } 
	  public void close(){
          try {
        	if(in!=null&&out!=null&&ssh!=null&&conn!=null){  
			in.close();
			out.close();
			ssh.close();  
			conn.close(); 
        	}
  		} catch (IOException e) {
			// TODO Auto-generated catch block
  			System.out.println("关闭连接出错");
			e.printStackTrace();
		}
	  }
}

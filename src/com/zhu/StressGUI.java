package com.zhu;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.zhu.StressTesting;

public class StressGUI {
	private static String DEBUG="false";
	
	private  JButton StartTest;
	private  JButton StopTest;
	private  JButton CloseTest;
	private  JButton Clear;
	private  JTextArea consoleT;
	private  JFrame f;
	private Thread thread;
	runinfo runflag;
	final StressTesting ST;
	
	public StressGUI(){
		//设置按钮
		StartTest=new JButton("strat");
		StopTest=new JButton("stop");
		CloseTest=new JButton("close");
		Clear=new JButton("clear");
		
		consoleT=new JTextArea();
		runflag=new runinfo();
		ST=new StressTesting(consoleT);
		
		StopTest.setEnabled(false);
		
		// 创建Frame对象
		f = new JFrame();
		f.setLayout(new GridBagLayout());
		GridBagConstraints layoutcontrol = new GridBagConstraints();

		
		// 创建panel容器
		JPanel  loginpanel = new JPanel ();
		JPanel  controlpanel = new JPanel ();
		JScrollPane monitorpanel=new JScrollPane();
		
		// 设置登录panel
		loginpanel.setBackground(Color.blue);
		
		// 设置控制panel
		//controlpanel.setBounds(20, 300, 150, 50);
		controlpanel.add(StartTest);// 默认垂直居中，间距为5
		controlpanel.add(StopTest);
		controlpanel.add(Clear);
		controlpanel.add(CloseTest);
		controlpanel.setBackground(Color.black);
		
		//设置控制台panel
		
		monitorpanel.getViewport().add(consoleT);

		
		//添加三组界面模块
		layoutcontrol.fill=GridBagConstraints.BOTH;
		layoutcontrol.gridx=0;
		layoutcontrol.gridy=0;
		layoutcontrol.weightx =1;
		layoutcontrol.weighty=1;
		layoutcontrol.gridheight=1;
		f.add(loginpanel,layoutcontrol);	
		
		layoutcontrol.fill=GridBagConstraints.BOTH;
		layoutcontrol.gridx=0;
		layoutcontrol.gridy=1;
		layoutcontrol.weightx =1;
		layoutcontrol.weighty=1;
		layoutcontrol.gridheight=1;
		f.add(controlpanel,layoutcontrol);	
		
		layoutcontrol.fill=GridBagConstraints.BOTH;
		layoutcontrol.gridx=0;
		layoutcontrol.gridy=2;
		layoutcontrol.weightx =1;
		layoutcontrol.weighty=1;
		layoutcontrol.gridheight=0;
		f.add(monitorpanel,layoutcontrol);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		// 设置Frame
		f.setSize(800, 600);
		f.setLocationRelativeTo(null);
		f.setTitle("nginx图片压力测试");
		f.setResizable(false);
		f.setVisible(true);
		
		//设置监听器
		//start
		StartTest.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				StopTest.setEnabled(true);
				StartTest.setEnabled(false);
				 thread=new Thread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						runflag.setRunflag(true);
						ST.runssh(runflag);				    	
				    	if(DEBUG=="true")
				         System.out.println("start testing");
					}});
				thread.start();

				
			}
			
		});
		
		
		//close
		CloseTest.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
	            f.setVisible(false);         //设置窗口f不可见
	            f.dispose();            //释放窗口及其子组件的屏幕资源
	            System.exit(0);
			}
			
		});
		
		//stop
		StopTest.addActionListener(new ActionListener(){


			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(DEBUG=="true")
				System.out.println("stop testing");
				runflag.setRunflag(false);
				StopTest.setEnabled(false);
				StartTest.setEnabled(true);
				ST.close();
				
			}
			
		});
		
		//clear
		Clear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				consoleT.setText("");
			}
			
		});
		

	}
	
	
	
	public static void main(String[] args) {
		StressGUI sgui=new StressGUI();

	}
	
	public class runinfo{
		private volatile boolean runflag = false;
		
		public runinfo(){
			runflag=false;
		}

		public boolean getRunflag() {
			return runflag;
		}

		public void setRunflag(boolean runflag) {
			this.runflag = runflag;
		} 
	}

	
}



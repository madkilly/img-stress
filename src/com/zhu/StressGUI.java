package com.zhu;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.zhu.StressTesting;

public class StressGUI implements ActionListener{
	
	private  Button StartTest;
	private  Button StopTest;
	private  Button CloseTest;
	private  TextArea consoleT;
	private  Frame f;
	
	public StressGUI(){
		//按钮
		StartTest=new Button("strat");
		StopTest=new Button("stop");
		CloseTest=new Button("close");
		//监听器
		StartTest.addActionListener(this);
		CloseTest.addActionListener(this);
		
		// 创建Frame对象
		f = new Frame();
		f.setLayout(null);
		// 创建panel容器
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		
		p1.setBounds(20, 300, 150, 50);
		p1.add(StartTest);// 默认垂直居中，间距为5
		p1.add(StopTest);
		p1.add(CloseTest);
		
		//创建控制台显示块
		consoleT=new TextArea();
		//consoleT.setBackground(Color.black);
		p2.setLayout(new BorderLayout());
		p2.add(consoleT);
		p2.setBounds(20, 350, 700, 240);		
		f.add(p1);	
		f.add(p2);	
		// 设置Frame标题
		f.setTitle("nginx图片压力测试");
		f.setSize(800, 600);
		f.setResizable(false);
		f.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		StressGUI sgui=new StressGUI();

	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if (e.getSource()==StartTest) {
	    	StressTesting ST=new StressTesting();
	    	ST.runssh(consoleT);
	         //System.out.println("start testing");
		}
		else if(e.getSource()==StopTest){
			System.out.println("stop testing");
		}
		else if (e.getSource()==CloseTest) {		
            f.setVisible(false);         //设置窗口f不可见
            f.dispose();            //释放窗口及其子组件的屏幕资源
            System.exit(0);
			//System.out.println("close testing");
		}
	}
	
}


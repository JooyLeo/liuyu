package com.yychatserver.controller;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;

import com.yychat.model.Message;
import com.yychat.model.User;
public class StartServer {
	public static HashMap hmSocket=new HashMap<String,Socket>();
 ServerSocket ss;
 Socket s;
 String userName;
 String passWord;
 
 public StartServer(){
  try{//�����쳣
  ss=new ServerSocket(3456);
  System.out.println("�������Ѿ�����������3456�˿�");//1024���ϵĶ˿�
  while(true){//?Thread���߳�
	  Socket s=ss.accept();//���տͻ�����������
	  System.out.println("���ӳɹ�:"+s);
	  
	  
	  //����user����
	  ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
	  User user=(User)ois.readObject();
	  userName=user.getUserName();
	  passWord=user.getPassWord();
	  System.out.println(userName);
	  System.out.println(passWord);
	  
	  //ʵ��������֤����
	  Message mess=new Message();
	  mess.setSender("Server");
	  mess.setReceiver(userName);
	  if(passWord.equals("123456")){//����
	   //���߿ͻ���������֤ͨ������Ϣ�����Դ���һ��Massage��
	   mess.setMessageType(Message.message_LoginSuccess);//"1"Ϊ��֤ͨ��
	  }else{
	   mess.setMessageType(Message.message_LoginFailure);//"0"Ϊ��֤ʧ��
	  }
	  ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
	  oos.writeObject(mess);
	   
	  //����������Ϣ�����������ԣ�Ӧ���½�һ�������߳�
	  if(passWord.equals("123456")){
		  hmSocket.put(userName,s);
		  new ServerReceiverThread(s).start();//����
	  }
	  
  }
 
 }catch (IOException e){
  e.printStackTrace();//�����쳣
 }catch (ClassNotFoundException e){
  e.printStackTrace();
 }
  }
}
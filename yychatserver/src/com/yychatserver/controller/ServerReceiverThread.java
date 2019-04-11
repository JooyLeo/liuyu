package com.yychatserver.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Set;

import com.yychat.model.Message;

public class ServerReceiverThread extends Thread{
	Socket s;
	public ServerReceiverThread(Socket s){//s是与发送者相对应的服务器Socket
		this.s=s;
		
	}
	public void run(){
		ObjectInputStream ois;
		ObjectOutputStream oos;
		Message mess;
		while(true){
		try {
			ois = new ObjectInputStream(s.getInputStream());
			mess=(Message)ois.readObject();//接收聊天消息,线程阻塞
			System.out.println(mess.getSender()+"对"+mess.getReceiver()+"说"+mess.getContent());
			
			if(mess.getMessageType().equals(Message.message_Common)){
				Socket s1=(Socket)StartServer.hmSocket.get(mess.getReceiver());//得到与接收者相对应的服务器Socket对象
				oos=new ObjectOutputStream(s1.getOutputStream());
				oos.writeObject(mess);//转发聊天信息
			}
			//第2步：服务器接收到该请求后发送在线好友消息（类型：message_OnlineFriend)
			//if(mess.getMessageType().equals(Message.message_RequestOnlineFriend)){
				////拿到全部在线好友的名字
				//Set friendSet=StartServer.hmSocket.keySet();//键值对
				//String friendName;
				//String friendString=" ";
				//while(it.hasNest()){
					//friendName=(String)it.next();
					//if(!friendName.equals(mess.getSender()))
						//friendString=friendString+friendName+" ";//为什么用空格？	
				//}	
				//System.out.println("全部好友的名字："+friendString);
			//}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		}
	}
}

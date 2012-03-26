package br.com.fiap.scan;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class UDPClient {
	public static void main(String args[]) throws Exception {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		clientSocket.setSoTimeout(100);
		List<String> portasReposta = new ArrayList<String>();
		for(int k=1; k<=30; k++){
			InetAddress IPAddress = InetAddress.getByName("10.1.25."+k);
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			String sentence = inFromUser.readLine();
			sendData = sentence.getBytes();
			for(int i = 1; i <= 65536; i++ ){
				
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, IPAddress, i);
				clientSocket.send(sendPacket);
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				
				try{
					clientSocket.receive(receivePacket);
					String modifiedSentence = new String(receivePacket.getData());
					System.out.println("Número da porta: "+i);
					System.out.println("FROM SERVER:" + modifiedSentence);
					portasReposta.add("Porta: "+i+"Host: 10.1.25."+k);
				}
				catch(SocketTimeoutException e){
					System.out.println("Tempo de espera atingido Porta: "+i+" Host: 10.1.25."+k);
				}
				
				
			}
		}
		clientSocket.close();
		
		for (String string : portasReposta) {
			System.out.println(string);
		}
	}
}
/*
 * 1581
 * 7587
 */


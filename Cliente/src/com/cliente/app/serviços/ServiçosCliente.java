package com.cliente.app.serviços;

import com.mensagem.app.Mensagem;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServiçosCliente {

	private Socket socket;
	private ObjectOutputStream output;

	public Socket conectarAoServidor(String ip, int porta) {

		try {
			//Conectar o cliente realiza conexão no endereço ip e na porta em que o servidor está rodando
			socket = new Socket(ip, porta);
			
			//Vincula o output do servidor ao output do socket
			output = new ObjectOutputStream(socket.getOutputStream());

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return null;
			
		}

		return socket;
	}

	public void enviarMensagem(Mensagem mensagem){

		try {
			
			//Escreve no output do servidor
			output.writeObject(mensagem);

		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

}

package com.servidor.app.serviços;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mensagem.app.Mensagem;
import com.mensagem.app.Mensagem.Action;

public class ServiçosServidor {

	private ServerSocket serverSocket;
	private Socket socket;
	private Map<String, ObjectOutputStream> mapOnlines = new HashMap<String, ObjectOutputStream>();
	private String historicoDeMensagens = "";

	public ServiçosServidor(int porta){

		//iniciar o servidor
		iniciarServidor(porta);

	}

	private void iniciarServidor(int porta){

		try {

			serverSocket = new ServerSocket(porta);
			System.out.println("testando");

			while(true){

				System.out.println("Loop no aguardando no serverSocket.accept() porta: " + porta);

				socket = serverSocket.accept();

				System.out.println("Novo socket aceito!");

				new Thread(new ListenerSocket(socket)).start();
				System.out.println("Nova thread criada para ouvir o novo socket");

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class ListenerSocket implements Runnable{

		private ObjectOutputStream output;
		private ObjectInputStream input;

		public ListenerSocket(Socket socket) {
			// TODO Auto-generated constructor stub

			try {

				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			Mensagem mensagem;
			try {

				while((mensagem = (Mensagem) input.readObject()) != null){
					System.out.println("\n \n Loops thread servidor: ação: " + mensagem.getAction());
					System.out.println("Destino: "+ mensagem.getDestinatario());
					System.out.println("Remetente: " + mensagem.getRemetente());
					Action action = mensagem.getAction();

					if(action.equals(Action.SolicitarConexão)){
						System.out.println("solicitar conexão servidor");
						solicitarConexão(mensagem);

					} else {

						if(action.equals(Action.AceitarConexão)){



						} else {

							if(action.equals(Action.RecusarConexão)){



							} else {

								if(action.equals(Action.Desconectar)){

									removeUsuarioDaLista(mensagem);
									System.out.println("removeu o usuario: "+ mensagem.getRemetente() + " do chat");

								} else {

									if(action.equals(Action.MensagemAll)){

										enviarMensagemNoChatParaTodos(mensagem);

									} else {

										if(action.equals(Action.MensagemPrivada)){

											enviarMensagemNoChatPrivado(mensagem);

										} else {

											if(action.equals(Action.ArquivoAll)){

												enviarArquivoParaTodos(mensagem);

											} else {

												if(action.equals(Action.ArquivoPrivado)){

													enviarArquivoPrivado(mensagem);

												} else {

													if(action.equals(Action.ListaDeUsuariosOnline)){



													}

												}

											}

										}

									}

								}

							}

						}

					}

				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		private void solicitarConexão(Mensagem mensagem){

			if(mapOnlines.size() == 0){

				aceitarUsuarioNoServidor(mensagem);

			} else {

				if(mapOnlines.containsKey(mensagem.getRemetente())){

					recusarUsuarioNoServidor(mensagem);

				} else {

					aceitarUsuarioNoServidor(mensagem);

				}

			}

		}

		private void aceitarUsuarioNoServidor(Mensagem mensagem){
			
			mensagem.setHistoricoDeMensagens(carregarHistorico());
			
			adicionaUsuarioNaLista(mensagem);
			mensagem.setAction(Action.AceitarConexão);
			mensagem.setDestinatario(mensagem.getRemetente());
			mensagem.setRemetente("Servidor");
			enviaMensagem(mensagem, output);

			enviarListaDeUsuarios();

		}

		private void recusarUsuarioNoServidor(Mensagem mensagem){

			mensagem.setAction(Action.RecusarConexão);
			mensagem.setDestinatario(mensagem.getRemetente());
			mensagem.setRemetente("Servidor");
			enviaMensagem(mensagem, output);

		}

		private void adicionaUsuarioNaLista(Mensagem mensagem){
			
			System.out.println("Adicionando novo usuario na lista, nome do usuario: " + mensagem.getRemetente());
			mapOnlines.put(mensagem.getRemetente(), output);

		}

		private void removeUsuarioDaLista(Mensagem mensagem){
			
			System.out.println("removendo o usuario "+ mensagem.getRemetente() + " da lista de usuarios online");
			mapOnlines.remove(mensagem.getRemetente(), output);
			enviarListaDeUsuarios();

		}

		private void enviaMensagem(Mensagem mensagem, ObjectOutputStream output){

			try {

				output.writeObject(mensagem);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		private void enviarListaDeUsuarios(){

			Mensagem mensagem = new Mensagem();
			mensagem.setAction(Action.ListaDeUsuariosOnline);
			mensagem.setRemetente("Servidor");
			mensagem.setDestinatario("All");
			mensagem.setListaUsuariosOnlines(criaListaUsuarios());

			for(Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()){

				enviaMensagem(mensagem, kv.getValue());

			}

		}

		private Set<String> criaListaUsuarios(){

			Set<String> listaUsuarios = new HashSet<String>();
			listaUsuarios.add("All");
			for(Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()){
				System.out.println(kv.getKey().toString());
				listaUsuarios.add(kv.getKey().toString());

			}


			return listaUsuarios;

		}

		private void enviarMensagemNoChatPrivado(Mensagem mensagem){

			if(mapOnlines.containsKey(mensagem.getDestinatario())){
				
				mensagem.setDataHora(LocalDateTime.now().toString());
				
				enviaMensagem(mensagem, mapOnlines.get(mensagem.getDestinatario()));
				enviaMensagem(mensagem, mapOnlines.get(mensagem.getRemetente()));				

			}

		}
		
		private void enviarMensagemNoChatParaTodos(Mensagem mensagem){
			
			mensagem.setDataHora(LocalDateTime.now().toString());
			
			salvarHistorico(mensagem);
			
			for(Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()){
				System.out.println("q ta acontecendo " + kv.getKey() + ", " + mensagem.getAction());
				enviaMensagem(mensagem, kv.getValue());

			}
			
		}
		
		private void salvarHistorico(Mensagem mensagem){		
				System.out.println("Salvou no historico");
				historicoDeMensagens = historicoDeMensagens.concat("["+ mensagem.getDataHora() +"] \n"+mensagem.getRemetente() + " disse para todos: " + mensagem.getMensagemDeTexto() + "\n");			
			
		}
		
		private String carregarHistorico(){
			
			return historicoDeMensagens;
			
		}
		
		private void enviarArquivoPrivado(Mensagem mensagem){
			
			if(mapOnlines.containsKey(mensagem.getDestinatario())){
				
				enviaMensagem(mensagem, mapOnlines.get(mensagem.getDestinatario()));
				
			}
			
		}
		
		private void enviarArquivoParaTodos(Mensagem mensagem){
			
			for(Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()){
				System.out.println("q ta acontecendo arquivos " + kv.getKey() + ", " + mensagem.getAction());
				enviaMensagem(mensagem, kv.getValue());

			}
			
		}

	}


	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public Map<String, ObjectOutputStream> getMapOnlines() {
		return mapOnlines;
	}
	public void setMapOnlines(Map<String, ObjectOutputStream> mapOnlines) {
		this.mapOnlines = mapOnlines;
	}

}
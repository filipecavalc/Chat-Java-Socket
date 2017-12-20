package com.mensagem.app;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Mensagem  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String remetente;
	private String destinatario;
	private String mensagemDeTexto;
	private Set<String> listaUsuariosOnlines = new HashSet<String>();
	private String historicoDeMensagens;
	private String dataHora;
	private Action action;
	private String nomeArquivo;
	private byte[] Arquivo;

	public enum Action {
		Desconectar, MensagemPrivada, MensagemAll, ListaDeUsuariosOnline, ArquivoPrivado, ArquivoAll, AceitarConexão, RecusarConexão, SolicitarConexão,
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getMensagemDeTexto() {
		return mensagemDeTexto;
	}

	public void setMensagemDeTexto(String mensagemDeTexto) {
		this.mensagemDeTexto = mensagemDeTexto;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Set<String> getListaUsuariosOnlines() {
		return listaUsuariosOnlines;
	}

	public void setListaUsuariosOnlines(Set<String> listaUsuariosOnlines) {
		this.listaUsuariosOnlines = listaUsuariosOnlines;
	}

	public String getHistoricoDeMensagens() {
		return historicoDeMensagens;
	}

	public void setHistoricoDeMensagens(String historicoDeMensagens) {
		this.historicoDeMensagens = historicoDeMensagens;
	}

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	public byte[] getArquivo() {
		return Arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		Arquivo = arquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

}

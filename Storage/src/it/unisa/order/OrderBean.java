package it.unisa.order;

public class OrderBean {
	int idFattura;
	String note;
	String data;
	int idUtente;
	
	public OrderBean() {
		this.idFattura = -1;
		this.note = null;
		this.data = null;
		this.idUtente = -1;
	}
	
	public int getIdFattura() {
		return idFattura;
	}
	public void setIdFattura(int idFattura) {
		this.idFattura = idFattura;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	
}

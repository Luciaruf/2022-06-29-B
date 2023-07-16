package it.polito.tdp.itunes.model;

public class Arco {

	Album source;
	Album target;
	Integer peso;
	
	public Arco(Album source, Album target, Integer peso) {
		super();
		this.source = source;
		this.target = target;
		this.peso = peso;
	}
	
	public Album getSource() {
		return source;
	}
	public void setSource(Album source) {
		this.source = source;
	}
	public Album getTarget() {
		return target;
	}
	public void setTarget(Album target) {
		this.target = target;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	
}

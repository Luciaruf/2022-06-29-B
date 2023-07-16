package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	ItunesDAO dao;
	Graph<Album, DefaultWeightedEdge> graph;
	
	public Model() {
		this.dao = new ItunesDAO();
		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
	}
	
	public Graph creaGrafo(int durata) {
		
		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.graph, this.dao.getVertices(durata));
		
		List<Arco> listaArchi = new ArrayList();
		for(Album a1 : this.graph.vertexSet()) {
			for(Album a2: this.graph.vertexSet()) {
				if(!a1.getAlbumId().equals(a2.getAlbumId())) {
					if(a1.getDurata()!=a2.getDurata()) {
						if(a1.getDurata()+a2.getDurata()>4*durata) {
							
							if(a1.getDurata()<a2.getDurata()) {
								listaArchi.add(new Arco(a1,a2,a1.getDurata()+a2.getDurata()));
							}
							else {
								listaArchi.add(new Arco(a2,a1,a1.getDurata()+a2.getDurata()));
							}
							
						}
					}
				}
			}
		}
		
		for(Arco a : listaArchi){
			Graphs.addEdgeWithVertices(this.graph, a.getSource(), a.getTarget(), a.getPeso());
		}
		
		
		return this.graph;
		
	}
	
	public Integer getBilancio(Album a) {
		int bilancio = 0;
		
		//tutti gli edge che entrano in a e quelli che escono
		List<DefaultWeightedEdge> edgesINDEfaultWeightedEdges = new ArrayList<>(this.graph.incomingEdgesOf(a)); 
		List<DefaultWeightedEdge> edgesOUTDEfaultWeightedEdges = new ArrayList<>(this.graph.outgoingEdgesOf(a));
		
		// cicla su tutti gli edge entranti e somma il peso
		for(DefaultWeightedEdge e: edgesINDEfaultWeightedEdges) 
			bilancio += this.graph.getEdgeWeight(e);
		
		for(DefaultWeightedEdge e1: edgesOUTDEfaultWeightedEdges) 
			bilancio-= this.graph.getEdgeWeight(e1);
		
		
		return bilancio;
	}
	
	public List<BilancioAlbum> getAdiacenti(Album root){
		List<Album>successori = new ArrayList<>(Graphs.successorListOf(this.graph, root));
		
		List<BilancioAlbum> bilanciosuccessori = new ArrayList<>();
		
		for(Album a: successori) {
			bilanciosuccessori.add(new BilancioAlbum(a,getBilancio(a)));
		}
		
		Collections.sort(bilanciosuccessori);
		
		return bilanciosuccessori;
	}
	
	
	
}

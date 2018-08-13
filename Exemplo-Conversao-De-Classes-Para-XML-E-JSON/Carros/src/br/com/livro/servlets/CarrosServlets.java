package br.com.livro.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;
import br.com.livro.domain.ListaCarros;
import br.com.livro.util.ServletUtil;

@WebServlet("/carros/*")
public class CarrosServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CarrosServlets() {

	}

	private CarroService carroService = new CarroService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Carro> carros = carroService.getCarros();
		ListaCarros lista = new ListaCarros();
		lista.setCarros(carros);

		// Gera o XML
		//String xml = JAXBUtil.toXML(lista);
		// Escreve o XML na response do servlet com application/xml
		// ServletUtil.writeXML(response, xml);
		
		// Gera o JSON
		//String json = JAXBUtil.toJSON(lista);
		// Escreve o JSON na response do servlet com application/JSON
		//ServletUtil.writeJSON(response, json);

		// Gera JSON com API do Google gson
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonGson = gson.toJson(lista);
		// Escreve o JSON na response do servlet com a API do Google gson
		ServletUtil.writeJSON(response, jsonGson);
		
		
		// Teste de carro XML
		/*Carro c = new Carro();
		c.setNome("RelâmpagoMarquinhos");
		c.setDesc("Carro do filme CARLOS");
		c.setId(1L);
		c.setTipo("CATHIAU");*/

		//String marquinhos = JAXBUtil.toXML(c);
		//ServletUtil.writeXML(response, marquinhos);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

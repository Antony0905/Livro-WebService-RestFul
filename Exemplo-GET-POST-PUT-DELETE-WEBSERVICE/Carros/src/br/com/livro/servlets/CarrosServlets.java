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
import br.com.livro.domain.Response;
import br.com.livro.util.RegexUtil;
import br.com.livro.util.ServletUtil;

@WebServlet("/carros/*")
public class CarrosServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CarrosServlets() {

	}

	private CarroService carroService = new CarroService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestUri = request.getRequestURI();
		Long id = RegexUtil.matchId(requestUri);

		if (id != null) {
			Carro carro = carroService.getCarro(id);
			if (carro != null) {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(carro);
				ServletUtil.writeJSON(response, json);
			} else {
				response.sendError(404, "Carro não encontrado");
			}
		} else {

			List<Carro> carros = carroService.getCarros();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(carros);
			ServletUtil.writeJSON(response, json);

		}

		// Gera o XML
		// String xml = JAXBUtil.toXML(lista);
		// Escreve o XML na response do servlet com application/xml
		// ServletUtil.writeXML(response, xml);

		// Gera o JSON
		// String json = JAXBUtil.toJSON(lista);
		// Escreve o JSON na response do servlet com application/JSON
		// ServletUtil.writeJSON(response, json);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Cria o carro
		Carro carro = getCarroFromRequest(request);
		// Salva o carro
		carroService.save(carro);
		// Escreve o JSON do novo carro salvo
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(carro);
		ServletUtil.writeJSON(response, json);
	}

	private Carro getCarroFromRequest(HttpServletRequest request) {
		Carro c = new Carro();
		String id = request.getParameter("id");
		if (id != null) {
			// Se informou o id, busca o objeto do banco de dados.
			c = carroService.getCarro(Long.parseLong(id));
		}

		c.setNome(request.getParameter("nome"));
		c.setDesc(request.getParameter("descricao"));
		c.setUrlFoto(request.getParameter("url_foto"));
		c.setUrlVideo(request.getParameter("url_video"));
		c.setLatitude(request.getParameter("latitude"));
		c.setLongitude(request.getParameter("longitude"));
		c.setTipo(request.getParameter("tipo"));

		return c;

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestUri = request.getRequestURI();
		Long id = RegexUtil.matchId(requestUri);

		if (id != null) {
			carroService.delete(id);
			Response r = Response.ok("Carro excluído com sucesso");
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(r);
			ServletUtil.writeJSON(response, json);
		} else {
			response.sendError(200, "URL inválida");
		}

	}

}

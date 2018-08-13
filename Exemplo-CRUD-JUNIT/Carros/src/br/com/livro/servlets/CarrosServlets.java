package br.com.livro.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;

@WebServlet("/carros/*")
public class CarrosServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CarrosServlets() {

	}

	private CarroService carroService = new CarroService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Carro> carros = carroService.getCarros();
		String carroString = carros.toString();

		response.getWriter().write(carroString);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

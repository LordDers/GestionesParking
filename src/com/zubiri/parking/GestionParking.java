package com.zubiri.parking;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		description = "gestion del parking", 
		urlPatterns = { "/Gestor" }				
)

/**
 * Servlet implementation class GestionParking
 */
public class GestionParking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionParking() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType( "text/html; charset=iso-8859-1" );
		if (ParkingVehiculos.getVehiculos().size()==0){
			//lectura del archivo
			ParkingVehiculos.anyadirVehiculo(new Coche(4,true,"marca_prueba","0000AAA",true,50));
			ParkingVehiculos.anyadirVehiculo(new Coche(4,true,"ferrari","0001ABA",true,100));
			ParkingVehiculos.anyadirVehiculo(new Coche(4,true,"fiat","0002ACA",true,10));
		}
		String gestion=request.getParameter("gestion");
		System.out.println(gestion);
		if (gestion.equals("mostrar_vehiculos")){
			System.out.println("empieza mostrando");
			//response(response,ParkingVehiculos.getVehiculos());
			ParkingVehiculos.leerVehiculos2();
			ParkingVehiculos.mostrarParkingVehiculos();
			
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<body>");				
			out.println(ParkingVehiculos.formattedParkingVehiculos());
			out.println("<a href='index.html'><button/>volver</a>");
			out.println("</body>");
			out.println("</html>");
		} else if (gestion.equals("buscar_matricula")){
			System.out.println("empieza buscando");
			String matricula=request.getParameter("matricula");
			Vehiculo encontrado = new Coche();
			try {
				encontrado = ParkingVehiculos.buscarVehiculo(matricula);
				response(response, encontrado);
			} catch (ArrayIndexOutOfBoundsException e) {
				response(response, "no se encontro el vehiculo");
			}			
		} else if (gestion.equals("anyadir_vehiculo")) {
			System.out.println("empieza anyadiendo");
			int n_ruedas = Integer.parseInt(request.getParameter("numruedas"));
			boolean motor = Boolean.parseBoolean(request.getParameter("motor"));
			String marca = request.getParameter("marca");
			String matricula = request.getParameter("matricula");
			boolean automatico = Boolean.parseBoolean(request.getParameter("automatico"));
			int consumo = Integer.parseInt(request.getParameter("consumo"));	
			System.out.println("new coche");
			Vehiculo nuevo = new Coche(n_ruedas,motor,marca,matricula,automatico,consumo);
			ParkingVehiculos.anyadirVehiculo(nuevo);
			// Error por ser Vehiculo
			//ParkingVehiculos.anyadirVehiculosFichero(nuevo);
			if (ParkingVehiculos.buscarVehiculo(matricula) == nuevo) {
				response(response, "vehiculo anyadido");
			} else {
				response(response, "error al anyadir vehiculo");
			}
		} else if (gestion.equals("borrar_vehiculo")){
			System.out.println("borrando");
			String matricula = request.getParameter("matricula1");
			ParkingVehiculos.borrarVehiculo(matricula);
			
			response(response, "Se ha borrado correctamente el vehículo con matricula " + matricula);
			
		} else if (gestion.equals("modificar_vehiculo")){
			/*System.out.println("empieza modificando");
			int n_ruedas = Integer.parseInt(request.getParameter("numruedas"));
			boolean motor = Boolean.parseBoolean(request.getParameter("motor"));
			String marca = request.getParameter("marca");
			String matricula = request.getParameter("matricula");
			boolean automatico = Boolean.parseBoolean(request.getParameter("automatico"));
			int consumo = Integer.parseInt(request.getParameter("consumo"));	
			System.out.println("new coche");
			Vehiculo nuevo = new Coche(n_ruedas,motor,marca,matricula,automatico,consumo);
			ParkingVehiculos.anyadirVehiculo(nuevo);
			if (ParkingVehiculos.buscarVehiculo(matricula) == nuevo) {
				response(response, "vehiculo anyadido");
			} else {
				response(response, "error al anyadir vehiculo");
			}*/
		}
		//ParkingVehiculos pv = new ParkingVehiculos();
		//response(response,"prueba");
		System.out.println("fin");		
	}
	
	// Mostrar vehículos
	private void response(HttpServletResponse response, ArrayList<Vehiculo> vehiculos)
		throws IOException {
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<p>-------------------------------</p>");
		for (int i=0; i<vehiculos.size(); i++){				
			out.println("<b>matricula:</b> "+vehiculos.get(i).getMatricula()+" | ");
			out.print("<b>marca:</b> "+vehiculos.get(i).getMarca()+"");
			out.println("<p>-------------------------------</p>");
		}
		out.println("<a href='index.html'><button/>volver</a>");
		out.println("</body>");
		out.println("</html>");
	}
	
	// Añadir y borrar vehículo
	private void response(HttpServletResponse response,String msg)
		throws IOException {
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");				
		out.println("<p>"+msg+"</p>");
		out.println("<a href='index.html'><button/>volver</a>");
		out.println("</body>");
		out.println("</html>");
	}
	
	// Buscar vehículo
	private void response(HttpServletResponse response, Vehiculo coche)
		throws IOException {
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<p>"+coche.getMarca()+"</p>");
		out.println("<p>"+coche.getMatricula()+"</p>");
		out.println("<a href='index.html'><button/>volver</a>");
		out.println("</body>");
		out.println("</html>");
	}
}

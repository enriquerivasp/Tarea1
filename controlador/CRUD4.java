package controlador;

import java.sql.Connection;
import java.util.Scanner;

import modelo.ConexionBBDD;
import vista.Operaciones;

public class CRUD4 {

	public static void main(String[] args) 
	{
		Connection conexion = null;
		
		ConexionBBDD conectar = new ConexionBBDD();
		
		conexion = conectar.crear_conexion(conexion);
		Operaciones vista = new Operaciones();
		
		int opcion=1;
		
		do
		{
			vista.mostrar_menu();
			
			Scanner leer = new Scanner(System.in);
			opcion = leer.nextInt();
			
			switch(opcion) 
			{
				case 1: vista.insertar_pelicula(conexion);break;
				case 2: vista.buscar_pelicula_nombre(conexion);break;
				case 3:	vista.actualizar_pelicula(conexion);break;
				case 4:	vista.eliminar_pelicula(conexion);break;
				case 5:	vista.busqueda_avanzada_peliculas(conexion);break;
				case 6:System.out.println("Usted está saliendo del programa");break;
				default: System.out.println("Opcion inválida, vuelve a intentarlo");
			}
		}
		while (opcion >=1 && opcion <=5);
		
		conectar.cerrar_conexion(conexion);
	}

}

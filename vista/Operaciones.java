package vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.ResultSet;

public class Operaciones 
{
	public Operaciones (){}
	public static  void mostrar_menu() 
	{
		System.out.println("MENU DE LA BASE DE DATOS");
		System.out.println("1.- INSERTAR PELICULA");
		System.out.println("2.- BUSCAR PELICULA POR NOMBRE");
		System.out.println("3.- ACTUALIZAR PELICULA");
		System.out.println("4.- ELIMINAR PELICULA");
		System.out.println("5.- BUSQUEDA AVANZADA");
		System.out.println("6.- SALIR");
	}
	
	public static void insertar_pelicula (Connection conexion) 
	{
		try 
		{
			String titulo, descripcion;
			int id_lenguaje, alquiler;
			double precio_alquiler, fianza;
			
			System.out.println("Introduce el nombre de la pelicula");
			Scanner leer = new Scanner(System.in);
			titulo=leer.nextLine().trim();
			
			System.out.println("Introduce una descripcion de la pelicula");
			descripcion=leer.nextLine().trim();
			
			System.out.println("Dias que se va a alquilar la pelicula(minimo 3 dias)");
			alquiler=leer.nextInt();
			
			System.out.println("Precio del alquiler");
			precio_alquiler=leer.nextDouble();
			
			System.out.println("Introduce la fianza para la pelicula");
			fianza=leer.nextDouble();
			
			id_lenguaje = 1;
			
			String query="INSERT INTO film (title,description,language_id,rental_duration,rental_rate,replacement_cost) VALUES ( ? , ? , ? , ? , ? , ? )";
			
			PreparedStatement pstmt = conexion.prepareStatement(query);
			
			pstmt.setString(1, titulo);
			pstmt.setString(2, descripcion);
			pstmt.setInt(3, id_lenguaje);
			pstmt.setInt(4, alquiler);
			pstmt.setDouble(5, precio_alquiler);
			pstmt.setDouble(6, fianza);
			
			int insertado = pstmt.executeUpdate();
			
			if (insertado >0) 
			{
				System.out.println("Se ha insertado la fila de forma correcta");
				System.out.println("");
			}
		
		}
		catch(SQLException e) 
		{
			System.out.println("Error al conectar a la base de datos: " + e.getMessage());
		}
		catch(NumberFormatException e) 
		{
			System.out.println("El formato introducido no es correcto: "+e.getMessage());
		}
		catch(InputMismatchException e) 
		{
			System.out.println("Los datos introducidos no son válidos: "+e.getMessage());
		}
	}
	
	public static void buscar_pelicula_nombre(Connection conexion) 
	{
		try {
			System.out.println("Introduce el nombre de la película que quieres buscar");
			Scanner leer = new Scanner(System.in);
			String titulo = leer.nextLine().toUpperCase().trim();
			
			String query="SELECT * FROM film WHERE title = ?";
			
			PreparedStatement pstmt = conexion.prepareStatement(query);
			
			pstmt.setString(1, titulo);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) 
			{
					String titulo_pelicula = rs.getString("title");
					int id_pelicula = rs.getInt("film_id");
					String descripcion = rs.getString("description");
					
					System.out.println("Id de la pelicula: "+id_pelicula);
					System.out.println("Nombre de la pelicula: "+titulo_pelicula);
					System.out.println("Descripcion: "+descripcion);
					System.out.println("");
			}
			else 
			{
				System.out.println("Resultados no encontrados");
			}
		}
		catch(SQLException e) 
		{
			System.out.println("Error al conectar a la base de datos: " + e.getMessage());
		}
		catch(NumberFormatException e) 
		{
			System.out.println("El formato introducido no es correcto: "+e.getMessage());
		}
		catch(InputMismatchException e) 
		{
			System.out.println("Los datos introducidos no son válidos: "+e.getMessage());
		}
	}
	
	public static void actualizar_pelicula(Connection conexion) 
	{
		try 
		{
			System.out.println("Introduce el id de la película que quieres editar");
			Scanner leer = new Scanner(System.in);
			int id = leer.nextInt();
			
			System.out.println("Introduce el nuevo título de la película");
			Scanner leer1 = new Scanner(System.in);
			String titulo = leer1.nextLine().trim();
			
			String query = "UPDATE film SET title = ? WHERE film_id=?";
			
			PreparedStatement pstmt = conexion.prepareStatement(query);
			
			pstmt.setString(1, titulo);
			pstmt.setInt(2,id);
			
			int confirmado = pstmt.executeUpdate();
			
			if (confirmado > 0) 
			{
				System.out.println("Se ha actualizado de forma correcta el registro");
				System.out.println("");
			}
			else 
			{
				System.out.println("Id de pelicula incorrecto");
				System.out.println("");
			}
		}
		catch(SQLException e) 
		{
			System.out.println("Error al conectar a la base de datos: " + e.getMessage());
		}
		catch(NumberFormatException e) 
		{
			System.out.println("El formato introducido no es correcto: "+e.getMessage());
		}
		catch(InputMismatchException e) 
		{
			System.out.println("Los datos introducidos no son válidos: "+e.getMessage());
		}
	}
	
	public static void eliminar_pelicula(Connection conexion) 
	{
		try {
			System.out.println("Introduce el id de la película que quieres editar");
			Scanner leer = new Scanner(System.in);
			int id = leer.nextInt();
			
			String query = "DELETE FROM film WHERe film_id = ?";
			
			PreparedStatement pstmt = conexion.prepareStatement(query);
			
			pstmt.setInt(1,id);
			
			int borrado = pstmt.executeUpdate();
			
			if (borrado>0) 
			{
				System.out.println("Se ha borrado el registro");
				System.out.println("");
			}
			
			
		}
		catch(SQLException e) 
		{
			System.out.println("Error al conectar a la base de datos: " + e.getMessage());
		}
		catch(NumberFormatException e) 
		{
			System.out.println("El formato introducido no es correcto: "+e.getMessage());
		}
		catch(InputMismatchException e) 
		{
			System.out.println("Los datos introducidos no son válidos: "+e.getMessage());
		}
	}
	
	public static void busqueda_avanzada_peliculas(Connection conexion) 
	{
		try 
		{
			String query ="SELECT film.film_id, actor.first_name, actor.last_name, actor.last_update, film.title, film.description, film.length "
						+ " FROM film "
							+ " LEFT JOIN film_actor ON film.film_id = film_actor.film_id "
							+ " LEFT JOIN actor ON film_actor.actor_id = actor.actor_id ";
			
			System.out.println("Quieres buscar por un título concreto(introduce el titulo para buscar o deja en blanco para buscar por todos)");
			Scanner leer = new Scanner(System.in);
			String titulo = leer.nextLine().toUpperCase().trim();
			
			if (titulo == null) 
			{
				query += "WHERE title LIKE '%' ";
			}
			else 
			{
				query += "WHERE title LIKE '%"+titulo+"%' ";
			}
			
			System.out.println("Quieres buscar por descripcion (introduce algo para buscar por descripcion o no introduzcas nada para buscar por todos)");
			Scanner leer1 = new Scanner(System.in);
			String descripcion = leer.nextLine().trim();
			
			if (descripcion == null) 
			{
				query += "AND description LIKE '%' ";
			}
			else 
			{
				query += "AND description LIKE '%"+descripcion +"%' ";
			}
		
			
			System.out.println("Quieres buscar por año(introduce un año para buscar o no introduzcas nada para buscar por todos)");
			Scanner leer2 = new Scanner(System.in);
			String año = leer2.nextLine().trim();
			
			if (año  == null)
			{
				query += "AND release_year LIKE '%' ";
			}
			else 
			{
				query += "AND release_year LIKE '%"+año+"%' ";
			}
			
			System.out.println("Quires buscar por duracion(introduce una para buscar por esta o no introduzcas nada para buscar por todas)");
			Scanner leer3 = new Scanner(System.in);
			String duracion = leer3.nextLine().trim();
			
			if (duracion == null) 
			{
				query += "AND length LIKE '%' ";
			}
			else 
			{
				query += "AND length LIKE '%"+duracion+"%' ";
			}
			
			System.out.println("Quieres buscar por nombre de actor (introduce un nombre o parte de el para buscar, o no intrduzcas nada para buscar por todos)");
			Scanner leer4 = new Scanner(System.in);
			String nombre = leer4.nextLine().toUpperCase().trim();
			
			System.out.println("Quieres buscar por apellido de actor (introduce un apellido o parte de el para buscar, o no intrduzcas nada para buscar por todos)");
			Scanner leer5 = new Scanner(System.in);
			String apellido = leer4.nextLine().toUpperCase().trim();
			
			if (nombre == null && apellido == null) 
			{
				query += "AND first_name LIKE '%' AND last_name LIKE '%'";
			}
			else if (nombre != null && apellido ==  null) 
			{
				query += "AND first_name LIKE '%"+nombre+"%' AND last_name LIKE '%'";
			}
			else if (nombre == null && apellido != null) 
			{
				query += "AND first_name LIKE '%' AND last_name LIKE '%"+apellido+"%'";
			}
			else 
			{
				query += "AND first_name LIKE '%"+nombre+"%' AND last_name LIKE '%"+apellido+"%'";
			}
			
			PreparedStatement pstmt = conexion.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) 
			{
				System.out.println("ID de la pelicula: " + rs.getInt("film_id") +
	                       "\nNombre del actor: " + rs.getString("first_name") +
	                       "\nApellido del actor: " + rs.getString("last_name") +
	                       "\nDescripcion de la pelicula: " + rs.getString("description")+
	                       "\nDuracion de la pelicula: "+rs.getInt("length")+"\n");   
			}
			
		}
		catch(SQLException e) 
		{
			System.out.println("Error al conectar a la base de datos: " + e.getMessage());
		}
		catch(NumberFormatException e) 
		{
			System.out.println("El formato introducido no es correcto: "+e.getMessage());
		}
		catch(InputMismatchException e) 
		{
			System.out.println("Los datos introducidos no son válidos: "+e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

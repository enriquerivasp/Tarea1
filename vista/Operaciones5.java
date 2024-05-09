package vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Operaciones5 {

	
	public Operaciones5()
	{
		
	}
	
	
	public static void mostrar_menu()
	{
		System.out.println("Menu: ");
		System.out.println("------------");
		System.out.println("1.Crear Peliculas");
		System.out.println("2.Leer Peliculas");
		System.out.println("3.Actualizar Peliculas");
		System.out.println("4.Eliminar Peliculas");
		System.out.println("-------------");
	}
	
	
	public static void crear_pelicula(Connection con)
	{
		boolean transaccion = false;
		try {
			con.setAutoCommit(false);
			Scanner scanner = new Scanner(System.in);
			String id;
			String titulo;
			String descripcion;
			
			System.out.println("Introduce el nombre de la pelicula: ");
			titulo=scanner.next();
			System.out.println("Introduce el ID de la pelicula ");
			id = scanner.next();
			System.out.println("Introduce la descripcion de la pelicula");
			descripcion = scanner.next();
			
			String query1 = "insert into film (film_id,title,description) values (?,?,?)";
			String query2 = "insert into film_text (film_id,title,description) values (?,?,?)";
			
			PreparedStatement stm = con.prepareStatement(query1,PreparedStatement.RETURN_GENERATED_KEYS);
			PreparedStatement stmt = con.prepareStatement(query2,PreparedStatement.RETURN_GENERATED_KEYS);

           
            
            stm.setString(1, id);
            stm.setString(2, titulo);
			stm.setString(3, descripcion);
			
		    stmt.setString(1, id);
            stmt.setString(2, titulo);
			stmt.setString(3, descripcion);
			
			int insertado = stm.executeUpdate();
			int insertado1 = stmt.executeUpdate();
			
            ResultSet rsfilm1 = stm.getGeneratedKeys();
            ResultSet rsfilm2 = stm.getGeneratedKeys();

			
			if(insertado == 1 && insertado1 == 1)
			{
				transaccion=true;
				con.commit();
				System.out.println("Insertado correctamente");
			}
			else {
				con.rollback();
				System.out.println("ERROR al insertar");
			}
			
			con.commit();
			
		}
		catch(NumberFormatException e) 
		{
			System.out.println("El formato introducido no es correcto: "+e.getMessage());
		}
		catch(InputMismatchException e) 
		{
			System.out.println("Los datos introducidos no son válidos: "+e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error al conectar a la base de datos.");
			e.printStackTrace();
		}
		
		finally{
			if(transaccion==false)
			{
				try {
					con.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void leer_pelicula(Connection con)
	{
		boolean transaccion=false;
		Scanner scanner = new Scanner(System.in);

		String id;
		String nombre;
		String descripcion;
		int año_creacion;
		int duracion;

		try {
			con.setAutoCommit(false);

			
			System.out.println("Introduce el nombre ");
			nombre = scanner.next();
			
			
			
			String query1= "Select film_id,title,description,release_year,lenght from film where title = ?";
			String query2= "Select film_id,title,description,release_year,lenght from film_id where title = ?";
			
	  PreparedStatement stm = con.prepareStatement(query1);
	          PreparedStatement stmt = con.prepareStatement(query2);
	            
	           
	            
	            stm.setString(1, nombre);
	            
	            stmt.setString(1, nombre);
			
				ResultSet rsfilm = stm.executeQuery();
				
				ResultSet rsfilm1 = stmt.executeQuery();

				System.out.println("Datos de la Tabla FILM");
				if (rsfilm.next()) 
				{
					
						String titulo_pelicula = rsfilm.getString("title");
						int id_pelicula = rsfilm.getInt("film_id");
						String descripcionn = rsfilm.getString("description");
						
						System.out.println("Id de la pelicula: "+id_pelicula);
						System.out.println("Nombre de la pelicula: "+titulo_pelicula);
						System.out.println("Descripcion: "+descripcionn);
						System.out.println("");
				}
				else 
				{
					System.out.println("Resultados no encontrados");
				}
				System.out.println("Datos de la Tabla FILM_text");
				if (rsfilm.next()) 
				{
					
						String titulo_pelicula = rsfilm.getString("title");
						int id_pelicula = rsfilm.getInt("film_id");
						String descripcionn = rsfilm.getString("description");
						
						System.out.println("Id de la pelicula: "+id_pelicula);
						System.out.println("Nombre de la pelicula: "+titulo_pelicula);
						System.out.println("Descripcion: "+descripcionn);
						System.out.println("");
				}
				else 
				{
					System.out.println("Resultados no encontrados");
				}
		
		}
		catch(NumberFormatException e) 
		{
			System.out.println("El formato introducido no es correcto: "+e.getMessage());
		}
		catch(InputMismatchException e) 
		{
			System.out.println("Los datos introducidos no son válidos: "+e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error al conectar a la base de datos.");
			e.printStackTrace();
		}
		
		finally{
			if(transaccion==false)
			{
				try {
					con.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
		
	
	
	public static void actualizar_pelicula(Connection con)
	{
		boolean transaccion = false;
		Scanner scanner = new Scanner(System.in);

		try{
			con.setAutoCommit(false);
		
			System.out.println("Introduce por que quieres actualizar:");
			String opcion = scanner.next();
			
			if(opcion.equalsIgnoreCase("titulo"))
			{
			System.out.println("Introduce el id de la pelicula a modificar:  ");
			int id = scanner.nextInt();
			
			System.out.println("Introduce el nuevo titulo: ");
			String titulo = scanner.next();
			
			String query = "UPDATE FILM where film_id = ?";
			PreparedStatement stm = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			stm.setString(1, titulo);
			
			int confirmado = stm.executeUpdate();
            ResultSet rsfilm1 = stm.getGeneratedKeys();
			}
			
			}

		
		
		catch(NumberFormatException e) 
		{
			System.out.println("El formato introducido no es correcto: ");
		}
		catch(InputMismatchException e) 
		{
			System.out.println("Los datos introducidos no son válidos: "+e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error al conectar a la base de datos.");
			e.printStackTrace();
		}
		
		finally{
			if(transaccion==false)
			{
				try {
					con.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

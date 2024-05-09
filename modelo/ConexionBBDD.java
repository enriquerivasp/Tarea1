package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBBDD 
{
	//Comentario prueba
	String url, usuario, contraseña;
	static final String archivo ="D:\\PROGRAMACIÓN\\conexion_crud4.txt";
	
	public ConexionBBDD()
	{
		String actual;
		String[] cortado;
		
		try(BufferedReader lector = new BufferedReader(new FileReader(archivo)))
		{
			actual=lector.readLine();
			
			cortado = actual.split("=|;");
			
			this.url="jdbc:mysql://localhost:3306/"+cortado[1];
			this.usuario=cortado[3];
			this.contraseña=cortado[5];
		}
		catch(FileNotFoundException e ) 
		{
			System.out.println("El archivo no se ha encontrado "+e.getMessage());
		}
		catch(IOException e) 
		{
			System.out.println("El archivo no puede leerse de forma satisfactoria "+e.getMessage());
		}
	}
	
	public Connection crear_conexion (Connection conexion) 
	{
		try 
		{
			conexion = DriverManager.getConnection(url,usuario,contraseña);
			
			return conexion;
		}
		catch(SQLException e) 
		{
			System.out.println("Error al conectar con la base de datos "+e.getMessage());
		}
		return conexion;
	}
	
	public Connection cerrar_conexion (Connection conexion) 
	{
		try 
		{
			conexion.close();
			
			return conexion;
		}
		catch(SQLException e) 
		{
			System.out.println("Error al conectar con la base de datos "+e.getMessage());
		}
		return conexion;
	}
}

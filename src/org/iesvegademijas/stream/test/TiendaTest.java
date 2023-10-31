package org.iesvegademijas.stream.test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

import com.sun.xml.bind.v2.TODO;
import org.iesvegademijas.hibernate.Fabricante;
import org.iesvegademijas.hibernate.FabricanteHome;
import org.iesvegademijas.hibernate.Producto;
import org.iesvegademijas.hibernate.ProductoHome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;


class TiendaTest {
	
	@Test
	void testSkeletonFrabricante() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();


			//TODO STREAMS

		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	

	@Test
	void testSkeletonProducto() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
		
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	@Test
	void testAllFabricante() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
			assertEquals(9,listFab.size());
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	@Test
	void testAllProducto() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
			assertEquals(11,listProd.size());
		
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		

	
	}
	
	/**
	 * 1. Lista los nombres y los precios de todos los productos de la tabla producto
	 */
	@Test
	void test1() {
	
		ProductoHome prodHome = new ProductoHome();
		
		try {
			prodHome.beginTransaction();
			
			List<Producto> listProd = prodHome.findAll();
			
			List<String> listaNombrePrecioProd = listProd.stream()
					.map(p -> "Nombre: " + p.getNombre() + " Precio: " + p.getPrecio())
					.toList();

			listaNombrePrecioProd.forEach(System.out::println);

			//Set<String[]> setNombrePrecio = listProd.stream().map(p -> new String[]{p.getNombre(), Double.toString(p.getPrecio())}).collect(toSet());

			//setNombrePrecio.forEach(strings -> System.out.println("Nombre "+strings[0] + ", Precio: "+strings[1]));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	
	/**
	 * 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares .
	 */
	@Test
	void test2() {
		
		ProductoHome prodHome = new ProductoHome();
		double conversionDolares = 1.0564;

		try {
			prodHome.beginTransaction();			
			List<Producto> listProd = prodHome.findAll();

			List<String> listaNombrePrecioProdDolar = listProd.stream()
					.map(p -> "Nombre: " + p.getNombre() + " Precio: " + p.getPrecio()*conversionDolares)
					.toList();

			listaNombrePrecioProdDolar.forEach(System.out::println);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.
	 */
	@Test
	void test3() {
		
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		

			List<String> listNombreMinusPrecio = listProd.stream()
					.map(p-> "Nombre en mayúscula: " +p.getNombre().toUpperCase() + " Precio: "+p.getPrecio())
					.toList();

			listNombreMinusPrecio.forEach(System.out::println);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.
	 */
	@Test
	void test4() {
	
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();

			List<String> nombreFabDos = listFab.stream()
					.map(f -> f.getNombre()+ ": "+f.getNombre().toUpperCase().substring(0,2))
					.toList();

			nombreFabDos.forEach(System.out::println);

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 5. Lista el código de los fabricantes que tienen productos.
	 */
	@Test
	void test5() {
	
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();

			List<Integer> listFabConProd = listFab.stream()
					.filter(f -> !f.getProductos().isEmpty())
					.map(Fabricante::getCodigo)
					.toList();

			listFabConProd.forEach(System.out::println);
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
	 */
	@Test
	void test6() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			List<String> fabNombreDesc = listFab.stream()
					.sorted(comparing(Fabricante::getNombre).reversed())
					.map(Fabricante::getNombre)
					.toList();

			fabNombreDesc.forEach(System.out::println);

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
	 */
	@Test
	void test7() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		

			List<String> listProdNombreAScPrecioDesc =
					listProd.stream()
							.sorted(comparing(Producto::getPrecio).reversed())
							.sorted(comparing(Producto::getNombre))
							.map(p -> p.getNombre() + ": "+p.getPrecio())
							.toList();
			listProdNombreAScPrecioDesc.forEach(System.out::println);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 8. Devuelve una lista con los 5 primeros fabricantes.
	 */
	@Test
	void test8() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();

			List<String> primeros5Fab = listFab.stream().map(f -> f.getNombre() + " (Código: "+ f.getCodigo()+")").limit(5).toList();

			primeros5Fab.forEach(System.out::println);
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
	 */
	@Test
	void test9() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			List<String> fab4y5 = listFab.stream()
					.skip(3)
					.limit(2)
					.map(Fabricante::getNombre)
					.toList();

			fab4y5.forEach(System.out::println);

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 10. Lista el nombre y el precio del producto más barato
	 */
	@Test
	void test10() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		

			List<String> prodMasBarato = listProd.stream()
					.sorted(comparing(Producto::getPrecio))
					.limit(1)
					.map(p -> "Producto más barato: " + p.getNombre() + " con precio: " + p.getPrecio())
					.toList();

			prodMasBarato.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 11. Lista el nombre y el precio del producto más caro
	 */
	@Test
	void test11() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

			List<String> prodMasCaro= listProd.stream()
					.sorted(comparing(Producto::getPrecio).reversed())
					.limit(1)
					.map(p -> "Producto más caro: " + p.getNombre() + " con precio: " + p.getPrecio())
					.toList();

			prodMasCaro.forEach(System.out::println);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
	 * 
	 */
	@Test
	void test12() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			List<String> prodFab2 = listProd.stream()
					.filter(p -> p.getFabricante().getCodigo() == 3)
					.map( p ->"Productos del fabricante con código 3: " + p.getNombre()).toList();

			prodFab2.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
	 */
	@Test
	void test13() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			List<String> prodPrecioMenorO120 = listProd.stream()
							.filter(p -> p.getPrecio() <= 120)
							.map(Producto::getNombre)
							.toList();

			System.out.println("Productos con precio <= 120: ");
			prodPrecioMenorO120.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 14. Lista los productos que tienen un precio mayor o igual a 400€.
	 */
	@Test
	void test14() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

			List<String> prodPrecioMayorO400 = listProd.stream()
					.filter(p -> p.getPrecio() >= 400)
					.map(Producto::getNombre)
					.toList();

			System.out.println("Productos con precio >= 400: ");
			prodPrecioMayorO400.forEach(System.out::println);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 15. Lista todos los productos que tengan un precio entre 80€ y 300€. 
	 */
	@Test
	void test15() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

			List<String> prodPrecio80A300 = listProd.stream()
					.filter(p -> p.getPrecio() >= 80 && p.getPrecio() <= 300)
					.map(Producto::getNombre)
					.toList();

			System.out.println("Productos con precio entre 80 y 300€: ");
			prodPrecio80A300.forEach(System.out::println);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
	 */
	@Test
	void test16() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

			List<String> prodPrecioMayor200Fab6 = listProd.stream()
					.filter(p -> p.getPrecio() > 200 && p.getFabricante().getCodigo() == 6)
					.map( p -> p.getNombre() + " "+p.getPrecio()+ "F: "+p.getFabricante().getCodigo())
					.toList();

			System.out.println("Productos con precio mayor a 200€ y código de fabricante 6: ");
			prodPrecioMayor200Fab6.forEach(System.out::println);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
	 */
	@Test
	void test17() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
			
			Set<Integer> codigosFabs = new HashSet<>();
			codigosFabs.add(1);
			codigosFabs.add(3);
			codigosFabs.add(5);

			List<String> listProdFab135 = listProd.stream()
					.filter(p -> codigosFabs.contains(p.getFabricante().getCodigo()))//códigosFabs es una closure externa que se atrapa en el lambda
					.map(p -> p.getNombre() + " (Código fabricante: "+p.getFabricante().getCodigo()+")")
					.toList();

			listProdFab135.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 18. Lista el nombre y el precio de los productos en céntimos.
	 */
	@Test
	void test18() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		

			List<String> prodCents = listProd.stream()
					.map(p -> p.getNombre() + "Precio en céntimos: "+p.getPrecio()*100)
					.toList();

			prodCents.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
	 */
	@Test
	void test19() {
	
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			List<String> fabricantesS = listFab.stream()
					.map(Fabricante::getNombre)
					.filter(nombre -> nombre.charAt(0) == 'S')
					.toList();

			fabricantesS.forEach(System.out::println);
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
	 */
	@Test
	void test20() {
	
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<String> productosPortatil = listProd.stream()
					.map(Producto::getNombre)
					.filter(nombre -> nombre.contains("Portátil"))
					.toList();

			productosPortatil.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
	 */
	@Test
	void test21() {
	
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<String> productosMonitorMenos215 = listProd.stream()
					.filter(p -> p.getNombre().contains("Monitor") && p.getPrecio()  < 215)
					.map(Producto::getNombre)
					.toList();

			productosMonitorMenos215.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
	 */
	@Test
	void test22() {

		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<String> prodMayorO180 = listProd.stream()
					.filter(p -> p.getPrecio()>=180)
					.sorted(comparing(Producto::getPrecio).thenComparing(Producto::getNombre).reversed())
					.map(p -> "Nombre: " + p.getNombre() + " Precio: " + p.getPrecio())
					.toList();

			prodMayorO180.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos. 
	 * Ordene el resultado por el nombre del fabricante, por orden alfabético.
	 */
	@Test
	void test23() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<String> prodFabAlf = listProd.stream()
					.sorted(comparing(p -> p.getFabricante().getNombre()))
					.map(p -> "Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio()+" , Fabricante: "+p.getFabricante().getNombre())
					.toList();

			prodFabAlf.forEach(System.out::println);


			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
	 */
	@Test
	void test24() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<String> prodMasCaro = listProd.stream()
					.sorted(comparing(Producto::getPrecio).reversed())
					.limit(1)
					.map(p -> "Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio()+" , Fabricante: "+p.getFabricante().getNombre())
					.toList();

			prodMasCaro.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
	 */
	@Test
	void test25() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<String> crucialMayor200 = listProd.stream()
					.filter(p -> p.getFabricante().getNombre().equals("Crucial") && p.getPrecio() > 200)
					.map(Producto::getNombre)
					.toList();

			crucialMayor200.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
	 */
	@Test
	void test26() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

			List<String> asusHPSeagate = listProd.stream()
					.filter(p -> p.getFabricante().getNombre().equals("Asus") || p.getFabricante().getNombre().equals("Hewlett-Packard") || p.getFabricante().getNombre().equals("Seagate") )
					.map(Producto::getNombre)
					.toList();

			asusHPSeagate.forEach(System.out::println);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
	 * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.
	 * La salida debe quedar tabulada como sigue:

Producto                Precio             Fabricante
-----------------------------------------------------
GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
Portátil Yoga 520      |452.79            |Lenovo
Portátil Ideapd 320    |359.64000000000004|Lenovo
Monitor 27 LED Full HD |199.25190000000003|Asus

	 */		
	@Test
	void test27() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();

			List<Producto> listProd = prodHome.findAll();

			//Máxima longitud de los nombres, precios y fabricantes. Si son 0, se pone la longitud de las palabras producto, precio y fabricante
			int maxNombre = listProd.stream().mapToInt(p -> p.getNombre().length()).max().orElse("Producto".length());
			int maxPrecio = listProd.stream().mapToInt(p -> Double.toString(p.getPrecio()).length()).max().orElse("Precio".length());
			int maxFabricante = listProd.stream().mapToInt(p -> p.getFabricante().getNombre().length()).max().orElse("Fabricante".length());

			//Productos de precio mayor o igual a 180 ordenados por precio y por nombre
			List<String> prodMayorO180 = listProd.stream()
					.filter(p -> p.getPrecio()>=180)
					.sorted(comparing(Producto::getPrecio).thenComparing(Producto::getNombre).reversed())
					//Formatear en tabla con la longitud máxima menos la longitud del elemento para que se añadan los espacios correctos
					.map(p ->
							p.getNombre() + " ".repeat(maxNombre-p.getNombre().length()) + "|" +
							p.getPrecio() +  " ".repeat(maxPrecio-Double.toString(p.getPrecio()).length()) +"|" +
							p.getFabricante().getNombre())
					.toList();

			//Formatear cabecera con los espacios máximos y 1 más
			System.out.println("Producto" + " ".repeat(maxNombre- "Producto".length() +1) + "Precio" + " ".repeat(maxPrecio - "Precio".length()+ 1) + "Fabricante");
			System.out.println("-".repeat(maxNombre+maxPrecio+maxFabricante));

			prodMayorO180.forEach(System.out::println);


			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos. 
	 * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados. 
	 * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES
	 * La salida debe queda como sigue:
Fabricante: Asus

            	Productos:
            	Monitor 27 LED Full HD
            	Monitor 24 LED Full HD

Fabricante: Lenovo

            	Productos:
            	Portátil Ideapd 320
            	Portátil Yoga 520

Fabricante: Hewlett-Packard

            	Productos:
            	Impresora HP Deskjet 3720
            	Impresora HP Laserjet Pro M26nw

Fabricante: Samsung

            	Productos:
            	Disco SSD 1 TB

Fabricante: Seagate

            	Productos:
            	Disco duro SATA3 1TB

Fabricante: Crucial

            	Productos:
            	GeForce GTX 1080 Xtreme
            	Memoria RAM DDR4 8GB

Fabricante: Gigabyte

            	Productos:
            	GeForce GTX 1050Ti

Fabricante: Huawei

            	Productos:


Fabricante: Xiaomi

            	Productos:

	 */
	@Test
	void test28() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();

			List<String> fabProds = listFab.stream()
					.map(f -> "Fabricante: "+ f.getNombre() + "\n\n\t\t\tProductos:\n" + "\t\t\t" +
							f.getProductos().stream()
									.map(p -> p.getNombre()+"\n\t\t\t")
									.collect(joining()))
					.toList();

			fabProds.forEach(System.out::println);
								
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
	 */
	@Test
	void test29() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			List<String> fabsSinProds = listFab.stream().filter(p -> p.getProductos().isEmpty()).map(Fabricante::getNombre).toList();

			fabsSinProds.forEach(System.out::println);
								
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
	 */
	@Test
	void test30() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

			System.out.println("Total de productos: " + listProd.stream().map(Producto::getNombre).toList().size());

			Assertions.assertEquals(11,listProd.stream().map(Producto::getNombre).toList().size());
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}

	
	/**
	 * 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
	 */
	@Test
	void test31() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		

			long fabricantes = listProd.stream()
					.map(p -> p.getFabricante().getNombre())
					.distinct()
					.count();

			Assertions.assertEquals(7,fabricantes);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 32. Calcula la media del precio de todos los productos
	 */
	@Test
	void test32() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		

			Double precioMedio = listProd.stream()
					.mapToDouble (Producto::getPrecio)
					.average()
					.orElse(0.0);

			Assertions.assertEquals(271.7236363636364, precioMedio);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
	 */
	@Test
	void test33() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		

			Double precioMin = listProd.stream()
					.map(Producto::getPrecio)
					.reduce(Double::min).orElse(0.0);

			Assertions.assertEquals(59.99, precioMin);


			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 34. Calcula la suma de los precios de todos los productos.
	 */
	@Test
	void test34() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			Double sumaPreciosProds = listProd.stream()
					.mapToDouble (Producto::getPrecio)
					.sum();

			Assertions.assertEquals(2988.96, sumaPreciosProds);


			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 35. Calcula el número de productos que tiene el fabricante Asus.
	 */
	@Test
	void test35() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		

			long productosAsus = listProd.stream()
					.filter(p -> p.getFabricante().getNombre().equals("Asus"))
					.count();

			Assertions.assertEquals(2, productosAsus);

			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 36. Calcula la media del precio de todos los productos del fabricante Asus.
	 */
	@Test
	void test36() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			OptionalDouble opPrecioMedioProdsAsus = listProd.stream()
					.filter(p -> p.getFabricante().getNombre().equals("Asus"))
					.mapToDouble(Producto::getPrecio)
					.average();

			double precioMedioProdsAsus = 0.0;

			if (opPrecioMedioProdsAsus.isPresent()) {
				precioMedioProdsAsus = opPrecioMedioProdsAsus.getAsDouble();
			}

			Assertions.assertEquals(223.995, precioMedioProdsAsus);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial. 
	 *  Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 */
	@Test
	void test37() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
						
			//TODO STREAMS
			Set<String[]> setNombrePrecio = listProd.stream().map(p -> new String[]{p.getNombre(), Double.toString(p.getPrecio())}).collect(toSet());

//			Double[] preciosCrucial = listProd.stream()
//					.filter(p -> p.getFabricante().getNombre().equals("Crucial"))
//					.map(p -> p.getPrecio())
//					// precio máximo, precio mínimo, precio medio y el número total de productos
//					.reduce(p -> new Double[]{0.0, Double.MAX_VALUE, 0.0, 0.0,0.0}, p.






			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 38. Muestra el número total de productos que tiene cada uno de los fabricantes. 
	 * El listado también debe incluir los fabricantes que no tienen ningún producto. 
	 * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene. 
	 * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.
	 * La salida debe queda como sigue:
	 
     Fabricante     #Productos
-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
           Asus              2
         Lenovo              2
Hewlett-Packard              2
        Samsung              1
        Seagate              1
        Crucial              2
       Gigabyte              1
         Huawei              0
         Xiaomi              0

	 */
	@Test
	void test38() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes. 
	 * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 * Deben aparecer los fabricantes que no tienen productos.
	 */
	@Test
	void test39() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€. 
	 * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
	 */
	@Test
	void test40() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
	 */
	@Test
	void test41() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			List<String> fabs2oMasProds = listFab.stream()
					.filter(f -> f.getProductos().size() >= 2)
					.map(Fabricante::getNombre)
					.toList();

			fabs2oMasProds.forEach(System.out::println);

		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €. 
	 * Ordenado de mayor a menor número de productos.
	 */
	@Test
	void test42() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();

			List<String> fabsProductosMayorO220 = listFab.stream()
					.map(f -> Map.entry(f, f.getProductos().stream().filter(p -> p.getPrecio() >= 220).count()))
					.sorted((f1, f2) -> (int) (f2.getValue() - f1.getValue()))
					.map(f -> "Fabricante: " + f.getKey().getNombre() + "\n\tNúmero de productos precio >= 220: " + f.getValue())
					.toList();

			fabsProductosMayorO220.forEach(System.out::println);


		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 43.Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 */
	@Test
	void test43() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS

		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 * Ordenado de menor a mayor por cuantía de precio de los productos.
	 */
	@Test
	void test44() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante. 
	 * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante. 
	 * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
	 */
	@Test
	void test45() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
			
	}
	
	/**
	 * 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante.
	 * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
	 */
	@Test
	void test46() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS															
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
			
	}
	
}


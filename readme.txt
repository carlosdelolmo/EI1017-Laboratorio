Para facilitar la labor de corrección, hemos incluído este fichero donde se resumen algunas de las mejoras de la nueva versión implementada, las sugerencias del profesor, y mejoras propias que se nos han ocurrido para intentar mejorar la experiencia de usuario.

Mejoras propias
===============
- Creada una clase FileType que permite verificar que cierto archivo tiene una extensión dada. En caso contrario lanza una excepción propia y se deja de trabajar con el archivo. Usamos esta clase para verificar que trabajamos con ficheros CSV.
- Creados test unitarios para comprobar el funcionamiento de la clase FileType.
- Creada una excepción propia InvalidFileTypeException.
- Añadido nuevo botón para volver al principio en la interfaz gráfica, permitiendo escoger nuevamente un fichero.
- Añadido nuevo botón para volver a ver un punto anterior ya estimado. La información referente al anterior punto la tiene el controlador, y es la vista la que se la pide cuando la requiere.
- Gestionada en el controlador la inserción de puntos para estimar. Si se introducen valores que no se pueden pasar a Double no produce un error.

Sesion 1
========
7	Estupenda factorización del código en CSV. Me encanta.
8	Los tipos que se usan en los argumentos de algunos métodos son cuestionables
		KNN.train(TableWithLabels tabla) --> KNN.train(Table tabla) // Y posteriormente se promociona a TableWithLabels

9	Los nombres de algunas variables o funciones no parecen demasiado adecuados
		Sustituídos los nombres confusos y poco explicativos.
		br --> bufferedReader
		e  --> exception
		campos --> encabezadosCSV
		num --> splittedLine
		elementoFichero --> fila
		elemento --> valor
		listado --> listaNumeros

4 	En algunas pruebas no se sabe cómo se ha obtenido el valor esperado o podría mejorarse/automatizarse
		Se automatizan mediante el uso de los métodos ya definidos y bucles

Sesion 2
=========
21*	Se tienen como atributos de la clase más info de la necesaria
		Se suprimen los atributos de clase no necesarios

32	public void train(T tipo); // Más que “tipo” sería “datos”, por ejemplo
		Corregido

33	Algorithm<TableWithLabels, Row, String > // para Kmeans debería ser Table, por más que usemos un dataset con etiquetas (pero que no usamos)
		Corregido en Kmeans y en KNN

34	Los representantes inciaales (aleatorios), ¿no podrían repetirse?
		Corregido mediante un do{}while(), escogiendo aleatoriamente un representante hasta encontrar uno que no este ya en el grupo de representantes

35	if (min_dist > dist || min_dist == -1.0)// ¿hace falta el 2º criterio?
		Sí era necesario para que el código funcionase según lo esperado.
		Corregida la implementación para que no sea necesario y simplificar el código.

36	obtenerEtiqueta() : algo no me cuadra; si el grupo es 3, la etiqueta es “cluster-3”. No hace falta una tabla con etiquetas; de hecho, no debe usarse las etiquetas reales
		Corregido

37	suma() y multiplicar() // detalle menor, pero conviene ser consistentes en los nombres; ¿usamos infinitivo?
		Corregido. Ambos son infinitivos ahora.

38	¿para qué se llama a obtenerEtiqueta() en mediaPuntosPorGrupo()?
		Corregido

39*	El concepto de kmeans() no parece que esté claro; y parece que la implementación se resiente, aunque se nota un esfuerzo en tener una solución modular
		Se ha modificado gran parte de la implementación de kmeans. Inicialmente se tenía una idea confusa de el concepto, pero en las siguientes clases de prácitcas se han solucionado.

Sesion 3
========
17	ElementoFichero → “Fichero”? Nombre mejorable, confuso
		Corregido, elementoFichero --> fila

18	Switch → sintaxis “nueva”. Sugiero la clásica para facilitar corrección de futuras entregas
		Corregido en todos los switch encontrados

Sesion 4
========
2	estimateParams (Controller): no sería “estimateClass” o algo así?
		Corregido

14	¿Debería saber la vista de DistanceFactory? (no digo que no, es para reflexionar qué implica/conviene)
		Corregido. Ahora es el controlador el que usa la factoría para obtener la distancia deseada.

15	La notificación del modelo a las vistas, podría ser a observadores más genéricos, no solo a las vistas ViewKNN
		Corregido, ahora se hace uso de la interfaz vista en toda la clase model. Cualquier clase 'vista' que implemente la interfaz 'ViewInterface' podrá usar el modelo ya creado. Corregimos esta implementación en el resto de clases también. Añadimos los métodos necesarios a las interfaces para poder hacer uso de ellas como referencias.

16	setModel() podría reutilizarse en el constructor KNNView
		Corregido. Ahora el constructor ya no accede directamente a las variables, sino que hace uso de los métodos setters privados.

17	En controlador: view.getPuntoValue() mejor que pasarle el punto?
		Corregido, ahora el controlador recibe directamente el punto, ya no se lo pide a la vista.
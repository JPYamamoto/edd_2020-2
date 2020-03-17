# Proyecto 2. EDD.
## Juan Pablo Yamamoto Zazueta.

### Comentarios.
* El proyecto utiliza la clase Linea para representar cadenas de texto, que tienen la propiedad lineaLimpia.
* La clase Linea no implementa la interfaz Comparable, pues depende de un ordenador decidir cómo se realizará la comparación. En este caso utiliza OrdenadorLexicografico, que compara lineas de manera lexicográfica a partir de su representación normalizada.
* Las banderas y los nombres de los archivos pueden recibirse en cualquier orden, con la única restricción de que la bandera -o considera al argumento inmediato siguiente como el nombre del archivo al cuál escribir.
* Si se utiliza la bandera -r más de una vez, el programa regresa las líneas en reversa, del mismo modo que si la bandera -r se usara una única vez.
* Si se utiliza la bandera -o más de una vez, solo el último archivo de salida será considerado.

### Guía.

* Instalación:
```
$ mvn install
```

* Uso:
```
// Con la entrada estandar:
$ cat archivo.txt | java -jar target/proyecto1.jar

// Con un archivo existente:
$ java -jar target/proyecto1.jar archivo.txt

// Bandera -r (reversa):
$ java -jar target/proyecto1.jar archivo.txt -r
$ cat archivo.txt | java -jar target/proyecto1.jar -r

// Bandera -o <archivo salida> (escritura).
// Esta acción es destructiva, por lo que el archivo de salida será sobreescrito
// si ya existía previamente.
$ java -jar target/proyecto1.jar archivo.txt -o salida.txt
$ cat archivo.txt | java -jar target/proyecto1.jar -o salida.txt

// Ambas banderas pueden utilizarse en conjunto, sin un orden determinado:
$ java -jar target/proyecto1.jar archivo.txt -o salida.txt -r
$ cat archivo.txt | java -jar target/proyecto1.jar -o salida.txt -r
```


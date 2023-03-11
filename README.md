
<h1 align="center">
  Comparación de Algoritmos de Multiplicación de Matrices: Tradicional vs Strassen
  <br>
</h1>
  <br>
  </h1>
</p>
</p>



# Desarrollo del Reto :books:
## Funcionamiento :key:

El código se compone de un archivo:

* [MultiplicacionMatrices.java](src/MultiplicacionMatrices.java) 	:computer:

A continuación se describe la composición principal del mismo:

 `RandomMatrix(int size) `
 - Este método genera matrices de tamaño nxn con números aleatorios entre 0 y 9.
 - Para ello se utiliza la librería *java.util.Random*.
 - Va a tomar un entero como parámetro que indicará el tamaño de la matriz, crea una matriz de tales dimensiones y la va llenando con números aleatorios.
 
`comparison(int[][] matriz1, int[][] matriz2)`
- Este método compara si el resultado del método tradicional o naive y el de Strassen son iguales.
- Para ello, primero verifica si sus dimensiones son iguales, en caso de no serlo, retornará false.
- Luego, irá recorriendo ambas matrices y va a chequear que los elementos coincidan. Si en algún momento estos no coinciden retornará false.
- En caso de que todos los elementos comparados sean iguales, retornará true.

`tiempoNanosegundos(int[][] A, int[][] B, String algoritmo)`
- El método recibe las matrices generadas aleatoriamente junto con el tipo de algoritmo al cual se le contará el tiempo.
- Para ambos métodos, se utiliza *System.nanoTime()* para contabilizar el tiempo que tarda en ejecutarse la multiplicación de matrices.
- Lo anterior inicializando dos veces la función y contemplando la diferencia entre ambos momentos, obteniendo el tiempo total.
- Se trabaja con nanosegundos para mayor precisión pues los tiempos de ejecución pueden ser muy cortos.

### Nota: :scroll:
- Teniendo en cuenta que el algoritmo de Strassen solo funciona para matrices potencias de 2, el equipo tuvo la iniciativa de poder realizar multiplicaciones de Strassen con tamaños que no fuesen potencias de 2, por eso en el archivo se pueden encontrar métodos que verifican si es potencia de 2, agregar ceros a una matriz, eliminar los ceros, etc. Sin embargo esto se sigue trabajando para poder implementarlo a futuro.

## Gráfica y Análisis :bar_chart:
Para poder analizar a partir de que n es más rápido el algoritmo de Strassen que el tradicional, se tomaron los tiempos y se realizó una gráfica.
- Se almacenan los tiempos que tarda el tradicional y strassen para potencias que varían de n tamaños de 2 a la 1 hasta 2 a la 10 en dos arreglos de tipo long[].
- Se utiliza la librería *JFree* con *Jcommon* para graficar y mediante *JFrame* se puede visualizar como formato GUI.
- El gráfico implementado consise en un diagrama de dos ejes y dos barras donde cada barra corresponde a un algoritmo junto con su tiempo en nanosegundos, dependiendo del tamaño de la matriz.
 
### Output :globe_with_meridians:

![image](https://user-images.githubusercontent.com/103126242/224504487-6fdc1056-693d-4b0b-a2d1-15c438ed009b.png)
- Se implementó un pequeño menú dónde se pueden seleccionar distintos tamaños de matrices y realizar su respectiva multiplicación. Se muestran ambas matrices, la matriz resultante para ambos algoritmos y la comprobación de si son iguales o no.

![image](https://user-images.githubusercontent.com/103126242/224434557-8e3d3f9f-adb3-442b-b459-a5b6e17d62dd.png)

- Al graficar los tiempos hasta un n de 128 (2 a la 7), se evidencia que el algoritmo de Strassen toma más tiempo que el tradicional

![image](https://user-images.githubusercontent.com/103126242/224434446-a78269f0-9dfa-4516-be09-ceb3261c3592.png)

- Al graficar los tiempos hasta un n de 256 (2 a la 8), a pesar de que se evidencia que el algoritmo de Strassen se demora menos, en algunos tests este algoritmo resultó tomar más tiempo para este mismo tamaño n. Por lo cual en este tamaño no se garantiza que sea más rápido.

![image](https://user-images.githubusercontent.com/103126242/224433864-1cf5fca9-db85-4483-92fc-d56e85a8cb5e.png)

- Finalmente, al graficar los tiempos hasta un n de 512 (2 a la 9), se concluye que el algoritmo de Strassen es más rápido a partir de un n = 512 (2 a la 9).

<br>

### Nota: :warning:
- Para poder graficar es necesario tener instaladas las librerias de JFree y JCommon. 
- Se recomienda usar un editor de código como IntelliJ IDEA que permite instalar estas librerias desde Maven o directamente desde Java.
- Versiones utilizadas: jfreechart-1.0.19 / jcommon-1.0.23



### Autores :pushpin:
Realizado por [Jhonnathan Stiven Ocampo](https://github.com/jhothinnan) , [Manuela Castaño](https://github.com/manu0420) y [Lina Ballesteros](https://github.com/linasofi13) 




# AIS-Practica-3-2023
#### Autor(es): Lara Fernández Gutiérrez y Diego Picazo García 

[Nuestro Repositorio](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023)

[Aplicación Okteto (CAMBIAR ENLACE)](https://books-maes95.cloud.okteto.net/)

## FASE 1
### [Mayo 3, 2023]
### Workflow - 1 
Hemos creado el primer workflow en la carpeta .github/workflows, el cual recoge la funcionalidad de ejecutar los test unitarios y de integración cuando cogemos una rama con el patrón feature y la fusionamos con la rama develop. Para ello, hemos creado dichas ramas utilizando tanto la web de github, como el comando git branch, el cual nos permite crear nuevas ramas en local para poder desplegarlas. De forma secundaria hemos utilizado los comandos básicos para poder subir archivos (git add, git commit, git push). A su vez hemos desplegado los resultados del workflow en un artifact llamado Unitary & Integration Results en la ruta ./unit-integ-results

### Workflow - 2
Hemos creado el segundo workflow en la rama de **develop**, ya que nos pedían recoger la funcionalidad de pasar toda la batería de tests de la aplicación cada vez que se realice un git push a esta rama. De forma secundaria, hemos utilizado comandos básicos de git para poder crear y subir archivos (git add, git commit, git push), recalcando el git push de prueba realizado para poder comprobar que dicho workflow funciona correctamente.

Cabe destacar que se ha añadido a ambos flujos de trabajo así como a flujos futuros la generación de artifacts que almacenan de forma temporal los resultados de estos flujos de trabajo y por ende, los resultados de los tests, permitiendonos que si un flujo de trabajo falla, podamos coger el artifact y ver donde tenemos el error

### [Mayo 4, 2023]
### Workflow - 3
El tercer workflow nos ha costado un poco estructurarlo pero lo hemos logrado. Hemos separado al igual que en el workflow 2 las tareas de forma independiente, categorizadas como se pide como e2e, rest, etc, asi como finalmente hemos creado (cuando todos los tests han sido válidos), una imagen de docker con el comando "mvn spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=${{ secrets.DOCKERHUB_USERNAME }}/nombre de la imagen" y hemos iniciado sesión en docker con el comando "docker login" habiendo previamente salvaguardado las credenciales en secrets del repo de github, la hemos subido y hemos cerrado sesion con docker logout como medida de seguridad. Aún estamos a la espera de que funcione la parte del "schedule: crono" ya que hemos probado a cambiarlo por un push para ver si nuestro workflow era erróneo y ha funcionado correctamente, así que hemos programado el workflow para dentro de unas horas para probar y ver qué ocurre. Hemos utilizado la misma estructura que viene dada en las diapositivas.
[Imagen de docker creada sin crono](https://hub.docker.com/r/gu4re/books-reviewer)

### Workflow - 4
El cuarto workflow consiste en la ejecución de las pruebas unitarias y de integración en la rama release/**, lo cual ocurre automáticamente cada vez que se detecta un nuevo commit en dicha rama. 
Una vez más, hemos realizado la ejecución de las tareas de forma independiente, empezando por las pruebas de integración y siguiendo con las unitarias. Para ambas pruebas se guardan los resultados en artifacts llamados "integ-results.txt" y "unit-results.txt" respectivamente

### [Mayo 5, 2023]
### Workflow - 5
El quinto workflow consiste en el flujo de trabajo final compuesto por la fusión de una versión estable o release, que se encuentra en la rama develop, a la rama de producción (master). Hemos necesitado acotar el origen a develop tal cual se pedía en el enunciado con un breve script de bash, así como la necesidad de instalar la CLI de Okteto con el siguiente comando: "curl https://get.okteto.com -sSfL | sh". 
Posteriormente ejecutamos todas las pruebas, construimos la imagen de Docker y la subimos a DocherHub, de la misma forma que hacíamos en el workflow - 3, con la diferencia de que ahora utilizando los comandos "okteto context use https://cloud.okteto.com --token ${{ secrets.OKTETO_TOKEN }}" y "okteto deploy --wait --build", somos capaces de desplegar la imagen de Docker en un contenedor de Okteto, dándole portabilidad a través de un URL. Además, para el correcto funcionamiento de la subida en Okteto, hemos tenido que configurar el archivo "docker-compose.yml", el cual especifica el servicio de books-reviewer con la imagen de Docker a subir a Okteto. 
Hemos encontrado diversos problemas a la hora de ejecutar el workflow, en especial sobre Okteto, y es que nos hubiera gustado automatizar la imagen que especificamos en el archivo "docker-compose", ya que por mucho que especificabamos en el atributo image el nombre de usuaro de Docker como un secrets de GitHub, así como la versión a través de la ejecución por terminal con el comando "$(mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)", no es posible ya que hemos visto que no lo soporta. Además, vimos la documentación de GitHub e intentamos automatizar el proceso usando:
      
```yml      
environment:
  VERSION: $(mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)
  DOCKERUSERNAME: ${{ secrets.DOCKERHUB_USERNAME }}"
```
pero no fuimos capaces de conseguir que funcionara, por lo que tuvimos que escribir directamente la imagen (image: gu4re/books-reviewer:0.2.0-SNAPSHOT # Write it literally due to env problems)

### Workflow - 3
Tras muchos intentos, finalmente consultando la documentación de GitHub así como "StackOverflow", vimos que para el correcto funcionamiento del schedule, el workflow necesita estar en la rama default (master), por lo tanto hemos copiado el workflow que habíamos desarrollado en la rama develop a la rama master, consiguiendo así una correcta ejecución del test a la hora indicada (0:30 UTC, 2:30 en España)
[Imagen de docker](https://hub.docker.com/layers/gu4re/books-reviewer/dev-20230505/images/sha256-e1d2ceee77247a815a6e21a55b1ab6850d3e43e30bc83bf1dd609957a7242c36?context=repo)
[Última ejecución](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4889085337)

## FASE 2 - Desarrollo con (GitFlow/TBD)

Una vez creados los workflows y funcionando estos, pasamos a crear la nueva funcionalidad utilizando (Gitflow o TBD):

```
$ git clone https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023.git Practica3-AIS
```

Una vez clonado el repositorio, procedemos a abrirlo con nuestro IDE de preferencia, en nuestro caso IntelliJ. Hemos determinado que lo mejor, sería incluir esta nueva funcionalidad dentro de la clase BookDetail, concretamente dentro del método setDescription(String description), la cual define la descripción de un libro. Lo hemos establecido tal que así:

```java
// De esta forma añadimos 3 puntos al final cuando excede del maximo de longitud
public void setDescription(String description) {
        if (description.length() > MAX_DESCRIPTION_LENGTH)
            this.description = description.substring(0, MAX_DESCRIPTION_LENGTH - 3)
                    .concat("...");
        else
            this.description = description;
    }
```

Luego hemos añadido los cambios al repositorio remoto de esta forma:

```
$ git add .
$ git commit -m "añadida funcionalidad nueva de la descripcion"
$ git push -u origin feature/short-description
```

Posteriormente, hemos generado una [pull request](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/pull/5) para fusionar la rama que contiene la nueva funcionalidad (feature/short-description) a la rama de desarrollo (develop). La acción ha desencadenado un [workflow](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4892340725) el cuál ha validado la acción de forma satisfactoria. Hemos fusionado las ramas y hemos integrado los cambios dentro de la rama de desarrollo (develop). Sin embargo, cuando hemos fusionado las ramas, se ha desencadenado otro [workflow de commit](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4892349790) en la rama de desarrollo (develop) y al observarlo detenidamente, hemos visto que a pesar de que el workflow ha pasado satisfactoriamente, en los artifact resultantes, concretamente en el [Unitary Test Results](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/suites/12697183866/artifacts/681372753), hemos visto que se ha producido un error menor en uno de los tests, concretamente en el testEXTRA, que es uno de los que programamos en su momento para la práctica 1. Aun así el workflow ha pasado satisfactoriamente, ya que no se ha considerado un error crítico y no ha afectado por ende al comportamiento habitual de la aplicación. De todas formas, siguiendo el modelo de GitFlow, vamos a crear una rama llamada "hotfix/short-description", y hacer un hotfix o arreglo del error menor, para solucionar este problema antes de sacar una release, para aportar mayor estabilidad a la versión de producción que queremos sacar.

[17:35]
Finalmente hemos aplicado un pequeño hotfix a nuestro testEXTRA que era un test unitario y hemos conseguido solucionar el problema, tan solo debíamos evitar el NullPointerException que se generaba de no haber indicado una descripcion de prueba a la hora de generar un Book dentro del test, solventándolo de esta manera:

```java
@Test
@DisplayName("EXTRA: Checking good ID-book passed")
// EXTRA test checking what happens if a good ID is passed
public void testUNIT_EXTRA(){
      /* Given */
      Optional<BookDetail> bookDetailGood;
      // random valid id of an existing book
      when(openLibraryService.getBook("OL259010W"))
                  .thenReturn(
                              new OpenLibraryService.BookData(null, "/work/01",
                                          "hello-world", new Integer[1], new String[]{"HELLO WORLD"}));
      bookDetailGood = bookService.findById("OL259010W");
      Assertions.assertNotSame(Optional.empty(), bookDetailGood,
                  "bookDetail should not be empty");
}
```

Al haber fusionado la rama del hotfix a la rama de develop, como nos pasó anteriormente con la feature, nos ha generado dos flujos de trabajo, los cuales esta vez han pasado todos de forma satisfactoria, [un workflow ha sido generado por la pull request](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4895046354) y merge a la rama de desarrollo y el [otro al haber pusheado y commiteado en dicha rama](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4895058446), ambos con el hotfix incorporado. Para prácticamente terminar con el GitFlow, vamos a realizar unas cuantas correciones en el POM (indicar nuestros nombres en el artifactID) así como depurar un poco el docker-compose para después desplegar un release estable de nuestra aplicación a partir de develop con el nombre "release/0.2.0"

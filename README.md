# AIS-Practica-3-2023 <img src="https://imgur.com/lGBDlX3.png" alt="codeurjc-icon" width="70" height="68" align="left"/>
#### *Autor(es): Lara Fernández Gutiérrez y Diego Picazo García*

<p align="justify">El proposito de esta práctica es trabajar con el modelo de GitFlow desarrollado por Vincent Driessen en 2010, proporcionando a nuestra aplicación un marco de trabajo para la gestión de ramas y versiones, ayudando a manetener una estructura organizada y eficiente. Permite a los equipos de desarrollo que trabajen de manera más colaborativa y nos ayuda a garantizar que se construya un software de calidad.</p>

<img src="https://imgur.com/2YMrWjl.png" alt="okteto-icon" width="65" height="65" align="left"/>

## UTILIDADES 
<br>

Puede acceder pulsando al enlace a [nuestro repositorio](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023) de GitHub. Además, puede acceder al link donde está desplegada la aplicación de [Okteto](https://books-reviewer-gu4re.cloud.okteto.net/). Es importante conocer que la aplicación de Okteto la hemos desplegado y dejado en Running para que se pueda acceder y esté disponible el servicio, si hubiera cualquier problema no dude en escribirnos a [Lara Fernández](mailto:l.fernandezgu.2020@alumnos.urjc.es) o a [Diego Picazo](mailto:d.picazo.2020@alumnos.urjc.es).


## FASE 1 - WORKFLOWS <img src="https://imgur.com/OIDilkl.png" alt="workflow-icon" width="60" height="60" align="left"/> 
 <p align="right">[Mayo 3, 2023]</p>

### Workflow 1 - Unitary and Integration Testing [](#workflow1)

<p align="justify">Hemos creado el primer workflow en la carpeta <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/develop/.github/workflows">.github/workflows</a>, el cual recoge la funcionalidad de ejecutar los test unitarios y de integración cuando cogemos una rama con el patrón feature y la fusionamos con la rama develop. Para ello, hemos creado dichas ramas utilizando tanto la web de github, como el comando:</p>

```bash
$ git branch
```

<p align="justify">Que nos permite crear nuevas ramas en local para poder desplegarlas. De forma secundaria hemos utilizado los comandos básicos para poder subir archivos:</p>

```bash
$ git add .
$ git commit -m "mensaje"
$ git push -u origin "rama"
```

### Workflow 2 - Commit Testing [](#workflow2)

<p align="justify">Hemos creado el segundo workflow en la rama de <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/develop">develop</a>, ya que nos pedían recoger la funcionalidad de pasar toda la batería de tests de la aplicación cada vez que se realice un <em>git push</em> a esta rama. De forma secundaria, hemos utilizado comandos básicos de git para poder crear y subir archivos:</p>

```bash
$ git add .
$ git commit -m "mensaje"
$ git push -u origin "rama"
```
      
<p align="justify">Recalcando el git push de prueba realizado para poder comprobar que dicho workflow funciona correctamente. Cabe destacar que se ha añadido a ambos flujos de trabajo así como a flujos futuros la generación de artifacts que almacenan de forma temporal los resultados de estos flujos de trabajo y por ende, los resultados de los tests, permitiendonos que si un flujo de trabajo falla, podamos coger el artifact y ver donde tenemos el error.</p>
<p align="right">[Mayo 4, 2023]</p>
 
### Workflow 3 - Nightly Testing [](#workflow3)

<p align="justify">El tercer workflow nos ha costado un poco estructurarlo pero lo hemos logrado. Hemos separado al igual que en el <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/edit/develop/README.md#workflow-2">workflow 2</a> las tareas de forma independiente, categorizadas como se pide como e2e, rest, etc, asi como, finalmente hemos creado (cuando todos los tests han sido válidos), una imagen de docker con el comando:</p>

```bash
# Creacion de la imagen
$ mvn spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=${{ secrets.DOCKERHUB_USERNAME }}/nombre de la imagen 
# Iniciamos sesion en docker
$ docker login --username="${{ secrets.DOCKERHUB_USERNAME }}" --password="${{ secrets.DOCKERHUB_TOKEN }}" 
# Subimos la imagen a DockerHub
$ docker push "${{ secrets.DOCKERHUB_USERNAME }}"/books-reviewer:dev-$(date +%Y%m%d) 
# Cerramos sesion en Docker por seguridad
$ docker logout 
```
      
<p align="justify">Aún estamos a la espera de que funcione la parte del "schedule: crono" ya que hemos probado a cambiarlo por un push para ver si nuestro workflow era erróneo y ha funcionado correctamente, así que hemos programado el workflow para dentro de unas horas para probar y ver qué ocurre. Hemos utilizado la misma estructura que viene dada en las diapositivas, sin éxito.</p>

### Workflow 4 - Release Testing [](#workflow4)

<p align="justify">El cuarto workflow consiste en la ejecución de las pruebas unitarias y de integración en la rama de prueba <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/release/workflow4-test">release/workflow4-test</a>, lo cual ocurre automáticamente cada vez que se detecta un nuevo commit en dicha rama. Una vez más, hemos realizado la ejecución de las tareas de forma independiente, empezando por las pruebas de integración y siguiendo con las unitarias. Para ambas pruebas se guardan los resultados en artifacts llamados <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/suites/12670222690/artifacts/679363787">integ-results.txt</a> y <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/suites/12670222690/artifacts/679363788">unit-results.txt</a> respectivamente.</p>
<p align="right">[Mayo 5, 2023]</p>

### Workflow 5 - Deployment Testing [](#workflow5)
<p align="justify">El quinto workflow consiste en el flujo de trabajo final compuesto por la fusión de una versión estable o release, que se encuentra en la rama <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/develop">develop</a>, a la rama de producción <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/master">master</a>. Hemos necesitado acotar el origen a <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/develop">develop</a> tal cual se pedía en el enunciado con un breve script de bash, así como la necesidad de instalar la CLI de Okteto:</p>
      
```yml      
- name: Verifying source branch
        run: |
             if [[ "${{ github.event.pull_request.head.ref }}" != "develop" ]]; then
              echo "The pull request source branch is not 'develop'. Exiting."
              exit 1
             fi
- name: Installing Okteto CLI
  run: |
    curl https://get.okteto.com -sSfL | sh
```
  
<p align="justify">Posteriormente ejecutamos todas las pruebas, construimos la imagen de Docker y la subimos a DocherHub, de la misma forma que hacíamos en el <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/edit/develop/README.md#workflow-3">workflow 3</a>, con la diferencia de que ahora utilizando los siguientes comandos:</p>

```bash
$ okteto context use https://cloud.okteto.com --token ${{ secrets.OKTETO_TOKEN }}
$ okteto deploy --wait --build
```
      
<p align="justify">Somos capaces de desplegar la imagen de Docker en un contenedor de Okteto, dándole portabilidad a través de una URL. Además, para el correcto funcionamiento de la subida en Okteto, hemos tenido que configurar el archivo <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/blob/develop/docker-compose.yml">docker-compose.yml</a>, el cual especifica el servicio de books-reviewer como la imagen de Docker a subir a Okteto. 
Hemos encontrado diversos problemas a la hora de ejecutar el workflow, en especial sobre Okteto, y es que nos hubiera gustado automatizar la imagen que especificamos en el archivo <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/blob/develop/docker-compose.yml">docker-compose.yml</a>, ya que por mucho que especificabamos en el atributo <em>image</em> el nombre de usuaro de Docker como un secrets de GitHub, así como la versión a través de la ejecución por terminal con el comando:</p>
      
```bash
# Comando utilizado en varios workflows para obtener la versión actual del proyecto ubicada en el pom.xml
$(mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)
```
<p align="justify">No es posible ya que hemos visto que no lo soporta. Además, vimos la documentación de GitHub e intentamos automatizar el proceso usando:</p>
      
```yml  
# Intento a la hora de especificarlo directamente
image: ${{ secrets.DOCKERHUB_USERNAME }}/books-reviewer:$(mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)
environment:
  VERSION: $(mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)
  DOCKERUSERNAME: ${{ secrets.DOCKERHUB_USERNAME }}
# Intento usando environment
image: ${DOCKERUSERNAME}/books-reviewer:${VERSION}
```

<p align="justify">Pero no fuimos capaces de conseguir que funcionara, por lo que tuvimos que escribir directamente la imagen:</p>

```yml      
version: '3'

services:
  books-reviewer:
    image: gu4re/books-reviewer:0.2.0-SNAPSHOT # Escrito literalmente debido a problemas con 'environment'
    ports:
      - 8080:8080
```

### RELEASE Workflow 3 - Nightly Testing [](#workflow3-2)

<p align="justify">Tras muchos intentos, finalmente consultando la <a href="https://docs.github.com/en/actions/using-workflows/events-that-trigger-workflows#schedule">documentación de GitHub</a> así como <a href="https://stackoverflow.com/questions/63436541/github-action-workflow-schedule-not-working-on-non-default-branch">StackOverflow</a>, vimos que para el correcto funcionamiento del schedule, el workflow necesita estar en la rama <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/master">master</a>, por lo tanto hemos copiado el workflow que habíamos desarrollado en la rama <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/develop">develop</a> a la rama <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/master">master</a>, consiguiendo así una <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4889085337">correcta ejecución del test</a> a la hora indicada (0:30 UTC, 2:30 en España). La imagen que generó dicha ejecución de DockerHub la podemos encontrar <a href="https://hub.docker.com/layers/gu4re/books-reviewer/dev-20230505/images/sha256-e1d2ceee77247a815a6e21a55b1ab6850d3e43e30bc83bf1dd609957a7242c36?context=repo">aquí</a>.</p>

## FASE 2 - Desarrollo con (GitFlow/TBD) <img src="https://gitforwindows.org/img/gwindows_logo.png" alt="workflow-icon" width="60" height="60" align="left"/>

<p align="justify">Una vez creados los workflows y funcionando estos, pasamos a crear la nueva funcionalidad utilizando (Gitflow o TBD):</p>

```bash
# Clonamos el repo en local
$ git clone https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023.git Practica3-AIS
# Abrimos la aplicacion con IntelliJ
$ cd Practica3-AIS
$ idea .
```

<p align="justify">Hemos determinado que lo mejor, sería incluir esta nueva funcionalidad dentro de la clase <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/blob/develop/src/main/java/es/codeurjc/ais/book/BookDetail.java">BookDetail</a>, concretamente dentro del método <em>setDescription(String description)</em>, el cuál define la descripción de un libro. Lo hemos establecido así:</p>

```java
// De esta forma cuando una descripcion excede el limite, nos quedamos con los 947 caracteres primeros
// Los 3 ultimos hasta 950 los rellenamos con '...'                                                  
public void setDescription(String description) {
        if (description.length() > MAX_DESCRIPTION_LENGTH)
            this.description = description.substring(0, MAX_DESCRIPTION_LENGTH - 3)
                    .concat("...");
        else
            this.description = description;
    }
```

<p align="justify">Luego hemos añadido los cambios al <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023">repositorio remoto</a> de esta forma:</p>

```
$ git add .
$ git commit -m "añadida funcionalidad nueva de la descripcion"
$ git push -u origin feature/short-description
```

<p align="justify">Posteriormente, hemos generado una <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/pull/5">pull request</a> para fusionar la rama que contiene la nueva funcionalidad <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/feature/short-description">feature/short-description</a> a la rama de desarrollo <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/develop">develop</a>. La acción ha desencadenado un <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4892340725">workflow</a> el cuál ha validado la acción de forma satisfactoria. Hemos fusionado las ramas y hemos integrado los cambios dentro de la rama de desarrollo <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/develop">develop</a>. Sin embargo, cuando hemos fusionado las ramas, se ha desencadenado otro <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4892349790">workflow de commit</a> en la rama de desarrollo <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/develop">develop</a> y al observarlo detenidamente, hemos visto que a pesar de que el workflow ha pasado satisfactoriamente, en los artifact resultantes, concretamente en el <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/suites/12697183866/artifacts/681372753">Unitary Test Results</a>, hemos visto que se ha producido un error menor en uno de los tests, concretamente en el <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/blob/feature/short-description/src/test/java/es/codeurjc/ais/unitary/ReviewServiceUnitaryTest.java#L76">testEXTRA</a>, que es uno de los que programamos en su momento para la primera práctica. Aun así el workflow ha pasado satisfactoriamente, ya que el propio flujo no lo ha considerado un error crítico y no ha afectado por ende al comportamiento habitual de la aplicación. De todas formas, siguiendo el modelo de GitFlow, vamos a crear ahora una rama llamada <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/hotfix/short-description">hotfix/short-description</a>, y hacer un hotfix o arreglo del error menor, para solucionar este problema antes de sacar una release, para aportar mayor estabilidad a la versión de producción que queremos sacar.
Finalmente hemos aplicado un pequeño hotfix a nuestro <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/blob/feature/short-description/src/test/java/es/codeurjc/ais/unitary/ReviewServiceUnitaryTest.java#L76">testEXTRA</a> que era un test unitario y hemos conseguido solucionar el problema, tan solo debíamos evitar el <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/NullPointerException.html">NullPointerException</a> que se generaba de no haber indicado una descripcion de prueba a la hora de generar un Book dentro del test, solventándolo de esta manera:</p>

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
                              // hello-world is now the new description
                              "hello-world", new Integer[1], new String[]{"HELLO WORLD"}));
      bookDetailGood = bookService.findById("OL259010W");
      Assertions.assertNotSame(Optional.empty(), bookDetailGood,
                  "bookDetail should not be empty");
}
```

## BOOKS REVIEWER v0.2.0 <img src="https://imgur.com/HoDIpCZ.png" alt="workflow-icon" width="60" height="60" align="left"/>

<p align="justify">Al haber fusionado la <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/hotfix/short-description">rama del hotfix</a> a la rama de <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/develop">develop</a>, como nos pasó anteriormente con la feature, nos ha generado dos flujos de trabajo, los cuales esta vez han pasado todos de forma satisfactoria, <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4895046354">un workflow ha sido generado por la pull request</a> y merge a la rama de desarrollo y el <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4895058446">otro al haber pusheado y commiteado en dicha rama</a>, ambos con el <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/blob/develop/src/test/java/es/codeurjc/ais/unitary/ReviewServiceUnitaryTest.java#L83">hotfix incorporado</a>. Para prácticamente terminar con el GitFlow, vamos a realizar unas cuantas correciones en el <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/blob/develop/pom.xml#L7">POM</a> (indicar nuestros nombres en el artifactID) así como depurar un poco el <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/blob/develop/docker-compose.yml">docker-compose</a> para después desplegar un release estable de nuestra aplicación a partir de <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/develop">develop</a> con el nombre <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/release/0.2.0">release/0.2.0</a>. Por último, hemos descomentado la parte de <em>Generating Selenium Artifact</em> de todos los workflows (hasta ahora comentada debido a la limitacion del plan gratuito con el almacenamiento de los artifact), y hemos ajustado la versión de nuestra aplicacion en el <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/blob/release/0.2.0/pom.xml#L8">POM</a> asi como el <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/blob/release/0.2.0/docker-compose.yml#L5">docker-compose</a> y hemos visto que no tenemos problemas en cuanto al release gracias al <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4896277778">flujo de trabajo ejecutado</a>, por lo tanto, vamos a fusionar el release con la rama de desarrollo <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/develop">develop</a> abriendo una pull request, y respetando dichos workflows que van a ser ejecutados antes de llevar a cabo este proceso.
Acabamos de agregar el nuevo release v0.2.0 a la rama de desarrollo <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/develop">develop</a> lo cual ha desencadenado dos workflows, <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4896412483">uno por su parte proveniente de la pull request</a> y <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4896425483">otro al haber pusheado</a> y commiteado en la rama de desarrollo <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/develop">develop</a>. Vamos a desplegar finalmente la aplicación en la rama de producción <a href="https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/tree/master">master</a> y a hacer algunos commit finales para retocar y dejarlo listo para desarrollar en el futuro una posible versión v0.3.0.
Finalmente, hemos integrado la aplicación en la rama de producción, por lo que aquí dejamos unos enlaces finales para que se pueda obtener tanto los artifacts como las imagenes finales de la aplicación:</p>

<table align="center" border="2" cellspacing="0">
  <td>
  
  - [Desplegando Books Reviewer v0.2.0 en master](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/actions/runs/4896503017)
  - [Aplicación desplegada en Okteto](https://books-reviewer-gu4re.cloud.okteto.net/)
  - [Imagen de Docker v0.2.0](https://hub.docker.com/layers/gu4re/books-reviewer/0.2.0/images/sha256-d912eed88c1b18f38ceb5a9a1bd2b9520f89167a47681fb617d4aaeaed20bb7f?context=repo)
  - [ARTIFACT: Docker Push Results](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/suites/12708186580/artifacts/682184938)
  
  </td>
  <td>
  
  - [ARTIFACT: Integration Testing Results](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/suites/12708186580/artifacts/682184941)
  - [ARTIFACT: REST Testing Results](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/suites/12708186580/artifacts/682184942)
  - [ARTIFACT: Selenium Testing Results](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/suites/12708186580/artifacts/682184944)
  - [ARTIFACT: Unitary Testing Results](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023/suites/12708186580/artifacts/682184946)
  
  </td>
</table>

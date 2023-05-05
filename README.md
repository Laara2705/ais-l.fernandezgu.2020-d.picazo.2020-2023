# AIS-Practica-3-2023
#### Autor(es): Lara Fernández Gutiérrez y Diego Picazo García 

[Nuestro Repositorio](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023)

[Aplicación Okteto (CAMBIAR ENLACE)](https://books-maes95.cloud.okteto.net/)

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

## Desarrollo con (GitFlow/TBD)

Una vez creados los workflows y funcionando estos, pasamos a crear la nueva funcionalidad utilizando (Gitflow o TBD):

```
$ git clone ...
```

....

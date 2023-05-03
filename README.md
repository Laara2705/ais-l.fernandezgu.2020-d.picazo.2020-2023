# AIS-Practica-3-2023
#### Autor(es): Lara Fernández Gutiérrez y Diego Picazo García 

[Nuestro Repositorio](https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023)

[Aplicación Okteto (CAMBIAR ENLACE)](https://books-maes95.cloud.okteto.net/)

### Workflow - 1 [Mayo 3, 2023]
Hemos creado el primer workflow en la carpeta .github/workflows el cual recoge la funcionalidad de ejecutar los test unitarios y de integración cuando cogemos una rama con el patron feature y la fusionamos con la rama develop. Para ello hemos creado dichas ramas utilizando o bien la web de github, como el comando git branch, el cual nos permite crear nuevas ramas en local para poder desplegarlas. De forma secundaria hemos utilizado los comandos básicos para poder subir archivos (git add, git commit, git push). A su vez hemos desplegado los resultados del workflow en un artifact llamado Unitary & Integration Results en la ruta ./unit-integ-results

### Workflow - 2 [Mayo 3, 2023]
Hemos creado el segundo workflow en la rama de **develop**, ya que nos pedían recoger la funcionalidad de pasar toda la batería de tests de la aplicación cada vez que se realice un git push a esta rama. De forma secundaria, hemos utilizado comandos básicos de git para poder crear y subir archivos (git add, git commit, git push), recalcando el git push de prueba realizado para poder comprobar que dicho workflow funciona correctamente.

Cabe destacar que se ha añadido a ambos flujos de trabajo así como a flujos futuros la generación de artifacts que almacenan de forma temporal los resultados de estos flujos de trabajo y por ende, los resultados de los tests, permitiendonos que si un flujo de trabajo falla, podamos coger el artifact y ver donde tenemos el error

## Desarrollo con (GitFlow/TBD)

Una vez creados los workflows y funcionando estos, pasamos a crear la nueva funcionalidad utilizando (Gitflow o TBD):

```
$ git clone ...
```

....

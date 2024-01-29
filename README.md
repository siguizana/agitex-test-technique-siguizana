# GUIDE TEST TECHNIQUE JAVA

### Description
Il s'agit d'une application Spring boot 3.2.2.
L'application consiste à importer des fichiers multi format(txt, json, csv, xls, xml)
### Prérequis
* JAVA 17
* MAVEN 3.8.6
### Lancement du projet

* [Cloner le projet à travers le lien ci-dessous](https://github.com/siguizana/agitex-test-technique-siguizana)
```
https://github.com/siguizana/agitex-test-technique-siguizana
```
* Lancer la commande ci-dessous pour contruire l'image.
```
    docker build -t spring-boot-test-image .
```
* Lancer la commande suivante pour exécuter l'image
```
  docker run -p 2024:2024 spring-boot-test-image
```
### Test après le demarrage du projet
* [Taper ce lien dans un navigateur au choix](http://localhost:2024/swagger-ui/index.html)
````
http://localhost:2024/swagger-ui/index.html
````
* Vous avez ainsi accès au swagger pour tester les différentes APIs du test technique.

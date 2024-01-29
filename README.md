# GUIDE TEST TECHNIQUE JAVA

### Description
Il s'agit d'une application Spring boot 3.2.2 en JAVA 17.
L'application consiste à importer des fichiers multi format(txt, json, csv, xls, xml).
L'application utilse une base de données h2. 

### Lancement du projet

* Cloner le projet à travers le lien ci-dessous
```
https://github.com/siguizana/agitex-test-technique-siguizana
```
* Lancer la commande ci-dessous pour contruire l'image.
Rasurer vous que vous avaez lancer doker avant de lancer la commande.
```
docker build -t spring-boot-test-image .
```
* Lancer la commande suivante pour exécuter l'image
```
docker run -p 2024:2024 spring-boot-test-image
```
### Test après le demarrage du projet
* Taper ce lien dans un navigateur au choix
````
http://localhost:2024/swagger-ui/index.html
````
* Les fichiers de test sont dans le repertoire ```/resources/files ``` du projet.
* Vous avez ainsi accès au swagger pour tester les différentes APIs du test technique.

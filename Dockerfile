# Étape de construction
FROM maven:3.8.6-eclipse-temurin-17 as builder

# Définition du répertoire de travail dans l'image
WORKDIR /app

# Copie du fichier pom.xml pour tirer parti du cache des dépendances
COPY pom.xml .

# Téléchargement des dépendances Maven
RUN mvn dependency:go-offline

# Copie du reste des sources
COPY src src

# Construction du projet
RUN mvn package

# Deuxième étape pour l'image finale
FROM openjdk:17

# Définition du répertoire de travail dans l'image
WORKDIR /app

# Exposition du port sur lequel l'application Spring Boot écoute
EXPOSE 2024

# Copie du JAR construit de l'application Spring Boot depuis l'étape précédente
COPY --from=builder /app/target/test-0.0.1-SNAPSHOT.jar /app/test-0.0.1-SNAPSHOT.jar

# Commande pour lancer l'application Spring Boot au démarrage du conteneur
CMD ["java", "-jar", "test-0.0.1-SNAPSHOT.jar"]

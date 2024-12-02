# Projet JEE

<div>
  <img src="img/CYTechLogo.png" style="width: 40%;">
</div>

## 📋 Projet

Cette application de gestion de scolarité est une plateforme web développée en Java permettant aux administrateurs, enseignants et étudiants de gérer les informations académiques.

Pour cela, elle dispose de toutes les fonctionnalités élémentaires nécessaires à la gestion des étudiants, des cours, des inscriptions et des résultats, tout en ayant une interface intuitive accessible grâce à l'authentification et à la gestion des rôles des utilisateurs.

L'application permet par exemple aux administrateurs d'ajouter ou de modifier les informations des étudiants et des enseignants, d'attribuer des cours aux enseignants ou encore d'inscrire des étudiants. Les enseignants peuvent saisir les notes des étudiants et gérer leurs inscriptions, tandis que les étudiants ont la possibilité de s'inscrire à des cours, de consulter leurs résultats et moyennes, ainsi que de générer des relevés de notes.<br>
Toutes ces informations sont stockées dans une base de données SQL, gérée grâce à Hibernate pour la persistance, et manipulées via une architecture MVC intégrant des JSP pour l'affichage et des servlets pour la logique métier.

En plus des fonctionnalités de gestion classique, elle contient également un système de notifications par email pour tenir les étudiants informés des mises à jour liées à leurs inscriptions ou aux notes publiées.

Cette application est déployée sur un serveur Apache Tomcat et sa deuxième version s'appuie sur le framework Spring Boot pour offrir une structure modulaire et évolutive.

### 👀 Aperçu

<div align="center">
  <img src="img/platform_presentation.gif" />
</div>


## 🚀 Lancer le site internet

Pour la partie 2 - Hibernate il faut installer la branche "Initial" et la télécharger. Ouvrez le fichier télechargé en tant que projet IntelliJ et faîtes ces changements avant de lancer : 
- Ajoutez une version d'Apache Tomcat
- Changez dans src/main/ressource/hibernate.cfg.xml l'identifiant et le mot de passe pour accéder à la base de données
- Si vous voulez une base de données remplie, exéctuez le code dump.sql présent dans la branche README

Vous êtes désormais prêt à lancer l'application Tomcat - sur la page de login connectez-vous en tant qu'administrateur pour créer des étudiants et professeurs. L'administrateur de base est 'admin.admin@cy-tech.fr' avec pour mot de passe 'admin'.

Pour la partie 3 - Spring Boot il faut installer la branche "Spring-Boot" et la télécharger. Ouvrez le fichier téléchargé en tant que projet IntelliJ et faîtes ces changements avant de lancer :<br>
Il faut se rendre sur le fichier src/main/resources/application.properties et changez : 
- spring.datasource.url= (c'est l'URL de connexion à la base de données)
- spring.datasource.username= (nom d'utilisateur associé à la base de données (souvent root))
- spring.datasource.password= (mot de passe de l'utilisateur de la base de données)
- spring.mail.username= (email)
- spring.mail.password= (mot de passe du mail pour un compte Google à double authentification, il faut générer un mot de passe d'application)
- Il faut exécuter le script sql pour faire le prmeier admin le mot de passe est admin et le mail est admin.admin@cy-tech.fr
Ensuite pour lancer l'application, il faut se rendre sur l'URL : http://localhost:8080/login

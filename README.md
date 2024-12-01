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


## 📋 Utilisation

Pour la partie 2 - Hibernate il faut installer la branche "Initial" et la telecharger. Ouvrire le fichier telecharger en tand que projet intelji eet faire ces changement avant de lancer : 
- ajouter une version de tomcat
- changer dans src/main/ressource/hibernate.cfg.xml (changer l'identifiant et mot de passe pour acceder a la base de donnee)
- si vous voulais une base de donner rempli exectuer le code dans SQL.txt dans la branche README
puis vous ete pret a lancer l'application tomcat - sur la page de login connecter vous sur un admin pour crée des etudiants et professeurs. l'admin de basse est admin.admin@cy-tech.fr mot de pass "admin"

Pour la partie 3 - Springboot if faut installer la branche "Spring-Boot" et la telecharger. Ouvrire le fichier telecharger en tand que projet intelji eet faire ces changement avant de lancer : 

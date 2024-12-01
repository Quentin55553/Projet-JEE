CREATE DATABASE gestion_scolarite;
USE `gestion_scolarite`;

CREATE TABLE `Personne` (
    `id_personne` VARCHAR(255) NOT NULL,
    `nom` VARCHAR(255) NOT NULL,
    `prenom` VARCHAR(255) NOT NULL,
    `date_naissance` DATE NULL DEFAULT NULL,
    `contact` VARCHAR(255) NULL DEFAULT NULL,
    `role` INT NULL DEFAULT NULL,
    `password` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id_personne`)
) ;

CREATE TABLE `Cours` (
    `id_cours` INT NOT NULL AUTO_INCREMENT,
    `nom_cours` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL DEFAULT NULL,
    `date_debut` DATE NULL DEFAULT NULL,
    `date_fin` DATE NULL DEFAULT NULL,
    `id_enseignant` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id_cours`),
    INDEX `id_enseignant` (`id_enseignant`),
    CONSTRAINT `cours_ibfk_1`
        FOREIGN KEY (`id_enseignant`)
            REFERENCES `Personne` (`id_personne`)
) ;

CREATE TABLE `Inscription` (
`id_inscription` INT NOT NULL AUTO_INCREMENT,
`date_inscription` DATE NULL DEFAULT NULL,
`etat` INT NULL DEFAULT NULL,
`description_refus` VARCHAR(255) NULL DEFAULT NULL,
`id_etudiant` VARCHAR(255) NULL DEFAULT NULL,
`id_cours` INT NOT NULL,
PRIMARY KEY (`id_inscription`),
INDEX `FK_cours` (`id_cours`),
INDEX `id_etudiant` (`id_etudiant`),
CONSTRAINT `inscription_ibfk_1`
    FOREIGN KEY (`id_etudiant`)
        REFERENCES `Personne` (`id_personne`),
CONSTRAINT `FK_cours`
    FOREIGN KEY (`id_cours`)
        REFERENCES `Cours` (`id_cours`)
) ;

CREATE TABLE `Resultat` (
`id_resultat` INT NOT NULL AUTO_INCREMENT,
`note` DOUBLE NULL DEFAULT NULL,
`id_etudiant` VARCHAR(255) NULL DEFAULT NULL,
`id_cours` INT NULL DEFAULT NULL,
PRIMARY KEY (`id_resultat`),
INDEX `id_etudiant` (`id_etudiant`),
INDEX `id_cours` (`id_cours`),
CONSTRAINT `resultat_ibfk_1`
    FOREIGN KEY (`id_etudiant`)
        REFERENCES `Personne` (`id_personne`),
CONSTRAINT `resultat_ibfk_2`
    FOREIGN KEY (`id_cours`)
        REFERENCES `Cours` (`id_cours`) ON DELETE CASCADE
) ;

INSERT INTO `Personne` (`id_personne`, `nom`, `prenom`, `date_naissance`, `contact`, `role`, `password`)
VALUES
    ('dupont.jean@cy-tech.fr', 'Dupont', 'Jean', '1980-01-15', 'dupont.jean@gmail.com', 2, 'password123'),
    ('durand.pierre@cy-tech.fr', 'Durand', 'Pierre', '2000-03-20', 'durand.pierre@gmail.com', 3, 'password456'),
    ('martin.sophie@cy-tech.fr', 'Martin', 'Sophie', '2001-11-05', 'martin.sophie@gmail.com', 3, 'password789'),
    ('admin.admin@cy-tech.fr', 'Admin', 'Admin', '1975-01-01', 'admin.admin@gmail.com', 1, 'admin');

INSERT INTO `Cours` (`nom_cours`, `description`, `date_debut`, `date_fin`, `id_enseignant`)
VALUES
    ('Mathématiques', 'Cours de mathématiques niveau 1', '2024-09-01', '2024-12-01', 'dupont.jean@cy-tech.fr'),
    ('Informatique', 'Introduction à la programmation', '2024-09-01', '2024-12-01', 'dupont.jean@cy-tech.fr');

INSERT INTO `Inscription` (`date_inscription`, `etat`, `description_refus`, `id_etudiant`, `id_cours`)
VALUES
    ('2024-08-20', 1, NULL, 'durand.pierre@cy-tech.fr', 1),
    ('2024-08-22', 1, NULL, 'durand.pierre@cy-tech.fr', 2),
    ('2024-08-25', 1, NULL, 'martin.sophie@cy-tech.fr', 1),
    ('2024-08-27', 1, NULL, 'martin.sophie@cy-tech.fr', 2);

INSERT INTO `Resultat` (`note`, `id_etudiant`, `id_cours`)
VALUES
    (15.5, 'durand.pierre@cy-tech.fr', 1),
    (18.0, 'durand.pierre@cy-tech.fr', 2),
    (14.0, 'martin.sophie@cy-tech.fr', 1),
    (16.5, 'martin.sophie@cy-tech.fr', 2);

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


# Projet ObjetVole
Elaboration d'une appli de verification du statut (volé/non volé) d'un appareil/objet ( téléphone, odrinateur...etc) acheté en seconde main.

**ObjetVole** est une application Java développée pour gérer les objets volés. Elle permet :
- La déclaration des vols.
- La gestion des objets.
- La notification des propriétaires lorsque leurs objets volés sont retrouvés.

Cette application utilise **JavaFX** pour l'interface utilisateur et intègre des services de notification par email(assez rude à implementer , ayant necessité l'appui de tutos et autres recherches sur le net), pour informer les utilisateurs.

## Fonctionnalités
1. **Inscription et Connexion** :
   - Les utilisateurs peuvent s'inscrire, se connecter et accéder à leur espace personnel.
   - Les informations des utilisateurs sont gérées en toute sécurité.

2. **Gestion des objets** :
   - Déclaration des objets volés.
   - Mise à jour des informations des objets.
   - Vérification du statut des objets (retrouvé, perdu).

3. **Notifications** :
   - Envoi automatique d'emails lorsque les objets volés sont retrouvés.
   - Possibilité de consulter les notifications dans l'application.

4. **Modification des informations utilisateur** :
   - Les utilisateurs peuvent mettre à jour leurs informations personnelles.

## Technologies utilisées
- **Java 11** ou versions ultérieures.
- **JavaFX** pour l'interface utilisateur.
- **MySQL** pour la base de données.
- **SMTP (via Gmail)** pour le service de notification par email.
- **Git** pour le contrôle de version.

## Installation
1. **Cloner le dépôt** :
   ```bash
   git clone https://github.com/BrianBrusly/ObjetVole.git

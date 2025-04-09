# TP POO Kennedy Projet ObjetVole
# HEUDEP DJANDJA Brian B 3GI 2025
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
   - Vérification du statut des objets (retrouvé, volé).

3. **Notifications** :
   - Envoi automatique d'emails lorsque les objets volés sont retrouvés.
   - Possibilité de consulter les notifications dans l'application.

4. **Modification des informations utilisateur** :
   - Les utilisateurs peuvent mettre à jour leurs informations personnelles.

## Technologies utilisées
- **Java 21/23** ou versions ultérieures , sur l'IDE Intellij
- **JavaFX** pour l'interface utilisateur.
- **MySQL** pour la base de données (via le site pHpAdmin)
- **SMTP (via Gmail)** pour le service de notification par email.
- **Git** pour le contrôle de version.

## Installation
1. **Cloner le dépôt** :
   ```bash
   git clone https://github.com/BrianBrusly/ObjetVole.git

2. **Configurer la base de données** :

    Créez une base de données MySQL appelée Items.

    Importez les fichiers SQL dans la base pour configurer les tables (config, Utilisateurs, Items, DeclarationVol.).

    Mettez à jour les identifiants de connexion dans les classes de persistance (BDconnexionUser, BDconnexionItem, etc.).

3. **Installer les dépendances** :

    Ajoutez JavaFX à votre projet via votre IDE (IntelliJ, Eclipse).

    Installez les bibliothèques nécessaires pour la gestion des emails (JavaMail (javax.mail.jar et jakarta.jar ..etc).
    JDBC pour la base de données c'est à dire :Java Database Connectivity (JDBC)

5. **Lancer l'application** :

    Exécutez la classe principale Main.java pour démarrer l'application !

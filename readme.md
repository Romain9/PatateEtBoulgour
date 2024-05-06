# Instruction

Le projet s'occupe de créer les tables mais pour le faire fonctionner il vous faut:
- Créer une base de donnée (mariadb)
- Mettre les informations de connexion de la base dans config.env
  - DATABASE_NAME=
  - DATABASE_USERNAME=
  - DATABASE_PASSWORD=
  - DATABASE_PORT=
- Utiliser le profile "firstRun" dans applications.properties (ligne spring.profiles.active)
- Changer le profile pour "prod" après lancement.

A noter :  le profil dev permet aussi de recréer les tables et réinsérer les données mais en affichant les requêtes,
Le profile prod conserve la base et ne valide que le schéma. Les requêtes sont masquées et les erreurs désactivées. 

Enfin, la base contient 3 utilisateurs:
- Admin un SUPER ADMIN (mdp: admin501)
- michel2 un utilisateur lambda (mdp: mot de passe)
- jaqueline.roseau un utilisateur lambda (mdp: 123456789)
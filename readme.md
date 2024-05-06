# Instruction

Le projet s'occupe de créer les tables mais pour le faire fonctionner il vous faut:
- Créer une base de donnée
- Mettre les informations de connexion de la base dans config.env

Il est conseillé pour un premier lancement de mettre le profil firstRun dans applications.properties (ligne spring.profiles.active).
Pour les prochains lancement, le profil dev permet de recréer les tables et réinsérer les données et le profil prod permet de garder la base de données et ses changements d'un lancement à l'autre.

Enfin, la base contient 3 utilisateurs:
- Admin un SUPER ADMIN (mdp: admin501)
- michel2 un utilisateur lambda (mdp: mot de passe)
- jaqueline.roseau un utilisateur lambda (mdp: 123456789)
import json
import os
import sqlite3

# Chemin vers le fichier de base de données temporaire
database_file = 'patate_et_boulgour_temp.db'

# Chemin vers le dump de base de données
database_dump = '../init_db_activites.sql'

# Vérifier si le fichier de base de données existe
if os.path.exists(database_file):
    # Supprimer le fichier de base de données
    os.remove(database_file)

# Connexion à la base de données
conn = sqlite3.connect(database_file)
c = conn.cursor()

# Charger les données JSON à partir du .json
with open('sportsantecvl.json', 'r', encoding='utf8') as file:
    data = json.load(file)

# Création de la table activité si elle n'existe pas déjà
c.execute('''CREATE TABLE IF NOT EXISTS activite
             (label VARCHAR, description VARCHAR, url VARCHAR, lat REAL, lng REAL, address VARCHAR)''')

# Ajout des activités dans la base
for entry in data:
    label = entry['Name']
    description = entry['Name']
    url = entry['url']
    lat = entry['lat']
    lng = entry['lng']
    address = entry['address']
    
    c.execute(
        "INSERT INTO activite (label, description, url, lat, lng, address) VALUES (?, ?, ?, ?, ?, ?)", 
        (label, description, url, lat, lng, address)
    )

    entry['id'] = c.lastrowid

# Détermination des pathologies
unique_pathologies = set()
for entry in data:
    pathologies = entry['Pathologies / Prévention']
    pathologies = pathologies.replace('\r', '')
    pathologies = pathologies.replace('Sport BIEN-ETRE', '')
    pathologies = pathologies.split('\n')
    pathologies = [val for val in pathologies if val != '']

    for pathologie in pathologies:
        unique_pathologies.add(pathologie)
    
    entry['Pathologies / Prévention'] = pathologies

# Création de la table pathologie si elle n'existe pas déjà
c.execute('''CREATE TABLE IF NOT EXISTS pathologie
             (id INTEGER PRIMARY KEY AUTOINCREMENT, nom VARCHAR)''')

# Convertion du set des pathologies en liste.
unique_pathologies = list(unique_pathologies)

# Insertion des pathologies dans la table
for pathologie in unique_pathologies:
    c.execute("INSERT INTO pathologie (nom) VALUES (?)", (pathologie,))

# Création de la table de relation activité_pathologie si elle n'existe pas déjà
c.execute('''CREATE TABLE IF NOT EXISTS activite_pathologie
             (id INTEGER PRIMARY KEY AUTOINCREMENT, id_activite INTEGER, id_pathologie INTEGER,
             FOREIGN KEY(id_activite) REFERENCES activite(id),
             FOREIGN KEY(id_pathologie) REFERENCES pathologie(id))''')

# Récupération des pathologies pour chaque activités et insertion
for entry in data:
    id_activite = entry['id']
    for pathologie in entry['Pathologies / Prévention']:
        id_pathologie = unique_pathologies.index(pathologie) + 1

        c.execute("INSERT INTO activite_pathologie (id_activite, id_pathologie) VALUES (?, ?)", (id_activite, id_pathologie))

# Récupération des diciplines
unique_dicipline = set()
for entry in data:
    diciplines = entry['Discipline'].replace('\n', '').split('\r')
    
    for dicipline in diciplines:
        unique_dicipline.add(dicipline)

    entry['Discipline'] = diciplines


# Création de la table discipline si elle n'existe pas déjà
c.execute('''CREATE TABLE IF NOT EXISTS discipline
             (id INTEGER PRIMARY KEY AUTOINCREMENT, nom VARCHAR)''')

# Convertion du set des diciplines en liste.
unique_dicipline = list(unique_dicipline)

# Insertion des disciplines dans la table
for discipline in unique_dicipline:
    c.execute("INSERT INTO discipline (nom) VALUES (?)", (discipline,))

# Création de la table de relation activite_discipline si elle n'existe pas déjà
c.execute('''CREATE TABLE IF NOT EXISTS activite_discipline
             (id INTEGER PRIMARY KEY AUTOINCREMENT, id_activite INTEGER, id_discipline INTEGER,
             FOREIGN KEY(id_activite) REFERENCES activite(id),
             FOREIGN KEY(id_discipline) REFERENCES discipline(id))''')

# Récupération des disciplines pour chaque activité et insertion
for entry in data:
    id_activite = entry['id']
    for dicipline in diciplines:
        id_discipline = unique_dicipline.index(dicipline)

        # Insertion de la relation entre l'activité et la discipline dans la table de relation
        c.execute("INSERT INTO activite_discipline (id_activite, id_discipline) VALUES (?, ?)", (id_activite, id_discipline))

# Dump de la base de donnée.
with open(database_dump, 'w', encoding='utf-8') as f:
        for line in conn.iterdump():
            f.write('%s\n' % line)
 
conn.commit()
conn.close()
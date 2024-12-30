## **StopAndGo**

### **Description**
La **StopAndGop** est une application mobile Android conçue pour simuler un feu de circulation en temps réel. Elle offre une interface simple et intuitive avec des fonctionnalités permettant de visualiser et de contrôler les durées des différents feux (rouge, jaune, vert). L'application utilise un cycle de feux de circulation automatique, avec la possibilité pour l'utilisateur de personnaliser les durées de chaque lumière. De plus, elle offre une fonctionnalité spéciale qui fait clignoter le feu jaune entre 0h et 6h pour simuler une situation de prévention.

### **Fonctionnalités principales** :
- **Simulation du cycle des feux de circulation** : Feu rouge, jaune et vert s'activent automatiquement dans un cycle infini.
- **Clignotement du feu jaune** : Entre 0h et 6h, le feu jaune clignote à des intervalles réguliers pour indiquer une situation de prévention.
- **Personnalisation des durées des feux** : Les utilisateurs peuvent ajuster les durées du feu rouge, jaune et vert via un menu de paramètres.
- **Sauvegarde des paramètres** : Les durées des feux sont sauvegardées dans les préférences partagées afin de conserver les choix de l'utilisateur entre les sessions.
- **Interface utilisateur fluide et responsive** : L'application offre une expérience agréable avec des animations et une interface soignée pour chaque état du feu de circulation.

### **Technologies utilisées** :
- **Android SDK** : Application développée pour Android avec Kotlin.
- **SharedPreferences** : Pour la gestion des préférences utilisateur et la sauvegarde des durées personnalisées.
- **Handler** : Utilisé pour gérer les délais et la répétition des cycles de feux.
- **AlertDialog** : Pour afficher un dialogue de personnalisation des durées des feux.
  
### **Design** :
![image](https://github.com/user-attachments/assets/14f1d4a1-5297-4496-960d-e7f799631010)


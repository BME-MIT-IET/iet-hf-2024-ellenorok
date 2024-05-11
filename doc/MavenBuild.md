# Ellenőrzési technikák dokumentáció
## Maven build és GitHub Actions

- Az eredeti projekt nem használt Maven buildet, sem packageket, így át kellett migrálni egy Mavennek megfelelő projekt formátumba, valamint létre kellett hozni hozzá a megfelelő pom.xml file-t.
- A Maven projekté formázás nehézségét a teljes projekt strutúra váltás okozta, valamint az, hogy egy már létező filerendszer köré kellett a Maven-t felhúzni.
- A projekt struktúrát az iet.hf.ellenorok packagebe tettem, valamint az összes java file-t áthelyeztem az ennek megfelelő packgebe.
- A Maven build GitHub Actionsből való működéséhez elkészítettem a megfelelő YAML file-t.
- A Maven build működésének ellenőrzésére teszteket készítettem, amik a projekt elemeit használják. Ezeket helyes és hibás értékkel is fel pusholtam. A helyes eredményre a pipeline lefutott, míg a hamisra error-t dobott. Ennek köszönhetően megbizonyosodtam arról, hogy a build az Actionsben is működik.
- Tanulságként levonható, hogy sokkal egyszerűbb egy projektet egyből Maven keretrendszerrel készíteni, mint utólag migrálni abba.

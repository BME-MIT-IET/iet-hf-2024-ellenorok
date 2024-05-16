# Sivatagi vízhálózat

A program az alábbi szabályokat követő játék megvalósítását végzi. Ehhez használ egy
főmenüt, amit a felhasználó megnyitáskor lát és később elérhet a játék közben is. Innen
indíthatja el a játékot. A játék grafikus felületen történik. Bővebb információk a szabályokról, működésről, a program belső működéséről a ```sivatag_docs\documentation.pdf``` fájlban találhatók.

A drukmákori sivatagon át bonyolult csőrendszer szállítja a vizet a hegyi forrásokból a
sivatagon túl elterülő városok ciszternáiba. A csőrendszer egyszerű, elágazás nélküli
csövekből és a csövekhez csatlakozó aktív elemekből (forrás, ciszterna, napelemmel működő
vízátemelő pumpa stb.) áll. Egy pumpa több (de a pumpára jellemző véges számú) csövet is
összeköthet, és minden pumpán külön-külön állítható, hogy éppen melyik belekötött csőből
melyik másik csőbe pumpáljon, azonban egyszerre csak egy bemenete és egy kimenete lehet.
A többi rákötött cső eközben el van zárva. A pumpák véletlen időközönként el tudnak
romlani, ilyenkor megszűnik az adott pumpánál a vízáramlás. A pumpák mindegyike
rendelkezik egy víztartállyal, amit a víz átemelése közben használ átmeneti tárolóként. A
pumpa csak akkor tud vizet pumpálni egy csőbe, ha a cső szabad kapacitása ezt lehetővé teszi.
A csőhálózat bővíthető, változtatható. A csövek kellően rugalmasak ahhoz, hogy az egyik
végüket lecsatlakoztatva egy másik aktív elemhez elvihetők és ott felcsatlakoztathatók
legyenek. A ciszternáknál 3 körönként készülnek az új csövek, amelyek egyik vége a
ciszternához kapcsolódik, a másik azonban szabad. A szabad végű csövekből a csőbe
betáplált víz a homokba folyik.

![image](https://github.com/BME-MIT-IET/iet-hf-2024-ellenorok/assets/127612755/65bf7b0b-90bf-483a-b775-ff1eb6e8b12c)

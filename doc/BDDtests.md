# Ellenőrzési technikák dokumentáció
## BDD tesztek

- Négy .feature fájlt készítettem, a hozzájuk tartozó StepDefinition osztályokkal. Mindegyik fájl valamilyen játékosi cselekvést tesztel, illetve azt, hogy ez milyen hatással van a vízhálózatra és a játék állására.

1. A pipe_state.feature során egy játékos ragadóssá, majd csúszóssá próbál tenni egy csövet. Az ezután a csőre lépő játékos az első esetben nem fog tudni elmozdulni, míg a második esetben nem marad rajta a csövön.
2. A player_gets_point.feature során egy szerelő megjavít egy csövet, egy szabotőr pedig kilyukaszt egyet, hogy pontot szerezzenek a csapatuknak.
3. A put_down_new_pump.feature során egy szerelő megpróbál letenni egy új pumpát egy csövön. Ekkor a cső lecserélődik két másik csőre és egy pumpára.
4. A water_flows.feature során a játékos átállítja a pumpa kimenetét. A következő lépésre a korábbi kimeneti csőbe nem mehet víz, csak az új kimenetbe.

- A StepDefinition osztályok megírásához felhasználtam a code snippet-eket, amiket a fordító javasol a .feature fájlok megírása után.
- A tesztek sikeresen lefutottak, tehát a játék az elvárásoknak megfelelően működik a kérdéses szenáriókban.
- Mivel minden kör után egy random érték dönti el, hogy elromlik egy pumpa, ezért a 4. tesztbe raktam egy ellenőrzést, hogy a pumpa elromlott-e. Ha ekkor a pumpa nem kerülne megjavításra, akkor a víz nem áramlana az új csőbe és nem lehetne sikeres a teszt.
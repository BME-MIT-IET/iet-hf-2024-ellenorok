# Manuális tesztek dokumentáció

- Ennél a tesztelésnél a fehasználó/termék fókuszban kelett manuális teszteket, teszteseteket készíteni, amelyek a játék számos funkcióját ellenőrzik.
- Ezekre azért volt szükség, hogy biztosíthassuk, hogy a program és a játék különböző elemei helyesen, a specifikációnak és a dokumentációnak megfelelően működnek.
Ki tudjuk küszöbölni az esetleges hibákat, melyek a program nem kívánt viselkedéséhez vezethetnek.


## Tesztelés

Három fő részre tudjuk felosztani ezeknek a teszteknek az elkészítését: Tervezés, végrehajtás és doumentálás

- A tesztek tervezésekor jelentős szempont olyan eseteket ellenőrizni, melyekkel a felhasználók gyakran találkozni fognak majd a program használata közben.
- Viszont érdemes olyan eseteket is tesztelni, amelyek csak a "kíváncsi" felhasználóknak jut az eszükbe, hogy meggyőződjünk arról, hogy az ilyen jellegű esetek sem vezetnek hibákhoz a program futása során.
- A tesztek között nem mindegyik hajtódott végre sikeresen. Volt ahol más lett az ellenőrzés eredménye, mint a várt. Éppen ezért volt szükség a tesztelésre.
    - Ezek az esetek rögzítésre kerültek, majd a többi teszt végrehajtása után, ki lettek javítva a programban a hibát okozó részek, majd újra végre lettek hajtva ezek a tesztek. Ezúttal sikeresen.
    - Abban az esetben, ha ezeka tesztek ekkor sem lettek volna sikeresek, újból a kódhoz kellett volna nyúlni, majd ismét tesztelni
- A tesztekről, valamint sikerességükről dokumentáció is készült, mely részletesen leírja ezeknek a folyamatát is. (manual_tests.xlsx)


## Konkrét tesztesetek

- program elindításának ellenőrzése
- játék elindításának ellenőrzése
- kilépés tesztelése a játékból (quit)
- új játék indításásank ellenőrzése, miután egy korábbi játék véget ért
- játékos léptetése bármilyen üres, szomszédos mezőre
- játékos léptetése bármilyen üres, nem szomszédos mezőre
- játékos léptetése szomszédos mezőre (node-ra), amin állnak
- játékos léptetése szomszédos csőre, amin állnak
- cső csúszóssá tételének ellenőrzése
- csúszós csőre lépésnek tesztelése
- cső ragadóssá tételének ellenőrzése
- ragadós csőről való elépés tesztelése
- cső kilyukasztásának ellenőrzése
- cső megjavításának ellenőrzése
- pumpa megjavításának tesztelése
- pumpálási irány átálításának ellenőrzése
- cső áthelyezésének ellenőrzése
- pumpa felvételének tesztelése
- pumpa lehelyezése
- kör befejezése
- játék befejezése
- szerelők nyernek
- szabotőrök nyernek
- döntetlen lesz a játék



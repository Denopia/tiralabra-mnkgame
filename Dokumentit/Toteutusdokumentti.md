# Toteutusdokumentti

### Yleisrakenne
Ohjelma koostuu itse peliluokasta, pelaajaluokasta ja
tekoälyluokasta. Peliolio pitää muistissa peliin liittyvät
tiedot ja pelaajaoliot muistavat pelaajiin liittyvät tiedot.
Peliin kuuluu siis kaksi pelaajaoliota. Lisäksi siihen kuuluu
yksi yleinen tekoälyolio, joka päättää tekoälypelaajien siirrot.
Tekoäly voi tehdä päätökset molempien pelaajien puolesta.
Peliin kuuluu myös piirtäjäluokka, joka tulostaa viestejä ja 
peliruudukon. Lopuksi on vielä apuluokka, jonka tehtävänä on
käsitellä kaksiulotteisia taulukkoja, eli tässä tapauksesa 
peliruudukkoa. 

### Saavutetut aika- ja tilavaativuudet
Peli ottaa käyttäjätlä syötteenä peliruudukon leveyden ja korkeuden
(EI VIELÄ TÄLLÄ HETKELLÄ). Eli jos r on korkeus ja c on leveys niin
tilaa kuluu r&ast;c. Koska tutkimme pelipuuta syvyyssuunnassa , jokaisen 
edeltävän solmun tilanne muistetaan, eli syvyyden ollessa d, tilaa
tarvitaan r&ast;c&ast;d. Tällä hetkellä tekoäly tutkii peliä 4 siirtoa eteenpäin.
Jos syvyyttä tästä kasvatetaan, päätöksenteko kestää liian kauan isommilla
peliruudukoilla. Tallennettavaa tila on siis suoraan verrannollinen 
käyttäjän haluaman peliruudukon kokoon. 

Koska syvyys on nyt 4 ja tutkimme kaikki mahdolliset siirrot aikavaativuudeksi
saadaan (r&ast;c)^4. Tätä kuitenkin nopeuttaa alpha-beta-karsinta, ja muu
optimointi (ei tutkita solmuja joissa siirto tehtäisiin kauas muista tehdyistä
siirroista).

### Suorituskyky
Tekoälyn voi laittaa pelaamaan itseään vastaan. Alla taulukoita pelien keskimääräisiä
kestoja erikokoisilla ruudukoilla.

| Ruudukon koko | Voittosuoran pituus | Aika (ms)  | Voittanut pelaaja |
|:-------------:|:-------------------:|:----------:|:-----------------:|
| 3x3           | 3                   | 60         | draw              |
| 4x4           | 4                   | 180        | draw              |
| 5x5           | 4                   | 665        | draw              |
| 5x6           | 4                   | 618        | 1                 |
| 5x5           | 5                   | 442        | draw              |
| 5x6           | 5                   | 750        | draw              |
| 6x6           | 5                   | 3227       | draw              |
| 7x7           | 5                   | 10353      | draw              |
| 8x8           | 5                   | 29423      | draw              |
| 9x9           | 5                   | 61365      | draw              |
| 10x10         | 5                   | 101093     | 1                 |

Varmaan mielenkiintoisempaa on testata kuinka kauan keskimäärin yhden siirron
päättäminen kestää. Tutkitaan tätä myöhemmin.

###Parannusehdotukset ja puutteet


jatketaan myöhemmin
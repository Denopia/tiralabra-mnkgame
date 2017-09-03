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

Ongelma on siis toteuttaa tekoäly, joka yrittää voittaa ristinollapelin.
Tekoäly käy läpi pelipuuta tietylle syvyydelle ja pisteyttää kaikki solmut
joihin päästään. Minmax-algoritmin avulla pisteytetään näiden solmujen
vanhemmat ja jatketaan vanhempien pisteytystä kunnes kaikkia mahdollisia
siirtoja vastaavat pelitilanteet ovat pisteytetty. Näistä valitaan siirto,
jolla on korkein pistemäärä. Jos usealla siirrolla on sama pistemäärä, valitaan
näistä siirto sattumanvaraisesti. Käytössä on javan Random-luokka, koska omaa
versioita tästä ei ollut aikaa toteuttaa. Sattumanvaraisuus on käytössä vain 
siksi, etteivät tekoäly vs tekoäly -pelit olisi aina identtisiä. Toistamalla
tällaisia pelejä huomattiin, että suurin osa peleistä esimerkiksi 10x10 kokoisilla 
ruudukoilla päättyi tasapeliin, mutta joskus pelaaja 1 pystyi voittamaan. Siispä
pelipuussa tietyllä syvyydellä olevat samanarvoiset pelitilanteet eivät oikeasti
olekaan samanarvoisia vaikka heuristiikan mukaan näin olisi.

Heuristiikka toimii siten, että ensin peliruudukko käydään läpi voittosuoran
kokoisella ikkunalla. Jos voittosuora on esimerkiksi 5 mittainen, ikkunan koko on
1x5. Ikkunaa käytetään jokaisessa mahdollisessa ruudukon paikassa. Jos ikkunan 
sisällä on vain toisen pelaajan merkkejä, voi ikkunan sisällä tulevaisuudessa olla
tämän pelaajan voittava suora. Näiden potentiaalisten voittosuorien määrät tallennetaan
taulukkoon ikkunan sisällä olevien merkien määrän mukaan. Jos ikkunan sisällä on 4 merkkiä
on se parempi tilanne kuin sellainen, jossa ikkunan sisällä olisi vain 2 merkkiä.

Kun pelitilanteen potentiaaliset voittopaiakt on käyty läpi, pistetetään tilanne niitä 
käyttäen. Mitä enemmän ja mitä parempia paikkoja pelaajalla on, sen parempi pisteytys.
Vastustajan paikat tietenkin heikentävät pelitilanteen arvoa. Tällainen heuristiikka
osoittautui kuitenkin tietynlaisissa tilanteissa huonoksi. Isompia suoria arvostetaan
enemmän kuin pieniä, mutta pienten suorien yhdistelmät voivat joissain tapauksissa olla
parempia kuin jotkin suurten suorien yhdistelmät, jotka kuitenkin pisteytetään paremmin.
Pisteytys vaatisi siis parannusta, jotain erikoistapausten tarkastusta tai kokonaan
erilaista pisteytystapaa.

Pelipuunläpikäynti suurilla ruudukoilla vie paljon aikaa. Jo 10x10 ruudukolla on 100
mahdollista siirtoa, ja sen jälkeen 99 ja niin edelleen. Solmujen tutkimisia tulee
siis tässä tapauksessa 100^n, jossa n on syvyys, johon asti puuta tutkitaan. Pelitilanteiden
tutkimisessa kuluu kuitenkin sen verran aikaa ettei ole mitään järkeä tutkia koko puuta.
Nopeuttamiseksi tehtiin päätös, että tekoäly voi tehdä siirron vain ruutuun, jonka ympärillä
on vähintään yksi pelimerkki. Joskus on tietenkin paras siirto laittaa merkki paikkaan, jonka
välittömässä läheisyydessä on vain tyhjiä ruutuja. Näin kuitenkin saadaan vähennettyä tutkittavien 
siirtojen määrää pelin alku- ja keskivaiheessa niin paljon, että ajattelin ettei silloin tällöin
parhaan mahdollisen siirron tekemisen tekemättä jättäminen haittaa liikaa. 

Siirtojen karsimisesta huolimatta, pelipuuta ei päästä tutkimaan kovin pitkälle. Kokeilemalla 
huomattiin, että 4 tai 5 on vielä siedettävä syvyys pelaajan odottelun kannalta (suuremmilla ruudukoilla
esim. 10x10). Solmujen tutkimiseen kuluvaa aikaa voisi varmasti nopeuttaa jollain tavalla, joten voi olla
mahdollista, että pelipuuta päästäisiin tutkimaan vielä syvemmälle. Syvyys on normaalisti 4, mutta jos
mahdollisten siirtojen määrä on tarpeeksi pieni, syvyyttä kasvatetaan pelin aikana.

### Saavutetut aika- ja tilavaativuudet

Pelaaja valitsee pelin alussa ruudukon koon valmiista vaihtoehdoista. Tarkoituksena oli,
että pelaaja voisi myös itse päättä ruudukon leveyden ja korkeuden ja voittosuoran
pituuden. Tämä kuitenkin jäi toteuttamatta.

Olkoon nyt r ruudukon korkeus ja c ruudukon leveys. Tilaa pelitilanteen tallennukseen 
kuluu r&ast;c. Pelitilanne tallennetaan tavalliseen kaksiulotteiseen taulukkoon.
Koska tutkimme pelipuuta syvyyssuunnassa, jokaisen edeltävän solmun 
tilanne joudutaan muistamaan, eli syvyyden ollessa d, tilaa tarvitaan r&ast;c&ast;d. 
Koska syvyys pelin aikana on suurimman osan ajasta 4 tai 5, tilaa kuluu korkeintaan
5&ast;r&ast;c. Syvyyden pysyessä käytännössä vakiona tilavaativuus on suoraan verrannollinen
pelaajan valitseman ruudukon kokoon.

Taso, jolle pelissä päästään sisältää siis m^d solmua, missä m on mahdolliset siirrot ja
d syvyys. Näille solmuille tehdään pisteytystarkastukset, jotka vievät jonkin ajan t1. Lisäksi
jokaiselle puun solmulle syvyyteen d asti tehdään peliruudukon kopioiminen ja muita operaatiota,
jotka vievät ajan t2. Näitä solmuja on m+m^2+...+m^d. Aikaa kuluu siis (t1+t2)&ast;m^d. Lisäksi
tapahtuu muita operaatiotia joiden aikavaativuus on joko vakio tai linearisesti riippuva ruudukon
koosta.

Empiirisen testauksen perusteella laskin keskiarvoja ajalle, jonka tekoäly käyttää siirron
päättämiseen, eli ajan jonka pelaaja joutuu odottamaan. Ajat ovat pienempiä kun mahdollisia
siirtoja on vähemmän ja suurempia kun mahdollisia siirtoja on paljon, mutta alla on keskiarvoja
eri kokoisilla ruudukoilla.

| Ruudukon koko | Voittosuoran pituus | Aika (ms)  |
|:-------------:|:-------------------:|:----------:|
| 3x3           | 3                   | 5          | 
| 4x4           | 4                   | 17         | 
| 5x5           | 5                   | 57         |
| 6x6           | 5                   | 306        | 
| 7x7           | 5                   | 1039       | 
| 8x8           | 5                   | 883        | 
| 10x10         | 5                   | 1259       |

Huomataan, että esimerkiksi ruutujen kaksinkertaistuessa (7x7 -> 10x10) vuoroon kuluva
aika ei kasva kuten teoreettisesti arvelin. Tämä johtuu siitä, että tekoäly voi tehdä
siirtoja vain ruutuihin joiden läheisyydessä ei ole pelkkiä tyhjiä ruutuja. Kokeilemalla
esimerkiksi 10x10 kokoisella ruudukolla, mahdollisia siirtoja oli pelin keski- ja loppuvaiheilla
noin 20-50 kappaletta. Tämä siirtojen karsiminen siis nopeuttaa algoritmin toimintaa huomattavasti.


### Parannusehdotukset ja puutteet

Aikaisemmin onkin jo tullut esille asioita, joissa olisi parannettavaa. Toiminnallisuutta olisi
ollut mukava saada laajennettua siten, että käyttäjä saa valita haluamansa kokoisen ruudukon.
Tekstipohjainen käyttöliittymä tällaisessa pelissä ei ole paras mahdollinen, joten olisi myös
hyvä saada tehtyä jonkinlainen graafinen käyttöliittymä jotta peliä voi pelata vain hiiren avulla.
Pelissä ei myöskään ole mahdollista valita millä merkillä pelaaja pelaa, tai pein loputtua aloittaa
uutta peliä ilman että käynnistää ohjelman uudelleen. Pelissä ei myöskään oli komentoa pelin lopettamiseen
kesken kaiken. Nämä puutteet liittyvät kylläkin vain ohjelman käytön miellyttävyyteen.

Itse tekoälyn toimintaan liittyviä puutteisiin kuuluu heuristiikan huono toimiminen tietyissä tilanteissa.
Joskus voi huomata kun tekoäly tekee siirron, joka ei näytä parhaalta mahdolliselta. Lisäksi tekoäly ei
välttämättä aina ole innokas lopettamaan peliä. Sillä voi siis olla useakin siirto jolla voi voittaa 
pelin, mutta se silti valitsee jonkin muun siirron. Tämä johtuu kai siitä, että vastustaja ei voi estää
tekoälyn voittoa millään siirrolla joten sillä ei ole kiire voittaa peliä heti. Näin ainakin tapahtui 
joissain vaiheesa, en ole aivan varma tapahtuuko näin viimeisimmässä versiossa. Usein siirrot näyttävät
melko järkeviltä, mutta joskus esiintyy siirtoja, jotka eivät näytä kovin hyviltä.

Pelipuuta ei päästä myöskään tutkimaan syvälle. Olisi ollut hyvä nopeuttaa solmuille tehtäviä operaatioita,
jotta syvyyttä olisi saatu vähän lisää.

Tekoälytestien tekeminen ei onnistunut kovin helposti sattumanvaraisuuden takia, mistä johtuen sen
käyttäminen siiron valinnassa samanarvoisten siirtojen joukosta hieman arveluttaa.

Tulostamiseen käytettävä Drawer-luokka jäi vähälle huomiolle, enkä ollut varma mitä kaikkea tulostelua
sen kautta haluan tehdä. Siellä on siis muutama hassu pelkkää tavallista tulostusta tekevä metodi.

Tämän enempää puutteita ei tällä hetkellä tule mieleen.

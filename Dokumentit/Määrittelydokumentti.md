# Määrittelydokumentti

### Ratkaistava ongelma
Työn aiheena on toteuttaa tekoäly laajennettuun risti-nolla-peliin.
Tarkoituksena on aluksi keskittyä 15x15 ruudun kokoiseen peliin,
jossa yritetään saada aikaan viiden suora. Kun tekoäly pystyy
pelaamaan peliä näillä arvoilla, voidaan yrittää saada se myös
toimimaan vaihtelevan kokoisilla ruudukoilla ja voittoon johtavan
suoran pituuksilla.

### Algoritmit ja tietorakenteet
Työssä ratkaistava ongelma on siis saada tekoäly tekemään jokaisessa
pelitilanteessa parhaat mahdolliset siirrot. Tähän voidaan käyttää
pelipuuta. Mahdolisten siirtojen määrä on kuitenkin tässä pelissä
suuri, joten koko pelipuuta ei ole järkevää käydä läpi. Joudutaan
siis lopettamaan pelipuun tutkiminen tietyllä syvyydellä ja arvioida
pelitilanteen hyvyyden heuristisesti. Heuristiikkaa varten tarvitaan
algoritmi, joka osaa laskea pelitilanteelle pistearvion sen 
suosiollisuudelle.

Pelitilanne joudutaan pitämään muistissa. Tähän periaatteessa
riittäisi vain taulukko ruuduista, mistä voi tarkastaa onko
ruutu tyhjä vai onko siinä x tai o. Siirron valintaan käytettävää
algoritmia saattaa kuitenkin nopeuttaa, jos pidetään muistissa
jo valmiiksi kaikki kahden, kolmen ja neljän suorat. Tämän voi
toteuttaa varmaankin hashmapilla, listoilla tai omilla ruutu-olioilla.
En osaa vielä sanoa mikä olisi paras tapa.

### Syötteet ja ohjelman käyttö
Toteutan pelin aluksi tekstipohjaisena, eli pelitilanne piirretään
kirjoitusmerkeillä. Peli siis kysyy pelaajalta siirron, joka annetaan
muodossa "a1", "d5" jne. Jokaisella ruudulla on siis uniikki rivi-sarake
yhdistelmä ja pelaaja kertoo mihin ruutuu haluaa vuorollaan merkkinsä
sijoittaa. Tekoäly tekee tämän jälkeen oman siirtonsa, minkä jälkeen tulee
taas pelaajan vuoro.

Alussa pelaajalta voidaan kysyä kuinka ison peliruudukon hän haluaa ja
haluaako hän aloittaa vai saako tekoäly tehdä ensimmäisen siirron.
Lisäksi pitää olla mahdollisuus antaa tekoälyn pelata itseään vastaan.

### Aika- ja tilavaativuus
Näitä on ehkä hieman hankala arvioida tässä vaiheessa. Tilavaativuus olisi
yksinkertaisimmillaan O(n), jos pelaaja saa päättää ruudukon koon ja 
muistissa pidetään vain mikä merkki jokaisessa ruudussa on. Tilaa tietysti 
kuluu enenmmän, jos halutaan muistaa kaikki viittä lyhyemmät suorat. 

Aikavaativuuden O-analyysistä en osaa sanoa vielä mitään. Tavoitteena
on kuitenkin, että tekoäly tekee siirtonsa tarpeeksi nopeasti, että 
peliä olisi miellyttävä pelata.

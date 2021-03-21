# Advent-of-code-2015-day-13
**Uppgiften kan hittas här: https://adventofcode.com/2015/day/13**

#### Tankesätt
Jag söker den största totala summan av glädje. Svaret kan fås genom att testa alla möjliga placeringar runt bordet.  Först tänkte jag att det fanns 8! = 40320 sätt att sitta på. Men insåg att detta inte stämmer eftersom bordet är runt.

Tex. om George är på plats 1 eller 7 sitter han fortfarande bredvid Alice: <br/>
Alice George *plats plats plats plats plats plats*  är samma som lycka som Alice *plats plats plats plats plats plats* George

Vidare så är detta samma placering runt det runda bordet: <br/>
A B C D E F G H  samma resultat A H G F E D C B

Jag började klura runt kombinatorik och olika möjliga placeringar kring mindre bord: <br/>
A B C  = A C B <br/>
A B C D = A bredvid BC eller BD eller CD 	<br/>
A B C D E = A bredvid BC BD BE CD CE DE x2! (De övriga 2 namnen kan placeras fritt = 2!) <br/>

Jag kom fram till att de möjliga placeringarna med 8 personer borde vara: <br/>
A B C D E F G H  = A bredvid BC BD BE BF BG BH <br/>
				A bredvid CD CE CF CG CH <br/>
				A bredvid DE DF DG DH <br/>
				A bredvid EF FG FH <br/>
				A bredvid FG FH <br/>
				A bredvid GH <br/>
				5! <br/>
21*5! = 2520st <br/>

2520st är ett mycket mindre tal än 40320, dvs värt att inte bara göra 8! <br/>
Nu kände jag att det var dags att börja koda. 

#### Plan
Söka upp kod för att generera alla möjliga permutationer av 5! (Kändes som det var OK i ditt mail) <br/>
Använda en hasmap till att spara värderna av glädje som fås genom att sitta bredvid olika personer. (Jag valde en hasmap för det var bland det första jag tänkte på. Andra funderingar var att skapa något slags klass för varje person som höll värderna av glädje) 

- Börjar implementera och tänka på att göra lösningen skalbar genom att: <br/>
- Kolla om jag kan hitta genererar n! Permutationer (Om möjligt) <br/>
- Skriva generell kod som leder fram till talet som multipliceras med n!  <br/>




# Sheriff-of-Nottingham

*Prezentare:
	-Pentru Implementare am creat o interfata PlayerTemplate care va 
contine niste constante utile pentru clasa abstracta Player, care o va
implementa. In Player vom creea metode de umplere a mainii, de identificare
a cartilor de acelasi tip, de identificare a cartii cu profit maxim, de
printare a jucatorului si inca niste metode ajutatoare.
    -Pe langa acestea in Player se mai afla 3 metode principale, basicSack,
wizardSack, si basicInspection. Pe baza acestora vom implementa cele 2 metode
abstracte de creeare a sacului si de inspectare, specifice fiecarei strategii
reprezentate in cate o clasa ce o extinde pe Player. 
    -Urmand indicatiile din cerinta, am implementat fiecare clasa conform 
strategiei. La implementare a contribuit semnificativ clasa Card, reprezentand
o implementare a fiecarui bun, particularizarea fiind realizata in cadrul
constructorului.
    -In clasa Main am implementat desfasurarea jocului conform cerintei. 
Pentru adaugarea bonusurilor si calcularea scorului, m-am folosit de o clasa 
BonusSetter care in constructorul ei atribuie bonusurile unui vector de 
jucatori.
    -Pentru rezolvarea bonusului m-am folosit de strategia Royal. Metoda de
umplere a sacului este implementata pe baza unor metode ajutatoare din clasa
Player.

*Descrierea jocului:
    -Lista de stringuri primita ca imput, creem o lista de tip "Player",
fiecare element primind downcast la tipul de strategie data de fiecare string.
Lista de inturi este transformata asemanator intr-o lista de tip Card,
tipul cartii fiind stabilit in constructor in functie de id ul din lista
initiala.
    -Pentru pregatirea jocului, toti jucatorii isi incarca lista de carti
"hand" asociat mainii cu 6 carti apeland metoda "fillHand" pe fiecare jucator. 
De asemenea folosesc 3 contoare: 1)"round" setat cu 1 care creste la fiecare
runda si este folosit pt a opri bucla while cand numarul de jocuri este 
dublul numarului de jucatori. Asta inseamna ca fiecare a fost sheriff de 2 
ori; 2) "roundOfGreedy" care numara rundele in care un jucator de tip Greedy 
este comerciant, pentru a stii in ce runda triseaza; 3)"index" care 
monitorizeaza pozitia jucatorului care urmeaza sa fie sheriff in runda curenta
si se reseteaza cand a parcurs toata lista.
    -In bucla while se intampla practic desfasurarea jocului. In fiecare
runda, la inceput setam sherifful in functie de index, apoi fiecare jucator
care nu este sheriff isi incarca lista de tip Card-"sack" in functie de
strategia adoptata, folosind functia "fillSack", implementata diferit in
fiecare clasa. Toate implementarile, mai putin cea a wizardului au in comun
metoda "basicSack", implementata in Player si care umple lista "sack" conform
strategiei basic.
    -In continuare fiecare jucator este inspectat sau inspecteaza, in functie
de valoarea de adevar a campului sheriff. Taraba fiecarui jucator este o lista
de tip Card-"Table" unde ajung bunurile neconfiscate. Fiecare jucator are si
niste campuri "pocket" si "bribe" care ajuta la gestionarea banilor in functie
de cum a decurs inspectia. Pentru inspectie utilizam metoda "inspection",
implementata particular pentru fiecare jucator, toate avand la baza insa
metoda "basicInspection" implementata in Player.
    -La finalul rundei fiecare jucator isi completeaza cartile din mana pana
la 6 si isi goleste sacul prin metodele "fillHand" si "clearSack". Totodata
campul sheriff al sheriffului curent.
    -Dupa incheierea jocului, creez un obiect de tip BonusSetter, care are un 
singur camp, o lista de tip Player si al carui constructor primeste ca 
parametru tot o lista de tip Player. In cadrul constructorului, campul
obiectului devine chiar lista noastra de jucatori, cu diferenta ca fiecare
jucator are bonusurile adaugate si scorul calculat, totul in functie de ce
carti are fiecare pe taraba. Listei initiale de jucatori i se atribuie cea a 
obiectului nou creat.
    -Pentru afisare utilizam functia "print" a fiecarui jucator, ordinea fiind
stabilita in functie de scor, sau daca au acelasi scor in functie de aparitia
in lista de jucatori (este afisat primul cel care are indexul mai mic in 
lista).

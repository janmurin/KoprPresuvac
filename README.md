# KoprPresuvac
## 1. Projekt##

Vytvorte klient-server aplikáciu na kopírovanie súboru. Požadované vlastnosti:

- Sťahovanie súboru prebieha paralelne cez používateľom daný počet TCP soketov.
    
- Toto sťahovanie je prerušiteľné tak, že ho je možné prerušiť, vypnúť program, napr. sa presunúť na iné miesto na internete a pri opätovnom pokuse o skopírovanie toto kopírovanie pokračuje od momentu prerušenia opäť paralelne cez rovnaký počet TCP soketov.
    
- Grafické používateľské rozhranie obsahujúce aspoň 
	- progressbar znázorňujúci percento skopírovania celého súboru
    
	- tlačidlo na prerušenie kopírovania a následné vypnutie programu
        
	- tlačidlo na opätovné pokračovanie v kopírovaní, ak je (pri spustení) zistené, že kopírovanie bolo prerušené
        
	- tlačidlo na úplné zrušenie kopírovania bez možnosti pokračovania, ktoré nezanechá na disku žiadnu stopu 
    
- Môžete si vybrať, či bude prebiehať kopírovanie zo servra na klienta alebo naopak, nie je potrebné programovať oba smery
    
- Stačí uvažovať kopírovanie jedného súboru v rovnakom čase
    
- Nie je potrebné programovať prehľadávač disku "na druhej strane" na výber súboru
    
- Odporúčam nepoužívať vytváranie špeciálnych paketov s hlavičkami, či inak krájať dáta na chunky, ale posielať cez Socket.getOutputStream().write() po prípadných úvodných dohodách iba dáta. Uzatvorenie streamu sa dá odchytiť cez výnikmku IOException, keď sa zatvorí socket.
    
- Program má byť schopný skopírovať bez problémov na lokálnej sieti, alebo v rámci localhostu aj 1GB súbor pod 1 minútu 

##Riešenie##

1. spustiť **FileServer.java** a nastaviť cesty k súborom a cieľovym adresarom
2. spustiť **MainForm.java**, skontrolovať cesty
	
	- fileserver načíta celý súbor do ramky. kopirovanieManagerTask zisti veľkosť dát a názov súboru a rozdelí veľkosť súboru na rovnako veľké chunky, ktoré potom pridelí jednotlivým downloaderom. Pri prerušení sa ukladajú do súboru **clientData.txt** do každého riadku dáta o stave kopírovania vo formáte: `startOffset_zapisanychOffset_chunkSize` a do posledného riadku sa ukladá celkový čas využitý na kopírovanie súboru.  
	- každý downloader pošle na server číslo offsetu súboru a veľkosť chunku a bude prijímať dáta cez socket inputstream postupne ukladajúc ich do buffru a priebežne ukladajúc cez RandomAccessFile na disk
	- **BUFFER_SIZE** v `Shared.java` môže mať veľký vplyv na rýchlosť kopírovania, pretože zápis na disk je časovo veľmi náročná úloha a čím vyšší buffer, tým menej čakania na writeSemafore  
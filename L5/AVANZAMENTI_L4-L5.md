# 📚 Avanzamenti dalla Lezione 4 alla Lezione 5

## 🎯 Panoramica Generale

Nella **Lezione 5** siamo passati da un'applicazione con operazioni CRUD hardcoded (scritte direttamente nel codice) a un'**applicazione interattiva** con un menu completo e funzionalità avanzate di import/export.

---

## ✨ Novità Principali

### 1️⃣ **MainInterattivo.java** - Applicazione con Menu Interattivo

**Cosa osservare:**
- 📋 **Menu grafico con ASCII art** per un'interfaccia più professionale
- 🔄 **Loop principale** che continua fino a quando l'utente sceglie di uscire
- ⌨️ **Gestione dell'input utente** tramite `Scanner`
- 🛡️ **Validazione degli input** con gestione degli errori

**Struttura del menu:**
```
1. Inserisci nuova pizza
2. Visualizza tutte le pizze
3. Cerca pizza per ID
4. Modifica pizza
5. Elimina pizza
6. Importa pizze da file CSV (NUOVO!)
7. Esporta pizze in file CSV (NUOVO!)
0. Esci
```

**Punti chiave da notare:**
- Ogni operazione CRUD ora è in un **metodo separato** (inserisciPizza, visualizzaTutteLePizze, ecc.)
- **Metodi helper** per leggere input in modo sicuro: `leggiIntero()` e `leggiDouble()`
- Gestione degli errori di input (es. se inserisci una lettera invece di un numero)

---

### 2️⃣ **Nuovo Package: util/** - Funzionalità Avanzate

#### 📤 **CsvExporter.java** - Esportazione Dati
**Cosa fa:**
- Permette di **salvare le pizze del database in un file CSV**
- Utile per fare backup o analizzare i dati in Excel/fogli di calcolo

**Da osservare:**
- Attualmente è una classe vuota (sarà implementata durante la lezione)
- Impareremo a scrivere file CSV dal database

#### 📥 **CsvImporter.java** - Importazione Dati
**Cosa fa:**
- Permette di **caricare pizze da un file CSV nel database**
- Molto utile per inserire tanti dati rapidamente invece di digitarli uno per uno

**Caratteristiche tecniche:**
- Formato CSV atteso: `nome,ingredienti,prezzo`
- Gestisce i separatori (virgole)
- Ha due versioni del metodo (OVERLOADING): con e senza header

**Da osservare:**
- Costanti per separatori CSV
- Regex speciale per gestire virgole dentro campi tra virgolette
- Metodo `importPizzasFromCsv` con parametro opzionale `hasHeader`

---

### 3️⃣ **Main.java** - Versione Dimostrativa (invariata)

**Nota importante:**
Il file Main.java originale è rimasto **identico alla L4** e continua a mostrare esempi di tutte le operazioni CRUD in sequenza (utile per dimostrazioni e test).

---

## 🔍 Cosa Osservare Durante lo Studio

### Nel MainInterattivo.java:

1. **Pattern Switch-Case**
   - Come viene gestito il menu con il costrutto `switch`
   - Il caso `default` per input non validi

2. **Gestione Sicura degli Input**
   - I metodi `leggiIntero()` e `leggiDouble()` con try-catch
   - Come prevengono il crash dell'applicazione in caso di input errato

3. **Organizzazione del Codice**
   - Ogni funzionalità è in un metodo dedicato
   - Codice più leggibile e manutenibile
   - Commenti JavaDoc per documentare i metodi

4. **Ciclo di Vita dell'Applicazione**
   - Il loop `while(continua)` che mantiene il programma attivo
   - Come viene gestita l'uscita pulita con `case 0`
   - Chiusura dello scanner nel blocco `finally`

### Nelle Classi Util:

1. **CsvImporter.java**
   - **Overloading dei metodi**: due versioni dello stesso metodo con parametri diversi
   - **Costanti**: `CSV_SEPARATOR` e `CSV_SEPARATOR_REGEX` per configurazione centralizzata
   - **Regex avanzata**: come gestire campi CSV con virgole al loro interno

2. **Documentazione**
   - Commenti JavaDoc dettagliati
   - Esempi di formato CSV atteso
   - Spiegazione dei parametri

---

## 🎓 Concetti Nuovi Introdotti

| Concetto | Dove Trovarlo | Perché È Importante |
|----------|---------------|---------------------|
| **Interfaccia Utente Testuale** | MainInterattivo.java | Rende l'app utilizzabile da utenti finali |
| **Input Validation** | metodi leggiIntero/Double | Previene errori e crash |
| **Overloading dei Metodi** | CsvImporter.java | Flessibilità nell'uso dei metodi |
| **Costanti** | CsvImporter.java | Configurazione centralizzata |
| **Separazione delle Responsabilità** | package util | Codice più organizzato |

---

## 🚀 Progressione Didattica

**L4** ➜ Operazioni CRUD di base con codice hardcoded
- ✅ Creare, leggere, aggiornare, eliminare dati
- ✅ Migrations per gestire lo schema del database

**L5** ➜ Applicazione interattiva con funzionalità avanzate
- ✅ Menu interattivo
- ✅ Input dell'utente gestito in modo sicuro
- ✅ Import/Export CSV (base preparata)
- ✅ Codice più professionale e manutenibile

---

## 💡 Esercizi Suggeriti

1. **Esplora MainInterattivo.java**: Leggi ogni metodo e cerca di capire il flusso
2. **Testa la validazione**: Prova a inserire input errati e osserva come vengono gestiti
3. **Studia l'overloading**: Confronta i due metodi `importPizzasFromCsv`
4. **Migliora il menu**: Pensa a nuove funzionalità che potresti aggiungere

---

## 📝 Note Finali

Questa lezione introduce il passaggio da **codice dimostrativo** a **applicazione reale**. L'attenzione è sulla **user experience** e sulla **robustezza** del codice, elementi fondamentali per applicazioni professionali.

Buono studio! 🎉

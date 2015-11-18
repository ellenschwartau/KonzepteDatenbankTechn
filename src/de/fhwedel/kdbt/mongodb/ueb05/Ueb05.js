function printResult (result) {
    print(tojson(result));
}

function printQuery (query) {
    print(query);
    query.forEach(printResult);
}

function printCollection (collection) {
    printQuery(collection.find({}));
}

// Löschen der evtl alten Datenbank
db.dropDatabase()

// Anlegen der Daten
db.buch.insert({invNr: 1, autor: "Marc-Uwe Kling", titel: "Die Känguru-Chroniken: Ansichten eines vorlauten Beuteltiers", verlag: "Ullstein-Verlag"});
db.buch.insert({invNr: 2, autor: "Andreas Eschbach", titel: "Ausgebrannt", verlag: "Carlsen"});
db.buch.insert({invNr: 3, autor: "Horst Evers", titel: "Der König von Berlin", verlag: "Rowohlt-Verlag"});

db.leser.insert({lNr: 1, name: "Friedrich Funke", adresse: {straße: "Bahnhofstraße", hausNr: "17", plz: 23758, ort: "Oldenburg"}});
db.leser.insert({lNr: 2, name: "Malte Jörgens", adresse: {straße: "Esinger Weg", hausNr: "43", plz: 25436, ort: "Tornesch"}});
db.leser.insert({lNr: 3, name: "Heinz Müller", adresse: {straße: "Klopstockweg", hausNr: "17", plz: 38124, ort: "Braunschweig"}, entliehen: [1, 3]});
db.leser.insert({lNr: 4, name: "Ellen Schwartau", adresse: {straße: "Osdorfer Weg", hausNr: "21", plz: 22607, ort: "Hamburg"}});

// ENTLIEHEN als eigenes JSON-Datum darzustellen, aus dem auf die Objekt-IDs des
// Lesers und des Buches verwiesen wird. Wo wir dann das Attribut RÜCKGABEDATUM
// repräsentiert?
// db.entliehen.insert({refLNr: 2, refInvNr: 1, rückgabedatum: new Date(2015, 11, 18)});

// die Objekt-ID des Lesers in den Buch-Objekten abzulegen. Wohin dann mit
// RÜCKGABEDATUM?
// - Auch in das Buch-Objekt
// db.buch.insert({invNr: 1, autor: "Marc-Uwe Kling", titel: "Die Känguru-Chroniken: Ansichten eines vorlauten Beuteltiers", verlag: "Ullstein-Verlag", refLNr: 3, rückgabedatum: new Date(2015, 11, 18)});

// Vorteile: Konsistente Darstellung
// Nachteile: Schwierige Abfrage, welche Bücher ein Leser ausgeliehen hat

// Eine Kopie des LESER-Objekts im BUCH -Objekt zu halten
// + einfacher Zugriff
// - widersprüchliche Datensätze, da mehrfach vorhanden
// db.buch.insert({invNr: 1, autor: "Marc-Uwe Kling", titel: "Die Känguru-Chroniken: Ansichten eines vorlauten Beuteltiers", verlag: "Ullstein-Verlag", leser: {lNr: 3, name: "Heinz Müller", adresse: {straße: "Klopstockweg", hausNr: "17", plz: 38124, ort: "Braunschweig"}}, rückgabedatum: new Date(2015, 11, 18)});

// Eine Liste von Objekt-IDs der ausgeliehenen Bücher im Leser-Objekt zu speichern.
// - Konsistenz: Das gleiche Buch darf nicht mehrmals ausgeliehen werden
// - Schwierige Abfrage: Welcher Leser hat Buch X ausgeliehen?
// db.leser.insert({lNr: 3, name: "Heinz Müller", adresse: {straße: "Klopstockweg", hausNr: "17", plz: 38124, ort: "Braunschweig"}, entliehen: [1, 3]});

// Eine Liste von Kopien der ausgeliehenen Buch-Objekte im Leser-Objekt zu speichern.
// + Einfacher Zugriff
// - Das ist einfach richtig scheiße, da gleiche nachteile wie voriger punkt und problem der Konsistenz
// db.leser.insert({lNr: 3, name: "Heinz Müller", adresse: {straße: "Klopstockweg", hausNr: "17", plz: 38124, ort: "Braunschweig"}, entliehen: [{invNr: 1, autor: "Marc-Uwe Kling", titel: "Die Känguru-Chroniken: Ansichten eines vorlauten Beuteltiers", verlag: "Ullstein-Verlag"}]});


db.entliehen.insert({refLNr: 2, refInvNr: 1, rückgabedatum: new Date(2015, 11, 18)});
db.entliehen.insert({refLNr: 2, refInvNr: 2, rückgabedatum: new Date(2015, 11, 21)})

db.newsletter.insert({Nr: 1, thema: "Belletristik", erscheinungsweise: "Monat"});
db.newsletter.insert({Nr: 2, thema: "Reiseliteratur", erscheinungsweise: "Monat"});
db.newsletter.insert({Nr: 3, thema: "Neues aus der IT", erscheinungsweise: "Woche"});
db.newsletter.insert({Nr: 4, thema: "Studenten-News", erscheinungsweise: "Fünfmal am Tag"});

db.abo.insert({refLNr: 3, refNewsletters: [1, 2]});

// s. 1:n alles gleich, bis auf Fall: Newsletter hat mehrere Leser, dann entsprechend Liste von Referenzen bzw. Kopien

// Suchen
printQuery(db.buch.find({autor: "Marc-Uwe Kling"}));

print("Anzahl der eingetragenen Bücher: " + db.buch.count());

// Ermitteln Sie bitte alle Leser, die mehr als ein Buch ausgeliehen haben, absteigend sortiert nach Anzahl der entliehenen Bücher
printQuery(db.entliehen.aggregate([{$group: {_id:'$refLNr', anzahlEntliehen:{$sum:1}}},{$sort: {anzahlEntliehen: -1}}])); //TODO Leser anzeigen

// Lassen Sie Friedrich Funke das Känguru-Buch ausleihen und wieder zurückgeben.
printCollection(db.entliehen);
db.entliehen.insert({refLNr: 1, refInvNr: 1, rückgabedatum: new Date(2015, 11, 18)});
printCollection(db.entliehen);
db.entliehen.remove({refLNr: 1, refInvNr: 1});
printCollection(db.entliehen);

// Lassen Sie Heinz Müller das Känguru-Buch zurückgeben. Friedrich Funke soll es wieder ausleihen.
printCollection(db.leser);
db.leser.update({lNr: 3}, {$pull: {entliehen: 1}});
printCollection(db.leser);
db.leser.update({lNr: 1}, {$push: {entliehen: 1}});
printCollection(db.leser);


package de.fhwedel.kdbt.db4o;

import java.io.File;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

public class DBUtils {

    /**
     * Datenbank Container
     */
    protected static ObjectContainer db;

    /** Trennzeile für die Konsolenausgabe */
    protected static final String SEPERATOR = "-------------";

    public DBUtils() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Löscht die alte Datenbank und erstellt eine neue.
     * 
     * @param dbName
     */
    protected static void refreshDb(String dbName) {
        new File(dbName).delete();
        db = Db4oEmbedded.openFile(dbName);
    }

    protected static void createDB(String dbName) {
        db = Db4oEmbedded.openFile(dbName);
    }

    protected static void createDbCascadeOnUpdate(String dbName, Class... classes) {
        db.close();
        new File(dbName).delete();
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        for (Class clazz : classes) {
            config.common().objectClass(clazz).cascadeOnUpdate(true);
        }
        db = Db4oEmbedded.openFile(config, dbName);
    }

    protected static void createDbCascadeOnDelete(String dbName, Class... classes) {
        db.close();
        new File(dbName).delete();
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        for (Class clazz : classes) {
            config.common().objectClass(clazz).cascadeOnDelete(true);
        }
        db = Db4oEmbedded.openFile(config, dbName);
    }

    /**
     * Speichert eine Liste an Objekten in die Datenbank.
     * 
     * @param objects
     */
    protected static void create(List<Object> objects) {
        objects.forEach(db::store);
    }

    /**
     * Liest die gespeicherten Objekte aus der Datenbank.
     */
    protected static void readObjects(Class clazz, String msg) {
        System.out.println(msg);
        List<Object> pilots = db.queryByExample(clazz);
        pilots.forEach(System.out::println);
        System.out.println(SEPERATOR);
    }

}

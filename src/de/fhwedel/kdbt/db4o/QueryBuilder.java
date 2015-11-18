package de.fhwedel.kdbt.db4o;

import java.util.Arrays;
import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Constraint;
import com.db4o.query.Query;

public class QueryBuilder {

    /**
     * Hilfsklasse zur Definition einzelner Bedingungen.
     * 
     * @author Ellen
     *
     */
    public static class GivenConstraint {
        public GivenConstraint(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public final String name;
        public final Object value;
    }

    /**
     * Baut eine Query.
     * 
     * @param db DB-Obejkt
     * @param c Klasse der betreffenden Objekte
     * @param constraints optionale Bedingungen
     * @return DB-Query
     */
    public static Query createQuery(ObjectContainer db, Class c, GivenConstraint... constraints) {
        Query query = db.query();
        query.constrain(c);
        return addAndConstraints(query, Arrays.asList(constraints));
    }

    /**
     * Fügt einer Query beliebig viele OR-Constraints hinzu.
     * 
     * @param q Query
     * @param constraints Bedingungen
     * @return Query
     */
    public static Query addOrConstraints(Query q, List<GivenConstraint> constraints) {
        if (!constraints.isEmpty()) {
            Constraint constraint =
                    q.descend(constraints.get(0).name).constrain(constraints.get(0).value);
            for (GivenConstraint givenConstraint : constraints.subList(1, constraints.size())) {
                constraint =
                        q.descend(givenConstraint.name).constrain(givenConstraint.value)
                                .or(constraint);
            }
        }
        return q;
    }

    /**
     * Fügt einer Query beliebig viele AND-Constraints hinzu.
     * 
     * @param q Query
     * @param constraints Bedingungen
     * @return Query
     */
    public static Query addAndConstraints(Query q, List<GivenConstraint> constraints) {
        for (GivenConstraint givenConstraint : constraints) {
            q.descend(givenConstraint.name).constrain(givenConstraint.value);
        }
        return q;
    }
}

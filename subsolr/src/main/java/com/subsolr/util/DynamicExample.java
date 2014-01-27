package com.subsolr.util;

import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.resultset.ResultSet;

import java.util.Map;

import static com.googlecode.cqengine.query.QueryFactory.*;

/**
 * Demonstrates generating attributes on-the-fly using reflection for fields in a POJO, building indexes on those
 * attributes on-the-fly, and then running queries against fields in the POJO.
 *
 * @author ngallagher
 * @since 2013-07-05 11:54
 */
public class DynamicExample {

    public static void main(String[] args) {
        // Generate attributes dynamically for fields in the given POJO...
        Map<String, Attribute<Map, Comparable>> attributes = DynamicIndexer.generateAttributesForKeysInMap(Map.class);
        // Build indexes on the dynamically generated attributes...
        IndexedCollection<Map> cars = DynamicIndexer.newAutoIndexedCollection(attributes.values());

        // Add some objects to the collection...
        cars.add(new Car(1, "ford", "focus", 4, 9000));
        cars.add(new Car(2, "ford", "mondeo", 5, 10000));
        cars.add(new Car(2, "ford", "fiesta", 3, 2000));
        cars.add(new Car(3, "honda", "civic", 5, 11000));

        Query<Car> query = and(
                equal(attributes.get("manufacturer"), "ford"),
                lessThan(attributes.get("doors"), 5),
                greaterThan(attributes.get("horsepower"), 3000)
        );
        ResultSet<Car> results = cars.retrieve(query);

        System.out.println("Ford cars with less than 5 doors and horsepower greater than 3000:- ");
        System.out.println("Using NavigableIndex: " + (results.getRetrievalCost() == 40));
        for (Car car : results) {
            System.out.println(car);
        }

        // Prints:
        //    Ford cars with less than 5 doors and horsepower greater than 3000:-
        //    Using NavigableIndex: true
        //    Car{carId=1, manufacturer='ford', model='focus', doors=4, horsepower=9000}
    }
}
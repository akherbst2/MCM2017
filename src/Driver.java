/**
 * @author Alyssa Herbst
 * created on 1/19/2017.
 */

import java.util.*;
import java.io.*;

import static java.lang.Math.*;

public class Driver {
    static StringBuilder sb;

    public static void main(String[] args) {

        List<Road> rds = new ArrayList<>();
        rds.add(new Road(50000, 1));
        rds.add(new Road((100000), 2));
        rds.add(new Road((150000), 3));


        CarGenerator gen = new CarGenerator(rds, 0, 0.1);

        for(int i= 0; i < 100; i++) {
            gen.generate();
        }
        for(Road road:rds) {
            System.out.println(road.getCars().size());
        }

    }

}
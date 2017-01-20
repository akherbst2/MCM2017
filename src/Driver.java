/**
 * @author Alyssa Herbst
 * created on 1/19/2017.
 */

import java.util.*;

public class Driver {
    static StringBuilder sb;

    public static void main(String[] args) {

        List<AlyssaRoad> rds = new ArrayList<>();
        rds.add(new AlyssaRoad(50000, 1));
        rds.add(new AlyssaRoad((100000), 2));
        rds.add(new AlyssaRoad((150000), 3));


        AlyssaCarGenerator gen = new AlyssaCarGenerator(rds, 0, 0.1);

        for(int i= 0; i < 100; i++) {
            gen.generate();
        }
        for(AlyssaRoad road:rds) {
            System.out.println(road.getCars().size());
        }

    }

}
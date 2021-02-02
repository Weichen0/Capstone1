package com.example.capstone;

import android.graphics.Color;

import java.util.ArrayList;

public class Format {
    static int[] colorClassArray = new int[]{
            Color.rgb(0,51,51),
            Color.rgb(0,102,102),
            Color.rgb(0,153,153),
            Color.rgb(0,25,51),
            Color.rgb(0,51,102),
            Color.rgb(0,76,153),
            Color.rgb(0,51,25),
            Color.rgb(0,102,51),
            Color.rgb(0,153,76),
            Color.rgb(27, 38, 49 ),
            Color.rgb(93, 109, 126 ),
            Color.rgb(195, 155, 211),
            Color.rgb(31, 97, 141)


    };

    public static int[] getColorClassArray() {
        return colorClassArray;

    }
    public static class mostTable {
        public static ArrayList<String> getMostIndex(ArrayList<Integer> yVal, ArrayList<String> xVal) {
            ArrayList<String> x = new ArrayList<String>();
            ArrayList<Integer> y = new ArrayList<Integer>();
            ArrayList<String> most = new ArrayList<>();
            String Val ;
            for (int i = 0; i < yVal.size(); i++){
                if (i == 0){
                    y.clear();
                    x.clear();
                    x.add(xVal.get(i));
                    y.add(yVal.get(i));
                }
                if (yVal.get(i) > y.get(0)){
                    y.clear();
                    y.add(yVal.get(i));
                    x.clear();
                    x.add(xVal.get(i));
                }
                if (yVal.get(i) == y.get(0)){
                    if (x.get(0) != xVal.get(i)){
                        y.add(yVal.get(i));
                        x.add(xVal.get(i));
                    }
                }
            }
            for (int i = 0; i < y.size(); i++){
                Val = x.get(i) + " " + y.get(i) ;
                most.add(Val);
            }


            return most ;

        }

    }
    public static class getFactors{
        public static ArrayList<String> getxVal (String Fac){
            ArrayList <String> val = new ArrayList<>();
            val.clear();
            if (Fac.equals("All")) {
                val.add("indirect");
                val.add("contact");
                val.add("aerosol");
                val.add("clustering");
                val.add("environmental");
                val.add("touch");
                val.add("hygiene");
                val.add("local");
                val.add("international");
                val.add("visit");
                val.add("religion");
                val.add("sports");
                val.add("restaurant");
                val.add("work");


            } else if (Fac.equals("Primary")) {
                val.add("indirect");
                val.add("contact");
                val.add("aerosol");


            } else if (Fac.equals("Secondary")) {
                val.add("clustering");
                val.add("environmental");
                val.add("touch");
                val.add("hygiene");

            } else if (Fac.equals("Exposure")) {
                val.add("local");
                val.add("international");
                val.add("visit");
                val.add("religion");
                val.add("sports");
                val.add("restaurant");
                val.add("work");

        }
            return val;
        }
        public static String getxLabel (String Fac){
            String label = " ";
            if (Fac.equals("All")) {

                label = "All";

            } else if (Fac.equals("Primary")) {

                label = "Primary Factor";

            } else if (Fac.equals("Secondary")) {

                label = "Secondary Factor";

            } else if (Fac.equals("Exposure")) {

                label = "Type of Exposure";
            }
            return label;
        }
    }

}

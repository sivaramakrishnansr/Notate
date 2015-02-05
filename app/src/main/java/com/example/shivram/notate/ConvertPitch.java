package com.example.shivram.notate;

/**
 * Created by Shivram on 2/4/2015.
 */
public class ConvertPitch {
    public ConvertPitch(){

    }
    public String convert(Float values) {
        for (int i = 1; i <= 8; i++) {
            if (values >= (15.6 * i) && values < (17.32 * i)) {
                return "C" + Integer.toString(i);
            }
            if (values >= (17.32 * i) && values < (18.35 * i)) {
                return "C# " + Integer.toString(i);
            }
            if (values >= (18.35 * i) && values < (19.45 * i)) {
                return "D" + Integer.toString(i);
            }
            if (values >= (19.45 * i) && values < (20.60 * i)) {
                return "D# " + Integer.toString(i);
            }
            if (values >= (20.60 * i) && values < (21.83 * i)) {
                return "E" + Integer.toString(i);
            }
            if (values >= (21.83 * i) && values < (23.12 * i)) {
                return "F" + Integer.toString(i);
            }
            if (values >= (23.12 * i) && values < (24.5 * i)) {
                return "G" + Integer.toString(i);
            }
            if (values >= (24.5 * i) && values < (25.96 * i)) {
                return "G# " + Integer.toString(i);
            }
            if (values >= (25.96 * i) && values < (27.50 * i)) {
                return "A" + Integer.toString(i);
            }
            if (values >= (27.50 * i) && values < (29.14 * i)) {
                return "A# " + Integer.toString(i);
            }
            if (values >= (29.14 * i) && values < (30.87 * i)) {
                return "B" + Integer.toString(i);
            }
        }
        return "Nil";

    }
}

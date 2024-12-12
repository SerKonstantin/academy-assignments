package org.academy;

public class Util {
    public static Boolean compareAreas(PlainGeometryFigure figure1, PlainGeometryFigure figure2) {
        double precision = 0.001;
        return Math.abs(figure1.getArea() - figure2.getArea()) < precision;
    }
}

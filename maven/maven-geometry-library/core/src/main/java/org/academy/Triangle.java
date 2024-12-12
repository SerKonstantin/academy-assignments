package org.academy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Triangle implements PlainGeometryFigure {
    private Point corner1;
    private Point corner2;
    private Point corner3;

    private Double edgeLength(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p1.getX() - p2.getX()), 2) + Math.pow((p1.getY() - p2.getY()), 2));
    }

    public Double getArea() {
        return edgeLength(corner1, corner2) + edgeLength(corner2, corner3) + edgeLength(corner3, corner1);
    }

    public Double getPerimeter() {
        Double c1 = corner1.getX() * (corner2.getY() - corner3.getY());
        Double c2 = corner2.getX() * (corner3.getY() - corner1.getY());
        Double c3 = corner3.getX() * (corner1.getY() - corner2.getY());
        return (c1 + c2 + c3) / 2;
    }
}

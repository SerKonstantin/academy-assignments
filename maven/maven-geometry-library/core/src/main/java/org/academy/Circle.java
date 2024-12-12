package org.academy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Circle implements PlainGeometryFigure {
    private Point center;
    private Double radius;

    public Double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    public Double getPerimeter() {
        return Math.PI * 2 * radius;
    }
}

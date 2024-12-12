package org.academy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Rectangle implements PlainGeometryFigure {
    // Omit corner-point and orientation for simplicity
    private Double length;
    private Double width;


    public Double getArea() {
        return length * width;
    }

    public Double getPerimeter() {
        return 2 * (length + width);
    }

    public Boolean isSquare() {
        return Math.abs(length - width) < 0.001;
    }
}

// File: QuantityLength.java

enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double conversionFactorToFeet;

    LengthUnit(double factor) {
        this.conversionFactorToFeet = factor;
    }

    public double convertToBaseUnit(double value) {
        return value * conversionFactorToFeet;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactorToFeet;
    }
}

// ONLY ONE PUBLIC CLASS
public class QuantityLength {

    private final double value;
    private final LengthUnit unit;
    private static final double EPSILON = 0.0001;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    // Convert
    public QuantityLength convertTo(LengthUnit targetUnit) {
        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);
        return new QuantityLength(round(converted), targetUnit);
    }

    // Add
    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sum = base1 + base2;
        double result = targetUnit.convertFromBaseUnit(sum);

        return new QuantityLength(round(result), targetUnit);
    }

    // Equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength)) return false;

        QuantityLength other = (QuantityLength) obj;

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPSILON;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    // Test main method
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println(q1.convertTo(LengthUnit.INCHES)); // 12 inches
        System.out.println(q1.add(q2, LengthUnit.FEET));     // 2 feet
        System.out.println(q2.equals(new QuantityLength(1.0, LengthUnit.FEET))); // true
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}
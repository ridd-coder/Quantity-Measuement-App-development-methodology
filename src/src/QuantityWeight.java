// File: QuantityWeight.java

enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factorToKg;

    WeightUnit(double factor) {
        this.factorToKg = factor;
    }

    // Convert → base unit (kg)
    public double convertToBaseUnit(double value) {
        return value * factorToKg;
    }

    // Convert ← base unit (kg)
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factorToKg;
    }
}

// ONLY ONE PUBLIC CLASS
public class QuantityWeight {

    private final double value;
    private final WeightUnit unit;
    private static final double EPSILON = 1e-6;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    // Convert
    public QuantityWeight convertTo(WeightUnit targetUnit) {
        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);
        return new QuantityWeight(round(converted), targetUnit);
    }

    // Add (default → first unit)
    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    // Add (explicit target unit)
    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sum = base1 + base2;
        double result = targetUnit.convertFromBaseUnit(sum);

        return new QuantityWeight(round(result), targetUnit);
    }

    // Equality (type-safe)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPSILON;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }

    // Test Main
    public static void main(String[] args) {

        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight w3 = new QuantityWeight(2.20462, WeightUnit.POUND);

        System.out.println(w1.equals(w2)); // true
        System.out.println(w1.equals(w3)); // true (~)

        System.out.println(w1.convertTo(WeightUnit.GRAM)); // 1000 g
        System.out.println(w3.convertTo(WeightUnit.KILOGRAM)); // ~1 kg

        System.out.println(w1.add(w2)); // 2 kg
        System.out.println(w1.add(w2, WeightUnit.GRAM)); // 2000 g
    }
}
class UC7 {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.0328084);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feet) {
            return feet / toFeetFactor;
        }
    }

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        // 🔥 UC7: Add with explicit target unit
        public static QuantityLength add(QuantityLength a, QuantityLength b, LengthUnit targetUnit) {
            if (a == null || b == null) throw new IllegalArgumentException("Null operand");
            if (targetUnit == null) throw new IllegalArgumentException("Target unit null");

            double sumFeet = a.toFeet() + b.toFeet();
            double result = targetUnit.fromFeet(sumFeet);

            return new QuantityLength(result, targetUnit);
        }

        // UC6 compatibility (result in first operand unit)
        public static QuantityLength add(QuantityLength a, QuantityLength b) {
            return add(a, b, a.unit);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // Demo
    public static void main(String[] args) {

        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println(add(a, b, LengthUnit.FEET));   // 2.0 FEET
        System.out.println(add(a, b, LengthUnit.INCH));   // 24.0 INCH
        System.out.println(add(a, b, LengthUnit.YARD));   // ~0.667 YARD
    }
}
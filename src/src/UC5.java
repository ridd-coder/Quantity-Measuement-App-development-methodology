public class UC5 {

    // Enum (base unit: FEET)
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

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    // Quantity class
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        // 🔥 Convert instance to another unit
        public double convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) throw new IllegalArgumentException("Target unit null");

            double feet = this.toFeet();
            return targetUnit.fromFeet(feet);
        }

        // 🔥 Static conversion API
        public static double convert(double value, LengthUnit source, LengthUnit target) {
            if (source == null || target == null)
                throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Invalid value");

            double feet = source.toFeet(value);
            return target.fromFeet(feet);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toFeet());
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // Main demo
    public static void main(String[] args) {

        System.out.println(QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCH)); // 12.0
        System.out.println(QuantityLength.convert(3.0, LengthUnit.YARD, LengthUnit.FEET)); // 9.0
        System.out.println(QuantityLength.convert(36.0, LengthUnit.INCH, LengthUnit.YARD)); // 1.0
        System.out.println(QuantityLength.convert(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH)); // ~0.3937
    }
}
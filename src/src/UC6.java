public class UC6 {

    // Enum (base: FEET)
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

        // 🔥 Comparison methods
        public boolean isGreaterThan(QuantityLength other) {
            return Double.compare(this.toFeet(), other.toFeet()) > 0;
        }

        public boolean isLessThan(QuantityLength other) {
            return Double.compare(this.toFeet(), other.toFeet()) < 0;
        }

        public boolean isGreaterOrEqual(QuantityLength other) {
            return Double.compare(this.toFeet(), other.toFeet()) >= 0;
        }

        public boolean isLessOrEqual(QuantityLength other) {
            return Double.compare(this.toFeet(), other.toFeet()) <= 0;
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
    }

    // Demo
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(2.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(24.0, LengthUnit.INCH);

        System.out.println(q1.equals(q2)); // true
        System.out.println(q1.isGreaterThan(q2)); // false
        System.out.println(q1.isLessOrEqual(q2)); // true

        QuantityLength q3 = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println(q3.isGreaterThan(q1)); // true
    }
}
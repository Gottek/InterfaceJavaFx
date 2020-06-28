package Main;


public class Position<T extends Number> {

        private T x;

        private T y;

        public Position( T x, T y) {
            setX(x);
            setY(y);
        }

        public T getX() { return x; }

        public void setX(T x) {
            this.x = x;
        }

        public T getY() { return y; }

        public void setY(T y) {
            this.y = y;
        }
}

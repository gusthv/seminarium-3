import java.util.Random;

public class InheritenceRandom extends java.util.Random {
    @Override
    public int nextInt() {
        return Math.abs(super.nextInt());
    }
}
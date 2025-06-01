import java.util.Random;

public class Main {
    public static void main(String[] args){
        InheritenceRandom inheritedRandom = new InheritenceRandom();
        int myPositiveNumber = inheritedRandom.nextInt();
        System.out.println(myPositiveNumber);
        CompositionRandom compositionRandom = new CompositionRandom();
        int compositionPositive = compositionRandom.positiveInt();
        System.out.println(compositionPositive);
    }
}

class InheritenceRandom extends java.util.Random {
    @Override
    public int nextInt() {
        return Math.abs(super.nextInt());
    }
}
class CompositionRandom{
    private Random random = new Random();

    public int positiveInt(){
        int value;
        do {
            value = random.nextInt();
        } while (value < 0);
        
        return value;
    }
}
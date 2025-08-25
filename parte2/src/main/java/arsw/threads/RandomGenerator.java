package arsw.threads;

import java.util.Random;

public class RandomGenerator {
	private static final Random random=new Random(System.currentTimeMillis());

    private RandomGenerator() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

	public static int nextInt(int max){
		return random.nextInt(max);
	}
	
}

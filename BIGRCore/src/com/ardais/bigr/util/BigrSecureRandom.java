package com.ardais.bigr.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.ardais.bigr.api.ApiFunctions;

/**
 * Provide a singleton source of secure random numbers that can't be reseeded.
 * 
 * To ensure that all callers get a reliably random sequence, we don't provide any
 * way to reseed the generator (since a single instance is shared among all callers,
 * this is a concern, since reseeding would affect all callers).
 */
public class BigrSecureRandom {
  // It would have been nice to make this extend SecureRandom but due to vagaries in
  // its implementation that wasn't reasonable (e.g. package-private methods that we would
  // have needed to call in the constructor).

  /**
   * This is the SecureRandom instance that we delegate to.
   */
  private static SecureRandom _randomInstance = null;

  /**
   * This is a singleton instance of this class.
   */
  private static BigrSecureRandom _singletonInstance = new BigrSecureRandom();

  /**
   * Private constructor to prevent instantiation.
   */
  private BigrSecureRandom() {
    try {
      _randomInstance = SecureRandom.getInstance("IBMSecureRandom");
    }
    catch (NoSuchAlgorithmException nsae) {
      try {
        _randomInstance = SecureRandom.getInstance("SHA1PRNG");
      }
      catch (NoSuchAlgorithmException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
  }
  
  /**
   * Initialize this class.  Calling this isn't required, it will be initialized on
   * first use if it is not.  But this gives an explicit way to force initialization
   * earlier than that if needed.  This can be important.  Prior to adding this method,
   * users could see very slow performance the first time the generated a random number.
   * This varied by environment, but when run inside WebSphere on Solaris it was quite
   * slow (~40 seconds).  We now call this method when the application starts up rather
   * than when the user first hits it (it is still slow, but users won't experience it).
   */
  public static void initialize() {
    // Force the random number generator in _randomInstance to do its first-time initialization,
    // which can be quite slow as noted in the comments above.  This is done for MR 6855.
    //
    _randomInstance.nextInt();
  }

  /**
   * @return the single instance of this class.
   */
  public static BigrSecureRandom getInstance() {
    return _singletonInstance;
  }

  /**
   * @see java.security.SecureRandom#generateSeed(int)
   */
  public byte[] generateSeed(int numBytes) {
    return _randomInstance.generateSeed(numBytes);
  }

  /**
   * @see java.security.SecureRandom#nextBytes(byte[])
   */
  public synchronized void nextBytes(byte[] bytes) {
    _randomInstance.nextBytes(bytes);
  }

  /**
   * @see java.util.Random#nextBoolean()
   */
  public boolean nextBoolean() {
    return _randomInstance.nextBoolean();
  }

  /**
   * @see java.util.Random#nextDouble()
   */
  public double nextDouble() {
    return _randomInstance.nextDouble();
  }

  /**
   * @see java.util.Random#nextFloat()
   */
  public float nextFloat() {
    return _randomInstance.nextFloat();
  }

  /**
   * @see java.util.Random#nextGaussian()
   */
  public synchronized double nextGaussian() {
    return _randomInstance.nextGaussian();
  }

  /**
   * @see java.util.Random#nextInt()
   */
  public int nextInt() {
    return _randomInstance.nextInt();
  }

  /**
   * @see java.util.Random#nextInt(int)
   */
  public int nextInt(int n) {
    return _randomInstance.nextInt(n);
  }

  /**
   * @see java.util.Random#nextLong()
   */
  public long nextLong() {
    return _randomInstance.nextLong();
  }
}

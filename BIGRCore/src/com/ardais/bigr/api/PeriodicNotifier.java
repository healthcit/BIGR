package com.ardais.bigr.api;

import java.util.HashMap;
import java.util.Iterator;

/**
 * This class allows you to register to receive notifications at a specified frequency.
 * <p>
 * Notifications begin as soon as an instance is created, and continues until one of two things
 * happens: the {@link #removeListener(PeriodicNotifierId)}method is called to remove a specific
 * notification client, or {@link #removeAllListeners()}is called. The caller can request that a
 * synchronous notification event be sent immediately when a client is registered, and thereafter
 * asynchronous event notifications are sent at the specified frequency.
 */
public class PeriodicNotifier {
  /**
   * The default delay in milliseconds between every notification event, set to 60 seconds.
   */
  public static final long DEFAULT_DELAY = 60000;

  private HashMap _clients = new HashMap();

  private static final PeriodicNotifier SINGLETON = new PeriodicNotifier();

  /**
   * Private: This class is intended to have only static non-private methods, and a singleton
   * instance, so prevent public instantiation.
   */
  private PeriodicNotifier() {
    super();
  }

  /**
   * Register a notification listener to be notified at the {@link #DEFAULT_DELAY}frequency.
   * 
   * @param notifyAtStartup True if the listener should always be sent an initial synchronous change
   *          event immediately when the instance is created.
   * @param listener The object that will receive periodic notifications.
   * @return An identifier that can be used to uniquely refer to this registration in the future,
   *         for example if you want to unregister by calling
   *         {@link #removeListener(PeriodicNotifierId)}you can do so by passing the returned
   *         object as a parameter to that method.
   */
  public static PeriodicNotifierId addListener(boolean notifyAtStartup,
                                               PeriodicNotifierListener listener) {
    return addListener(notifyAtStartup, DEFAULT_DELAY, listener);
  }

  /**
   * Register a notification listener to be notified at the specified frequency.
   * 
   * @param notifyAtStartup True if the listener should always be sent an initial synchronous change
   *          event immediately when the instance is created.
   * @param delay The delay in milliseconds between successive notifications. This must be at least
   *          15000 (15 seconds).
   * @param listener The object that will receive periodic notifications.
   * @return An identifier that can be used to uniquely refer to this registration in the future,
   *         for example if you want to unregister by calling
   *         {@link #removeListener(PeriodicNotifierId)}you can do so by passing the returned
   *         object as a parameter to that method.
   */
  public static PeriodicNotifierId addListener(boolean notifyAtStartup, long delay,
                                               PeriodicNotifierListener listener) {
    PeriodicNotifierId id = new PeriodicNotifierId();
    synchronized (SINGLETON._clients) {
      PeriodicNotifierInstance notifier = SINGLETON.new PeriodicNotifierInstance(id,
          notifyAtStartup, delay, listener);
      notifier.start();
      SINGLETON._clients.put(id, notifier);
    }
    return id;
  }

  /**
   * Unregister the specified notification client. The client's listener will no longer receive
   * periodic notifications.
   * 
   * @param id The id of the registered client, as returned originally by addListener.
   */
  public static void removeListener(PeriodicNotifierId id) {
    synchronized (SINGLETON._clients) {
      PeriodicNotifierInstance notifier = (PeriodicNotifierInstance) SINGLETON._clients.get(id);
      if (notifier != null) {
        if (notifier.isAlive()) {
          notifier.interrupt();
        }
        SINGLETON._clients.remove(id);
      }
    }
  }

  /**
   * Unregister all notification clients. The client listeners will no longer receive periodic
   * notifications.
   */
  public static void removeAllListeners() {
    synchronized (SINGLETON._clients) {
      Iterator iter = SINGLETON._clients.values().iterator();
      while (iter.hasNext()) {
        PeriodicNotifierInstance notifier = (PeriodicNotifierInstance) iter.next();
        if (notifier.isAlive()) {
          notifier.interrupt();
        }
      }
      SINGLETON._clients.clear();
    }
  }

  /**
   * This class is a thread that calls a user-supplied listener at a specified frequency. Currently,
   * one thread is created for each client registered with {@link PeriodicNotifier}.
   */
  private class PeriodicNotifierInstance extends Thread {
    PeriodicNotifierId _periodicNotifierId;

    private PeriodicNotifierListener _listener;

    private long _delay;

    protected PeriodicNotifierInstance(PeriodicNotifierId periodicNotifierId,
                                       boolean notifyAtStartup, long delay,
                                       PeriodicNotifierListener listener) {
      if (periodicNotifierId == null) {
        throw new IllegalArgumentException("The periodicNotifierId must not be null");
      }
      if (delay < 15000) {
        throw new IllegalArgumentException("The delay must be at least 15000 (15 seconds): "
            + delay);
      }
      if (listener == null) {
        throw new IllegalArgumentException("The listener must not be null");
      }

      _periodicNotifierId = periodicNotifierId;
      _listener = listener;
      _delay = delay;

      setDaemon(true);
      
      // If notifyAtStartup is true, the first notification is supposed to be synchronous,
      // so we can't do it in the run() method, we have to do it here.  Do this once all
      // of the other constructor setup is complete.
      //
      if (notifyAtStartup) {
        notifyListener();
      }
    }

    protected void notifyListener() {
      _listener.onPeriodNotificationEvent(_periodicNotifierId);
    }

    public void run() {
      boolean stopRunning = false;
      while (!stopRunning && !Thread.interrupted()) {
        try {
          Thread.sleep(_delay);
        }
        catch (InterruptedException e) {
          stopRunning = true;
        }
        // To correctly implement the notifyAtStartup == false setting, we have to call
        // notifyListener after the delay, not before it.
        //
        if (!stopRunning) {
          notifyListener();
        }
      }
    }
  }
}

package com.ardais.bigr.api;

import java.io.File;

/**
 * This class allows you to register to receive notifications when a file changes. It periodically
 * checks the file's modification date (with checks done at a user-specified frequency). If the
 * file's modification date on a check is later than its modification date on the previous check,
 * notification is sent to a user-supplied listener object.
 * <p>
 * Monitoring begins as soon as an instance is created, and continues until one of two things
 * happens: the {@link #stopWatching()}method is called, or the monitor is removed from
 * {@link com.ardais.bigr.api.PeriodicNotifier}, perhaps because
 * {@link com.ardais.bigr.api.PeriodicNotifier#removeAllListeners()}was called.
 */
public class FileWatcher {

  private String _filename;

  private long _delay;

  private boolean _notifyAtStartup;

  private FileWatcherListener _listener;

  private PeriodicNotifierId _periodicNotifierId;

  /**
   * Create a new file watcher.
   * 
   * @param filename The full pathname of the file to monitor.
   * @param notifyAtStartup True if the listener should always be sent an initial synchronous change
   *          event immediately when the instance is created.
   * @param delay The delay in milliseconds between successive checks of the file. This must be at
   *          least 15000 (15 seconds).
   * @param listener The object that will receive notifications when a file change is detected.
   */
  public FileWatcher(String filename, boolean notifyAtStartup, long delay,
                     FileWatcherListener listener) {
    super();

    if (filename == null) {
      throw new IllegalArgumentException("The filename must not be null");
    }
    if (delay < 15000) {
      throw new IllegalArgumentException("The delay must be at least 15000 (15 seconds): " + delay);
    }
    if (listener == null) {
      throw new IllegalArgumentException("The listener must not be null");
    }

    _filename = filename;
    _delay = delay;
    _notifyAtStartup = notifyAtStartup;
    _listener = listener;

    _periodicNotifierId = PeriodicNotifier.addListener(_notifyAtStartup, _delay,
        new FileWatcherPeriodicListener());
  }

  /**
   * Stop watching the file with this FileWatcher. Once stopped, watching cannot be resumed with
   * this FileWatcher instance. A new instance would need to be created. Watching will also be
   * stopped if {@link PeriodicListener#removeAllListeners()}is called.
   */
  public void stopWatching() {
    PeriodicNotifier.removeListener(_periodicNotifierId);
  }

  /**
   * @return Returns the interval in milliseconds between file-change checks.
   */
  public long getDelay() {
    return _delay;
  }

  /**
   * @return Returns the filename.
   */
  public String getFilename() {
    return _filename;
  }

  /**
   * @return Returns the listener that will receive notifications of file changes.
   */
  public FileWatcherListener getListener() {
    return _listener;
  }

  /**
   * @return Returns whether a synchronous change-notification event should be sent to the listener
   *         automatically when the FileWatcher starts watching the file. If this is false, then the
   *         listener will only receive an event if the file's modification increases from the date
   *         it had when the FileWatcher first started monitoring it.
   */
  public boolean isNotifyAtStartup() {
    return _notifyAtStartup;
  }

  /**
   * This class registers with {@link PeriodicNotifier}to receive events at the delay frequency.
   * This private inner class handles to events, checking for file changes each time it is called
   * and notifying the user-supplied listener whenever it detects a new version of the file.
   */
  private class FileWatcherPeriodicListener implements PeriodicNotifierListener {
    private File _file;

    private long _lastModif;

    private boolean _warnedAlready = false;

    private boolean _forceInitialNotification;

    protected FileWatcherPeriodicListener() {
      _file = new File(_filename);

      // Initialize _lastModif, the file's last modification date. We do this here rather
      // than when we get the first event notification, since when _notifyAtStartup is false
      // the underlying PeriodNotifier doesn't send the first event notification to us
      // until a full interval has elapsed, and we could miss a file date change in that interval
      // if we didn't initialize _lastModif here. If the file doesn't exist, _lastModif is
      // initialized to zero so that if it comes into existence at a later event, things will
      // work correctly.
      //
      _lastModif = 0;
      boolean fileExists = false;
      try {
        fileExists = _file.exists();
      }
      catch (SecurityException e) {
        ApiLogger.warn("Was not allowed to check file existence, file:[" + _filename + "].", e);
        return; // there is no point in continuing
      }
      if (fileExists) {
        // Calling lastModified() can also throw a SecurityException. However, if we reached this
        // point this is very unlikely.
        _lastModif = _file.lastModified();
      }

      // If the caller didn't request an immediate notification at startup, the first event
      // we get from the PeriodicNotifier won't be immediate, it will be delivered asynchronously
      // one delay unit later. In this case, we don't want our listener to get a file-change
      // event at that time unless that file modification date has actually increased between
      // now and when we get that event. Setting __forceInitialNotification = _notifyAtStartup
      // here accomplishes that.
      //
      _forceInitialNotification = _notifyAtStartup;
    }

    /**
     * This processes the periodic timer events, checking the file we're watching for date changes
     * and notifying the client listener of changes if appropriate.
     */
    protected void checkAndNotify() {
      boolean fileExists = false;
      boolean forceNotification = _forceInitialNotification;

      _forceInitialNotification = false;

      try {
        fileExists = _file.exists();
      }
      catch (SecurityException e) {
        ApiLogger.warn("Was not allowed to check file existence, file:[" + _filename + "].", e);
        return; // there is no point in continuing
      }

      if (fileExists) {
        // Calling lastModified() can also throw a SecurityException. However, if we reached this
        // point this is very unlikely.
        long lastMod = _file.lastModified();
        if (forceNotification || (lastMod > _lastModif)) {
          _lastModif = lastMod;
          _listener.onFileChanged(FileWatcher.this);
          _warnedAlready = false;
        }
      }
      else {
        if (!_warnedAlready) {
          ApiLogger.debug("FileWatcher: [" + _filename + "] does not exist.");
          _warnedAlready = true;
        }
      }
    }

    /**
     * Handle notification events from the underlying PeriodicNotifier that we're listening to.
     */
    public void onPeriodNotificationEvent(PeriodicNotifierId periodicNotifierId) {
      checkAndNotify();
    }

  } // end inner class FileWatcherPeriodicListener
}

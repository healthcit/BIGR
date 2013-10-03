package com.ardais.bigr.api;

/**
 * Used by clients of {@link FileWatcher} to receive notifications of file changes.
 */
public interface FileWatcherListener {
  public void onFileChanged(FileWatcher watcher);
}

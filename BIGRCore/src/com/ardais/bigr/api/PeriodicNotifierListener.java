package com.ardais.bigr.api;

/**
 * Used by clients of {@link PeriodicNotifier} to receive event notifications.
 */
public interface PeriodicNotifierListener {
  public void onPeriodNotificationEvent(PeriodicNotifierId periodicNotifierId);
}

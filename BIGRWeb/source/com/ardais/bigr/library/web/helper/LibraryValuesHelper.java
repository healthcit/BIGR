package com.ardais.bigr.library.web.helper;

import java.util.Collection;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.util.EjbHomes;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LibraryValuesHelper {
  private static boolean _hasBeenInitialized = false;

  private static Collection _donorAccounts;

  private static String[] _appearanceValueList =
    {
      "Any",
      "1",
      "2",
      "3",
      "4",
      "5",
      "6",
      "7",
      "8",
      "9",
      "10",
      "11",
      "12",
      "13",
      "14",
      "15",
      "16",
      "17",
      "18",
      "19",
      "20",
      "21",
      "22",
      "23",
      "24",
      "25",
      "26",
      "27",
      "28",
      "29",
      "30",
      "31",
      "32",
      "33",
      "34",
      "35",
      "36",
      "37",
      "38",
      "39",
      "40",
      "41",
      "42",
      "43",
      "44",
      "45",
      "46",
      "47",
      "48",
      "49",
      "50",
      "51",
      "52",
      "53",
      "54",
      "55",
      "56",
      "57",
      "58",
      "59",
      "60",
      "61",
      "62",
      "63",
      "64",
      "65",
      "66",
      "67",
      "68",
      "69",
      "70",
      "71",
      "72",
      "73",
      "74",
      "75",
      "76",
      "77",
      "78",
      "79",
      "80",
      "81",
      "82",
      "83",
      "84",
      "85",
      "86",
      "87",
      "88",
      "89",
      "90",
      "91",
      "92",
      "93",
      "94",
      "95",
      "96",
      "97",
      "98",
      "99",
      "100" };

  private static String[] _ageAtCollectionFromList =
    {
      "Any",
      "0",
      "5",
      "10",
      "15",
      "20",
      "25",
      "30",
      "35",
      "40",
      "45",
      "50",
      "55",
      "60",
      "65",
      "70",
      "75",
      "80",
      "85" };

  private static String[] _ageAtCollectionToList =
    {
      "Any",
      "5",
      "10",
      "15",
      "20",
      "25",
      "30",
      "35",
      "40",
      "45",
      "50",
      "55",
      "60",
      "65",
      "70",
      "75",
      "80",
      "85",
      "89+" };

  public static Collection getDonorAccounts() {
    initIfNecessary();
    return _donorAccounts;
  }

  /**
   * Returns the appearanceValueList.
   * @return String[]
   */
  public static String[] getAppearanceValueList() {
    initIfNecessary();
    return _appearanceValueList;
  }

  /**
   * Returns the ageAtCollectionFromList.
   * @return String[]
   */
  public static String[] getAgeAtCollectionFromList() {
    initIfNecessary();
    return _ageAtCollectionFromList;
  }

  /**
   * Returns the ageAtCollectionToList.
   * @return String[]
   */
  public static String[] getAgeAtCollectionToList() {
    initIfNecessary();
    return _ageAtCollectionToList;
  }

  private static synchronized void initIfNecessary() {
    if (!_hasBeenInitialized) {
      initOrRefresh();
      _hasBeenInitialized = true;

    }
  }

  /**
   * Initialize the static data structures in this class.
   */
  public static synchronized void initOrRefresh() {
    try {
      ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
      ListGenerator list = home.create();
      _donorAccounts = list.getDonorAccounts();
    } catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

}

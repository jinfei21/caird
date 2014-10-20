package com.ctrip.caird.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Helper functions FTW!
 * 
 * 
 */
public class Utils {

    public static final String NEWLINE = System.getProperty("line.separator");

    /**
     * Pattern for splitting a string based on commas
     */
    public static final Pattern COMMA_SEP = Pattern.compile("\\s*,\\s*");

    /**
     * Print an error and exit with error code 1
     * 
     * @param message The error to print
     */
    public static void croak(String message) {
        System.err.println(message);
        System.exit(1);
    }

    /**
     * Print an error and exit with the given error code
     * 
     * @param message The error to print
     * @param errorCode The error code to exit with
     */
    public static void croak(String message, int errorCode) {
        System.err.println(message);
        System.exit(errorCode);
    }

    /**
     * Delete the given file
     * 
     * @param file The file to delete
     */
    public static void rm(File file) {
        if(file != null)
            rm(Collections.singletonList(file));
    }

    /**
     * Delete an array of files
     * 
     * @param files Files to delete
     */
    public static void rm(File[] files) {
        if(files != null)
            for(File f: files)
                rm(f);
    }

    /**
     * Delete the given file
     * 
     * @param file The file to delete
     */
    public static void rm(String file) {
        if(file != null)
            rm(Collections.singletonList(new File(file)));
    }

    /**
     * Delete all the given files
     * 
     * @param files A collection of files to delete
     */
    public static void rm(Iterable<File> files) {
        if(files != null) {
            for(File f: files) {
                if(f.isDirectory()) {
                    rm(Arrays.asList(f.listFiles()));
                    f.delete();
                } else {
                    f.delete();
                }
            }
        }
    }

    /**
     * Sort a collection to a List
     * 
     * @param collection to be converted to a sorted list
     */
    public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        Collections.sort(list);
        return list;
    }

  

  
    /**
     * @return true iff the argument is the name of a readable file
     */
    public static boolean isReadableFile(String fileName) {
        return isReadableFile(new File(fileName));
    }

    /**
     * @return true iff the argument is a readable file
     */
    public static boolean isReadableFile(File f) {
        return f.exists() && f.isFile() && f.canRead();
    }

    /**
     * @return true iff the argument is the name of a readable directory
     */
    public static boolean isReadableDir(String dirName) {
        return isReadableDir(new File(dirName));
    }

    /**
     * @return true iff the argument is a readable directory
     */
    public static boolean isReadableDir(File d) {
        return d.exists() && d.isDirectory() && d.canRead();
    }

    /**
     * Throw an IllegalArgumentException if any of the given objects are null
     * 
     * @param objects The objects to test
     */
    public static void assertNotNull(Object... objects) {
        assertNotNull("Null argument not allowed", objects);
    }

    /**
     * Throw an IllegalArgumentException if any of the given objects are null
     * 
     * @param s The error message to give
     * @param objects The objects to test
     */
    public static void assertNotNull(String s, Object... objects) {
        for(Object o: objects)
            if(o == null)
                throw new IllegalArgumentException(s);
    }

    /**
     * Throw an IllegalArgumentException if the argument is null, otherwise just
     * return the argument.
     * 
     * Useful for assignment as in this.thing = Utils.notNull(thing);
     * 
     * @param <T> The type of the thing
     * @param t The thing to check for nullness.
     * @param message The message to put in the exception if it is null
     */
    public static <T> T notNull(T t, String message) {
        if(t == null)
            throw new IllegalArgumentException(message);
        return t;
    }

    /**
     * Throw an IllegalArgumentException if the argument is null, otherwise just
     * return the argument.
     * 
     * Useful for assignment as in this.thing = Utils.notNull(thing);
     * 
     * @param <T> The type of the thing
     * @param t The thing to check for nullness.
     */
    public static <T> T notNull(T t) {
        if(t == null)
            throw new IllegalArgumentException("This object MUST be non-null.");
        return t;
    }

    /**
     * Return the value v if min <= v <= max, otherwise throw an exception
     * 
     * @param value The value to check
     * @param min The minimum allowable value
     * @param max The maximum allowable value
     * @return The value, if it is in the range
     */
    public static int inRange(int value, int min, int max) {
        if(value < min)
            throw new IllegalArgumentException("The value " + value
                                               + " is lower than the minimum value of " + min);
        else if(value > max)
            throw new IllegalArgumentException("The value " + value
                                               + " is greater than the maximum value of " + max);
        else
            return value;
    }

    /**
     * Computes the percentage, taking care of division by 0
     */
    public static double safeGetPercentage(long rawNum, long total) {
        return total == 0 ? 0.0d : rawNum / (float) total;
    }

    /**
     * Computes the percentage, taking care of division by 0.
     * 
     * @return number between 0-100+
     */
    public static int safeGetPercentage(float rawNum, float total) {
        return total == 0f ? 0 : Math.round(rawNum * 100f / total);
    }

    /**
     * Computes sum of a {@link java.lang.Long} list
     * 
     * @param list
     * @return sum of the list
     */
    public static long sumLongList(List<Long> list) {
        long sum = 0;
        for(Long val: list) {
            sum += val;
        }
        return sum;
    }

    /**
     * Compute the average of a {@link java.lang.Long} list
     * 
     * @param list
     * @return
     */
    public static long avgLongList(List<Long> list) {
        long sum = sumLongList(list);
        return list.size() == 0 ? 0L : sum / list.size();
    }

    /**
     * Compute the sum of a {@link java.lang.Double} list
     * 
     * @param list
     * @return
     */
    public static double sumDoubleList(List<Double> list) {
        double sum = 0.0;
        for(Double val: list) {
            sum += val;
        }
        return sum;
    }

    /**
     * Compute the average of a {@link java.lang.Double} list
     * 
     * @param list
     * @return
     */
    public static double avgDoubleList(List<Double> list) {
        double sum = sumDoubleList(list);
        return list.size() == 0 ? 0.0 : sum / list.size();
    }

    /**
     * Gets hash code of an object, optionally returns hash code based on the
     * "deep contents" of array if the object is an array.
     * <p>
     * If {@code o} is null, 0 is returned; if {@code o} is an array, the
     * corresponding {@link Arrays#deepHashCode(Object[])}, or
     * {@link Arrays#hashCode(int[])} or the like is used to calculate the hash
     * code.
     */
    public static int deepHashCode(Object o) {
        if(o == null) {
            return 0;
        }
        if(!o.getClass().isArray()) {
            return o.hashCode();
        }
        if(o instanceof Object[]) {
            return Arrays.deepHashCode((Object[]) o);
        }
        if(o instanceof boolean[]) {
            return Arrays.hashCode((boolean[]) o);
        }
        if(o instanceof char[]) {
            return Arrays.hashCode((char[]) o);
        }
        if(o instanceof byte[]) {
            return Arrays.hashCode((byte[]) o);
        }
        if(o instanceof short[]) {
            return Arrays.hashCode((short[]) o);
        }
        if(o instanceof int[]) {
            return Arrays.hashCode((int[]) o);
        }
        if(o instanceof long[]) {
            return Arrays.hashCode((long[]) o);
        }
        if(o instanceof float[]) {
            return Arrays.hashCode((float[]) o);
        }
        if(o instanceof double[]) {
            return Arrays.hashCode((double[]) o);
        }
        throw new AssertionError();
    }

    /**
     * Determines if two objects are equal as determined by
     * {@link Object#equals(Object)}, or "deeply equal" if both are arrays.
     * <p>
     * If both objects are null, true is returned; if both objects are array,
     * the corresponding {@link Arrays#deepEquals(Object[], Object[])}, or
     * {@link Arrays#equals(int[], int[])} or the like are called to determine
     * equality.
     * <p>
     * Note that this method does not "deeply" compare the fields of the
     * objects.
     */
    public static boolean deepEquals(Object o1, Object o2) {
        if(o1 == o2) {
            return true;
        }
        if(o1 == null || o2 == null) {
            return false;
        }

        Class<?> type1 = o1.getClass();
        Class<?> type2 = o2.getClass();
        if(!(type1.isArray() && type2.isArray())) {
            return o1.equals(o2);
        }
        if(o1 instanceof Object[] && o2 instanceof Object[]) {
            return Arrays.deepEquals((Object[]) o1, (Object[]) o2);
        }
        if(type1 != type2) {
            return false;
        }
        if(o1 instanceof boolean[]) {
            return Arrays.equals((boolean[]) o1, (boolean[]) o2);
        }
        if(o1 instanceof char[]) {
            return Arrays.equals((char[]) o1, (char[]) o2);
        }
        if(o1 instanceof byte[]) {
            return Arrays.equals((byte[]) o1, (byte[]) o2);
        }
        if(o1 instanceof short[]) {
            return Arrays.equals((short[]) o1, (short[]) o2);
        }
        if(o1 instanceof int[]) {
            return Arrays.equals((int[]) o1, (int[]) o2);
        }
        if(o1 instanceof long[]) {
            return Arrays.equals((long[]) o1, (long[]) o2);
        }
        if(o1 instanceof float[]) {
            return Arrays.equals((float[]) o1, (float[]) o2);
        }
        if(o1 instanceof double[]) {
            return Arrays.equals((double[]) o1, (double[]) o2);
        }
        throw new AssertionError();
    }

    /**
     * Returns a set of objects that were added to the target list
     * 
     * getAddedInTarget(current, null) - nothing was added, returns null. <br>
     * getAddedInTarget(null, target) - everything in target was added, return
     * target. <br>
     * getAddedInTarget(null, null) - neither added nor deleted, return null. <br>
     * getAddedInTarget(current, target)) - returns new partition not found in
     * current.
     * 
     * @param current Set of objects present in current
     * @param target Set of partitions present in target
     * @return A set of added partitions in target or empty set
     */
    public static <T> Set<T> getAddedInTarget(Set<T> current, Set<T> target) {
        if(current == null || target == null) {
            return new HashSet<T>();
        }
        return getDiff(target, current);
    }

    /**
     * Returns a set of objects that were deleted in the target set
     * 
     * getDeletedInTarget(current, null) - everything was deleted, returns
     * current. <br>
     * getDeletedInTarget(null, target) - everything in target was added, return
     * target. <br>
     * getDeletedInTarget(null, null) - neither added nor deleted, return empty
     * set. <br>
     * getDeletedInTarget(current, target)) - returns deleted partition not
     * found in target.
     * 
     * @param current Set of objects currently present
     * @param target Set of target objects
     * @return A set of deleted objects in target or empty set
     */
    public static <T> Set<T> getDeletedInTarget(final Set<T> current, final Set<T> target) {
        if(current == null || target == null) {
            return new HashSet<T>();
        }
        return getDiff(current, target);
    }

    private static <T> Set<T> getDiff(final Set<T> source, final Set<T> dest) {
        Set<T> diff = new HashSet<T>();
        for(T id: source) {
            if(!dest.contains(id)) {
                diff.add(id);
            }
        }
        return diff;
    }

    /**
     * Return a copy of the list sorted according to the given comparator
     * 
     * @param <T> The type of the elements in the list
     * @param l The list to sort
     * @param comparator The comparator to use for sorting
     * @return A sorted copy of the list
     */
    public static <T> List<T> sorted(List<T> l, Comparator<T> comparator) {
        List<T> copy = new ArrayList<T>(l);
        Collections.sort(copy, comparator);
        return copy;
    }

    /**
     * Return a copy of the list sorted according to the natural order
     * 
     * @param <T> The type of the elements in the list
     * @param l The list to sort
     * @return A sorted copy of the list
     */
    public static <T extends Comparable<T>> List<T> sorted(List<T> l) {
        List<T> copy = new ArrayList<T>(l);
        Collections.sort(copy);
        return copy;
    }

    /**
     * A reversed copy of the given list
     * 
     * @param <T> The type of the items in the list
     * @param l The list to reverse
     * @return The list, reversed
     */
    public static <T> List<T> reversed(List<T> l) {
        List<T> copy = new ArrayList<T>(l);
        Collections.reverse(copy);
        return copy;
    }

    /**
     * Compares two lists
     * 
     * @param <T> The type of items in the list
     * @param listA List 1
     * @param listB List 2
     * @return Returns a boolean comparing the lists
     */
    public static <T> boolean compareList(List<T> listA, List<T> listB) {
        // Both are null.
        if(listA == null && listB == null)
            return true;

        // At least one of them is null.
        if(listA == null || listB == null)
            return false;

        // If the size is different.
        if(listA.size() != listB.size())
            return false;

        // Since size is same, containsAll will be true only if same
        return listA.containsAll(listB);
    }

    /**
     * A helper function that wraps the checked parsing exception when creating
     * a URI
     * 
     * @param uri The URI to parse
     * @return a URI object.
     */
    public static URI parseUri(String uri) {
        try {
            return new URI(uri);
        } catch(URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String paddedString(String str, int totalWidth) {
        int padLength = totalWidth - str.length();
        if(padLength <= 0) {
            return str;
        }
        StringBuilder paddedStr = new StringBuilder();
        for(int i = 0; i < padLength; i++) {
            paddedStr.append(' ');
        }
        paddedStr.append(str);
        return paddedStr.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T1, T2> T1 uncheckedCast(T2 t2) {
        return (T1) t2;
    }

    /**
     * Check if a file is a symbolic link or not
     * 
     * @param symlinkFile
     * @return true if File is symlink else false
     */
    public static boolean isSymLink(File symlinkFile) {
        try {
            File canonicalFile = null;
            if(symlinkFile.getParent() != null) {
                File canonicalDir = symlinkFile.getParentFile().getCanonicalFile();
                canonicalFile = new File(canonicalDir, symlinkFile.getName());
            } else {
                canonicalFile = symlinkFile;
            }
            return !canonicalFile.getCanonicalFile().equals(canonicalFile.getAbsoluteFile());
        } catch(IOException e) {
            return false;
        }
    }

    /**
     * Given a start time, computes the next time when the wallclock will reach
     * a certain hour of the day, on a certain day of the week Eg: From today,
     * when is the next Saturday, 12PM ?
     * 
     * @param startTime start time
     * @param targetDay day of the week to choose
     * @param targetHour hour of the day to choose
     * @return calendar object representing the target time
     */
    public static GregorianCalendar getCalendarForNextRun(GregorianCalendar startTime,
                                                          int targetDay,
                                                          int targetHour) {
        long startTimeMs = startTime.getTimeInMillis();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(startTimeMs);

        // adjust time to targetHour on startDay
        cal.set(Calendar.HOUR_OF_DAY, targetHour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // check if we are past the targetHour for the current day
        if(cal.get(Calendar.DAY_OF_WEEK) != targetDay || cal.getTimeInMillis() < startTimeMs) {
            do {
                cal.add(Calendar.DAY_OF_YEAR, 1);
            } while(cal.get(Calendar.DAY_OF_WEEK) != targetDay);
        }
        return cal;
    }

    /**
     * Returns the day of week, 'nDays' from today
     * 
     * @return Calendar constant representing the day of the week
     */
    public static int getDayOfTheWeekFromNow(int nDays) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, nDays);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * A common method for all enums since they can't have another base class
     * 
     * @param <T> Enum type
     * @param c enum type. All enums must be all caps.
     * @param string case insensitive
     * @return corresponding enum, or null
     */
    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if(c != null && string != null) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch(IllegalArgumentException ex) {}
        }
        return null;
    }

    /**
     * Specifically, this utility is to address the fact that System.nanoTime()
     * can sometimes go backwards, due to the fact that it relies on the
     * performance counters
     * 
     * @param startNs
     * @param endNs
     * @return 0 if endNs < startNs, delta otherwise
     */
    public static long elapsedTimeNs(long startNs, long endNs) {
        if(endNs < startNs) {
            return 0L;
        } else {
            return endNs - startNs;
        }
    }

    /**
     * This method breaks the inputList into distinct lists that are no longer
     * than maxContiguous in length. It does so by removing elements from the
     * inputList. This method removes the minimum necessary items to achieve the
     * goal. This method chooses items to remove that minimize the length of the
     * maximum remaining run. E.g. given an inputList of 20 elements and
     * maxContiguous=8, this method will return the 2 elements that break the
     * inputList into 3 runs of 6 items. (As opposed to 2 elements that break
     * the inputList into two runs of eight items and one run of two items.)
     * 
     * @param inputList The list to be broken into separate runs.
     * @param maxContiguous The upper limit on sub-list size
     * @return A list of Integers to be removed from inputList to achieve the
     *         maxContiguous goal.
     */
    public static List<Integer> removeItemsToSplitListEvenly(final List<Integer> inputList,
                                                             int maxContiguous) {
        List<Integer> itemsToRemove = new ArrayList<Integer>();
        int contiguousCount = inputList.size();
        if(contiguousCount > maxContiguous) {
            // Determine how many items must be removed to ensure no contig run
            // longer than maxContiguous
            int numToRemove = contiguousCount / (maxContiguous + 1);
            // Breaking in numToRemove places results in numToRemove+1 runs.
            int numRuns = numToRemove + 1;
            // Num items left to break into numRuns
            int numItemsLeft = contiguousCount - numToRemove;
            // Determine minimum length of each run after items are removed.
            int floorOfEachRun = numItemsLeft / numRuns;
            // Determine how many runs need one extra element to evenly
            // distribute numItemsLeft among all numRuns
            int numOfRunsWithExtra = numItemsLeft - (floorOfEachRun * numRuns);

            int offset = 0;
            for(int i = 0; i < numToRemove; ++i) {
                offset += floorOfEachRun;
                if(i < numOfRunsWithExtra)
                    offset++;
                itemsToRemove.add(inputList.get(offset));
                offset++;
            }
        }
        return itemsToRemove;
    }

    /**
     * This method returns a list that "evenly" (within one) distributes some
     * number of elements (peanut butter) over some number of buckets (bread
     * slices).
     * 
     * @param listLength The number of buckets over which to evenly distribute
     *        the elements.
     * @param numElements The number of elements to distribute.
     * @return A list of size breadSlices, each integer entry of which indicates
     *         the number of elements.
     */
    public static List<Integer> distributeEvenlyIntoList(int listLength, int numElements) {
        if(listLength < 1) {
            throw new IllegalArgumentException("Argument listLength must be greater than 0 : "
                                               + listLength);
        }
        if(numElements < 0) {
            throw new IllegalArgumentException("Argument numElements must be zero or more : "
                                               + numElements);
        }
        int floorElements = numElements / listLength;
        int itemsWithMoreElements = numElements - (listLength * floorElements);

        ArrayList<Integer> evenList = new ArrayList<Integer>(listLength);
        for(int i = 0; i < itemsWithMoreElements; i++) {
            evenList.add(i, floorElements + 1);
        }
        for(int i = itemsWithMoreElements; i < listLength; i++) {
            evenList.add(i, floorElements);
        }
        return evenList;
    }

    /**
     * This method returns a map that "evenly" (within one) distributes some
     * number of elements (peanut butter) over some number of buckets (bread
     * slices).
     * 
     * @param mapKeys The keys of the map over which which to evenly distribute
     *        the elements.
     * @param numElements The number of elements to distribute.
     * @return A Map with keys specified by breadSlices each integer entry of
     *         which indicates the number of elements
     */
    public static Map<Integer, Integer> distributeEvenlyIntoMap(Set<Integer> mapKeys,
                                                                int numElements) {
        Map<Integer, Integer> evenMap = new HashMap<Integer, Integer>();
        List<Integer> evenList = distributeEvenlyIntoList(mapKeys.size(), numElements);
        int offset = 0;
        for(Integer key: mapKeys) {
            evenMap.put(key, evenList.get(offset));
            offset++;
        }
        return evenMap;
    }


}

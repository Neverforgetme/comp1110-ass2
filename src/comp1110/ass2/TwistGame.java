package comp1110.ass2;

import java.util.*;

/**
 * This class provides the text interface for the Twist Game
 * <p>
 * The game is based directly on Smart Games' IQ-Twist game
 * (http://www.smartgames.eu/en/smartgames/iq-twist)
 */
public class TwistGame {

    /**
     * Determine whether a piece or peg placement is well-formed according to the following:
     * - it consists of exactly four characters
     * - the first character is in the range a .. l (pieces and pegs)
     * - the second character is in the range 1 .. 8 (columns)
     * - the third character is in the range A .. D (rows)
     * - the fourth character is in the range 0 .. 7 (if a piece) or is 0 (if a peg)
     *
     * @param piecePlacement A string describing a single piece or peg placement
     * @return True if the placement is well-formed
     */
    public static boolean isPlacementWellFormed(String piecePlacement) {
        // FIXME Task 2: determine whether a piece or peg placement is well-formed
        char[] ch = piecePlacement.toCharArray();
        //  Task 2 from Anzee
        // break the piecePlacement into char []
        if (piecePlacement.length() == 4) {
            if (ch[0] >= 'a' && ch[0] <= 'h' && ch[1] >= '1' && ch[1] <= '8' && ch[2] >= 'A' && ch[2] <= 'D' && ((ch[3] >= '0' && ch[3] <= '7'))) {
                return true;
                // conditions when the first character shows the pieces
            } else if (ch[0] >= 'i' && ch[0] <= 'l' && ch[1] >= '1' && ch[1] <= '8' && ch[2] >= 'A' && ch[2] <= 'D' && ch[3] == '0') {
                return true;
                // conditions when the first character shows the pegs
            } else {
                return false;
            }
        }
        return false;
    }
//  Task 2: use toCharArray() to break the String into char[], check all the characters satisfy the condition
//  check char[0] that pieces from a - h
//  pegs from i - l (two pegs with a same color- blue green yellow- are represented by 1 letter)
//  check char[1] that position is from 1 - 8 in columns
//  check char[2] that position is from A - D in rows
//  check char[3] that rotation of pieces is reasonable (from 1 - 8)
//  rotation of pegs is only 0 (no rotations for peg)
//  if all good, returns true  Anzee

    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N four-character piece placements (where N = 1 .. 15);
     * - each piece or peg placement is well-formed
     * - each piece or peg placement occurs in the correct alphabetical order (duplicate pegs can be in either order)
     * - no piece or red peg appears more than once in the placement
     * - no green, blue or yellow peg appears more than twice in the placement
     *
     * @param placement A string describing a placement of one or more pieces and pegs
     * @return True if the placement is well-formed
     */
    //现在的情况就是我们必须要先完成task4在方便调用
    public static boolean isPlacementStringWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement is well-formed
        //Task 3 from Anzee

        if (placement.length() % 4 == 0 && placement.length() >= 4 && placement.length() <= (4 * 15)) {
            // check the length of placement is reasonable, have to be a multiple of 4, at least larger or equal to 4 and smaller or equal to 60
            boolean exactly_4N = true;
            // boolean if length is reasonable, this is true
            boolean all_wellFormed = true;
            // define all parts are well formed at beginning
            for (int i = 0; i < placement.length(); i = i + 4) {
                all_wellFormed = exactly_4N && isPlacementWellFormed(placement.substring(i, i + 4));
                // check all parts are well formed or not
            }
            if (all_wellFormed) {
                Set<Character> set = new HashSet<>();
                List<Character> list = new ArrayList<>();

                Set<Character> set_j = new HashSet<>();
                List<Character> list_j = new ArrayList<>();

                Set<Character> set_k = new HashSet<>();
                List<Character> list_k = new ArrayList<>();

                Set<Character> set_l = new HashSet<>();
                List<Character> list_l = new ArrayList<>();

                //I used a stupid way, for using set and list, cause list with order but set is not, if there is any repeated value, set will cover the last 1 but list will record all of them

                for (int i = 0; i < placement.length(); i += 4) {
                    if (placement.charAt(i) >= 'a' && placement.charAt(i) <= 'i') {
                        set.add(placement.charAt(i));
                        list.add(placement.charAt(i));
                    } else if (placement.charAt(i) == 'j') {
                        set_j.add(placement.charAt(i));
                        list_j.add(placement.charAt(i));
                    } else if (placement.charAt(i) == 'k') {
                        set_k.add(placement.charAt(i));
                        list_k.add(placement.charAt(i));
                    } else if (placement.charAt(i) == 'l') {
                        set_l.add(placement.charAt(i));
                        list_l.add(placement.charAt(i));
                    }
                }
                // break the placement by pieces and red peg (because they can only appear 1 time, and create set and list for all other pegs without red, all of them 3 can appear no more than twice)

                List<Character> list_ordered = new ArrayList<>();
                List<Character> list_non_ordered = new ArrayList<>();
                for (int i = 0; i < placement.length(); i = i + 4) {
                    list_ordered.add(placement.charAt(i));
                    list_non_ordered.add(placement.charAt(i));
                }
                Collections.sort(list_ordered);
                for (int j = 0; j < list_ordered.size(); j++) {
                    if (list_ordered.get(j) != (list_non_ordered.get(j))) {
                        return false;
                    }
                }
                // check the order of placement, by create two list, and use Collection.sort() to order one of them, if each values of these two list are not same, return false

                return set.size() == list.size() && (set_j.size() == list_j.size() || set_j.size() == list_j.size() / 2 && list_j.size() != 3) && (set_k.size() == list_k.size() || set_k.size() == list_k.size() / 2 && list_k.size() != 3) && (set_l.size() == list_l.size() || set_l.size() == list_l.size() / 2 && list_l.size() != 3);
                // if the list and set for the pieces and red peg have same size, and for each other pegs without red, they can only have two possible choices, 1. they have same size, 2. the list size is double to the size of set
                // and because list.size() gives a int, when size is 3, 3/2 is floor to 1 but which is not true, so list.size() for all pegs without red cannot equal to 3
            } else {
                return false;
            }
        }
        return false;
    }
//Task 3: use if else to find out the length of string //placement.length()// is correct or not, because each piece or peg with
//   4-characters, so the length must be (4 * N), at least 4 and maximum is 60, because there are 8 pieces and 7 pegs
//  (maximum is 15, minimum is 1)
//   "each piece or peg placement occurs in the correct alphabetical order (duplicate pegs can be in either order)"???
//  use isPlacementWellFormed(String piecePlacement) to make sure all pieces and pegs are well-formed
//  might be can use ArrayList and HashSet to check decide all pieces and red peg do not appear more than once, check all
//  other pegs do not appear more than twice   Anzee

    /**
     * Determine whether a placement string is valid.  To be valid, the placement
     * string must be well-formed and each piece placement must be a valid placement
     * according to the rules of the game.
     * - pieces must be entirely on the board
     * - pieces must not overlap each other
     * - pieces may only overlap pegs when the a) peg is of the same color and b) the
     * point of overlap in the piece is a hole.
     *
     * @param placement A placement sequence string
     * @return True if the placement sequence is valid
     */
    public static boolean isPlacementStringValid(String placement) {
        //HashSet<Character> list = new HashSet<>();
        //HashSet<Character> checkList = new HashSet<>();
        //Character [] piecesList = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        //list.addAll(Arrays.asList(piecesList));

        //if (isPlacementStringWellFormed(placement)){
        //int pieceIndex = 0;

        //for (int pieceIndex = 0; pieceIndex < placement.length(); pieceIndex+=4){
        //checkList.add(placement.charAt(pieceIndex));
        //}

        //if (!checkList.contains(list)){
        //return false;
        //}
        //Task 5 code written by Wei Wan (u6642209)
        String p = placement;
        ArrayList<String> op = new ArrayList<>();
        ArrayList<String> r = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        ArrayList<String> g = new ArrayList<>();
        String m;
        char n;

        if (!isPlacementStringWellFormed(p)) {
            return false;
        } else {
            for (int i = 0; i < (p.length() / 4); i++) {
                String str = p.substring(4 * i, 4 * i + 4);
                char s = str.charAt(0);
                char t = str.charAt(3);
                m = str.charAt(1) + "";
                n = str.charAt(2);

                if (s == 'a' && t == '0') {
                    String vp1 = m + n;
                    String sp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String vp2 = (Integer.parseInt(m) + 2) + "" + n;
                    String sp2 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(vp2) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        r.add(vp1);
                        r.add(vp2);
                    }
                } else if (s == 'a' && t == '1') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    String sp2 = m + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(vp2) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        r.add(vp1);
                        r.add(vp2);
                    }
                } else if (s == 'a' && t == '2') {
                    String vp1 = m + (char) ((int) n + 1);
                    String sp1 = m + n;
                    String vp2 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(vp2) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        r.add(vp1);
                        r.add(vp2);
                    }
                } else if (s == 'a' && t == '3') {
                    String vp1 = m + n;
                    String sp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String vp2 = m + (char)((int) n + 2);
                    String sp2 = m + (char)((int) n + 1);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(vp2) || op.contains(sp2)
                        || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        r.add(vp1);
                        r.add(vp2);
                    }
                } else if (s == 'a' && t == '4') {
                    String vp1 = m + (char) ((int) n + 1);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String vp2 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    String sp2 = (Integer.parseInt(m) + 2) + "" + n;
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(vp2) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        r.add(vp1);
                        r.add(vp2);
                    }
                } else if (s == 'a' && t == '5') {
                    String vp1 = m + n;
                    String sp1 = m + (char) ((int) n + 1);
                    String vp2 = m + (char) ((int) n + 2);
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(vp2) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        r.add(vp1);
                        r.add(vp2);
                    }
                } else if (s == 'a' && t == '6') {
                    String vp1 = m + n;
                    String sp1 = m + (char) ((int) n + 1);
                    String vp2 = (Integer.parseInt(m) + 2) + "" + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + n;
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(vp2) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        r.add(vp1);
                        r.add(vp2);
                    }
                } else if (s == 'a' && t == '7') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp1 = m + n;
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B') {
                        return false;
                    } else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        r.add(vp1);
                        r.add(vp2);
                    }
                } else if (s == 'b' && t == '0') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = m + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp3 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        r.add(vp1);
                    }
                } else if (s == 'b' && t == '1') {
                    String vp1 = m + (char) ((int) n + 1);
                    String sp1 = m + (char) ((int) n + 2);
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp3 = (Integer.parseInt(m) + 1) + "" + n;
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        r.add(vp1);
                    }
                } else if (s == 'b' && t == '2') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp1 = m + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp3 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        r.add(vp1);
                    }
                } else if (s == 'b' && t == '3') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp2 = m + (char) ((int) n + 1);
                    String sp3 = m + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        r.add(vp1);
                    }
                } else if (s == 'b' && t == '4') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp1 = (Integer.parseInt(m) + 2) + "" + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp3 = m + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        r.add(vp1);
                    }
                } else if (s == 'b' && t == '5') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = m + n;
                    String sp2 = m + (char) ((int) n + 1);
                    String sp3 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        r.add(vp1);
                    }
                } else if (s == 'b' && t == '6') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp2 = m + (char) ((int) n + 1);
                    String sp3 = (Integer.parseInt(m) + 2) + "" + n;
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        r.add(vp1);
                    }
                } else if (s == 'b' && t == '7') {
                    String vp1 = m + (char) ((int) n + 1);
                    String sp1 = m + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp3 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        r.add(vp1);
                    }
                } else if (s == 'c' && (t == '0' || t == '4')) {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp1 = m + n;
                    String sp2 = (Integer.parseInt(m) + 2) + "" + n;
                    String sp3 = (Integer.parseInt(m) + 3) + "" + n;
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 5 || n < 'A' || n > 'D')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        b.add(vp1);
                    }
                } else if (s == 'c' && (t == '1' || t == '5')) {
                    String vp1 = m + (char) ((int) n + 1);
                    String sp1 = m + n;
                    String sp2 = m + (char) ((int) n + 2);
                    String sp3 = m + (char) ((int) n + 3);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 8 || n < 'A' || n > 'A')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        b.add(vp1);
                    }
                } else if (s == 'c' && (t == '2' || t == '6')) {
                    String vp1 = (Integer.parseInt(m) + 2) + "" + n;
                    String sp1 = m + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp3 = (Integer.parseInt(m) + 3) + "" + n;
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 5 || n < 'A' || n > 'D')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        b.add(vp1);
                    }
                } else if (s == 'c' && (t == '3' || t == '7')) {
                    String vp1 = m + (char) ((int) n + 2);
                    String sp1 = m + n;
                    String sp2 = m + (char) ((int) n + 1);
                    String sp3 = m + (char) ((int) n + 3);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 8 || n < 'A' || n > 'A')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        b.add(vp1);
                    }
                } else if (s == 'd' && t == '0') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String vp2 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    String sp1 = m + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp3 = (Integer.parseInt(m) + 2) + "" + n;
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        b.add(vp1);
                        b.add(vp2);
                    }
                } else if (s == 'd' && t == '1') {
                    String vp1 = m + (char) ((int) n + 1);
                    String vp2 = m + (char) ((int) n + 2);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp3 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        b.add(vp1);
                        b.add(vp2);
                    }
                } else if (s == 'd' && t == '2') {
                    String vp1 = m + n;
                    String vp2 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp1 = m + (char) ((int) n + 1);
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp3 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        b.add(vp1);
                        b.add(vp2);
                    }
                } else if (s == 'd' && t == '3') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = m + n;
                    String sp2 = m + (char) ((int) n + 1);
                    String sp3 = m + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        b.add(vp1);
                        b.add(vp2);
                    }
                } else if (s == 'd' && t == '4') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String vp2 = (Integer.parseInt(m) + 2) + "" + n;
                    String sp1 = m + (char) ((int) n + 1);
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp3 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        b.add(vp1);
                        b.add(vp2);
                    }
                } else if (s == 'd' && t == '5') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    String sp1 = m + n;
                    String sp2 = m + (char) ((int) n + 1);
                    String sp3 = m + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        b.add(vp1);
                        b.add(vp2);
                    }
                } else if (s == 'd' && t == '6') {
                    String vp1 = m + (char) ((int) n + 1);
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = m + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp3 = (Integer.parseInt(m) + 2) + "" + n;
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        b.add(vp1);
                        b.add(vp2);
                    }
                } else if (s == 'd' && t == '7') {
                    String vp1 = m + n;
                    String vp2 = m + (char) ((int) n + 1);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp3 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2) || op.contains(sp3)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        op.add(sp3);
                        b.add(vp1);
                        b.add(vp2);
                    }
                } else if (s == 'e' && t == '0') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = m + n;
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'e' && t == '1') {
                    String vp1 = m + (char) ((int) n + 1);
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + n;
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'e' && t == '2') {
                    String vp1 = m + n;
                    String vp2 = m + (char) ((int) n + 1);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'e' && t == '3') {
                    String vp1 = m + n;
                    String vp2 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp1 = m + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'e' && t == '4') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = m + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'e' && t == '5') {
                    String vp1 = m + (char) ((int) n + 1);
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = m + n;
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'e' && t == '6') {
                    String vp1 = m + n;
                    String vp2 = m + (char) ((int) n + 1);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + n;
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'e' && t == '7') {
                    String vp1 = m + n;
                    String vp2 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'f' && t == '0') {
                    String vp1 = (Integer.parseInt(m) + 2) + "" + n;
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = m + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + n;
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'f' && t == '1') {
                    String vp1 = m + (char) ((int) n + 1);
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'f' && t == '2') {
                    String vp1 = m + (char) ((int) n + 1);
                    String vp2 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp2 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'f' && t == '3') {
                    String vp1 = m + n;
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = m + (char) ((int) n + 1);
                    String sp2 = m + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'f' && t == '4') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String vp2 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    String sp1 = m + (char) ((int) n + 1);
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'f' && t == '5') {
                    String vp1 = m + (char) ((int) n + 2);
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = m + n;
                    String sp2 = m + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'f' && t == '6') {
                    String vp1 = m + n;
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp2 = (Integer.parseInt(m) + 2) + "" + n;
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'C')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'f' && t == '7') {
                    String vp1 = m + (char) ((int) n + 1);
                    String vp2 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 7 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(sp1);
                        op.add(sp2);
                        g.add(vp1);
                        g.add(vp2);
                    }
                } else if (s == 'g' && t == '0') {
                    String vp1 = m + n;
                    String vp2 = m + (char) ((int) n + 1);
                    String vp3 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp2 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(vp3) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(vp3);
                        op.add(sp1);
                        op.add(sp2);
                        y.add(vp1);
                        y.add(vp2);
                        y.add(vp3);
                    }
                } else if (s == 'g' && t == '1') {
                    String vp1 = m + (char) ((int) n + 1);
                    String vp2 = (Integer.parseInt(m) + 1) + "" + n;
                    String vp3 = (Integer.parseInt(m) + 2) + "" + n;
                    String sp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(vp3) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(vp3);
                        op.add(sp1);
                        op.add(sp2);
                        y.add(vp1);
                        y.add(vp2);
                        y.add(vp3);
                    }
                } else if (s == 'g' && t == '2') {
                    String vp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String vp2 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    String vp3 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 2);
                    String sp1 = m + (char) ((int) n + 1);
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(vp3) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(vp3);
                        op.add(sp1);
                        op.add(sp2);
                        y.add(vp1);
                        y.add(vp2);
                        y.add(vp3);
                    }
                } else if (s == 'g' && t == '3') {
                    String vp1 = m + (char) ((int) n + 2);
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    String vp3 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(vp3) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(vp3);
                        op.add(sp1);
                        op.add(sp2);
                        y.add(vp1);
                        y.add(vp2);
                        y.add(vp3);
                    }
                } else if (s == 'g' && t == '4') {
                    String vp1 = m + (char) ((int) n + 1);
                    String vp2 = m + (char) ((int) n + 2);
                    String vp3 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp2 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(vp3) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(vp3);
                        op.add(sp1);
                        op.add(sp2);
                        y.add(vp1);
                        y.add(vp2);
                        y.add(vp3);
                    }
                } else if (s == 'g' && t == '5') {
                    String vp1 = m + n;
                    String vp2 = (Integer.parseInt(m) + 1) + "" + n;
                    String vp3 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(vp3) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(vp3);
                        op.add(sp1);
                        op.add(sp2);
                        y.add(vp1);
                        y.add(vp2);
                        y.add(vp3);
                    }
                } else if (s == 'g' && t == '6') {
                    String vp1 = (Integer.parseInt(m) + 2) + "" + n;
                    String vp2 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 1);
                    String vp3 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    String sp1 = m + (char) ((int) n + 1);
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(vp3) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(vp3);
                        op.add(sp1);
                        op.add(sp2);
                        y.add(vp1);
                        y.add(vp2);
                        y.add(vp3);
                    }
                } else if (s == 'g' && t == '7') {
                    String vp1 = m + (char) ((int) n + 1);
                    String vp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 2);
                    String vp3 = (Integer.parseInt(m) + 2) + "" + (char) ((int) n + 2);
                    String sp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(vp2) || op.contains(vp3) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(vp2);
                        op.add(vp3);
                        op.add(sp1);
                        op.add(sp2);
                        y.add(vp1);
                        y.add(vp2);
                        y.add(vp3);
                    }
                } else if (s == 'h' && (t == '0' || t == '4')) {
                    String vp1 = m + n;
                    String sp1 = (Integer.parseInt(m) + 1) + "" + n;
                    String sp2 = (Integer.parseInt(m) + 2) + "" + n;
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'D')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        y.add(vp1);
                    }
                } else if (s == 'h' && (t == '1' || t == '5')) {
                    String vp1 = m + n;
                    String sp1 = m + (char) ((int) n + 1);
                    String sp2 = m + (char) ((int) n + 2);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 8 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        y.add(vp1);
                    }
                } else if (s == 'h' && (t == '2' || t == '6')) {
                    String vp1 = (Integer.parseInt(m) + 2) + "" + n;
                    String sp1 = m + n;
                    String sp2 = (Integer.parseInt(m) + 1) + "" + n;
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 6 || n < 'A' || n > 'D')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        y.add(vp1);
                    }
                } else if (s == 'h' && (t == '3' || t == '7')) {
                    String vp1 = m + (char) ((int) n + 2);
                    String sp1 = m + n;
                    String sp2 = m + (char) ((int) n + 1);
                    if (op.contains(vp1) || op.contains(sp1) || op.contains(sp2)
                            || Integer.parseInt(m) < 1 || Integer.parseInt(m) > 8 || n < 'A' || n > 'B')
                        return false;
                    else {
                        op.add(vp1);
                        op.add(sp1);
                        op.add(sp2);
                        y.add(vp1);
                    }
                }

                if (s == 'i' && ((!r.contains(m + n)) && op.contains(m + n)))
                    return false;
                if (s == 'j' && ((!b.contains(m + n)) && op.contains(m + n)))
                    return false;
                if (s == 'k' && ((!g.contains(m + n)) && op.contains(m + n)))
                    return false;
                if (s == 'l' && ((!y.contains(m + n)) && op.contains(m + n)))
                    return false;
            }
            return true;
        }
        //Task 5 code written by Wei Wan (u6642209)
        // FIXME Task 5: determine whether a placement string is valid
    }
//Task 5: use for loop and if else to check each piece with its rotation is not out of board
//  maybe create a string set [] and make it match with the board position, then filling the placement. if there is any
//  repeat on 1 position, the last value will cover the value before, then check the length of String, if length < 32,
//  return false.
//  recording all the solid points of pieces on the board, check the pegs location, if any two are same, return false.
//  make sure all pegs are located at the area of pieces with a same color.
//  使用 for 循环，如果 else 来检查每个部件的旋转是否不在板外，可以创建一个字符串集 [] 并使其与板位置匹配，然后填充位置。
//  如果第 1 个位置有任何重复，最后一个值将覆盖之前的值，
//  然后检查 String 的长度，如果长度 < 32，则返回 false。记录棋盘上所有棋子的实心点，检查钉子位置，如果有两个相同，
//  则返回false。确保所有钉都位于具有相同颜色的零件区域。Anzee

    /**
     * Given a string describing a placement of pieces and pegs, return a set
     * of all possible next viable piece placements.   To be viable, a piece
     * placement must be a valid placement of a single piece.  The piece must
     * not have already been placed (ie not already in the placement string),
     * and its placement must be valid.   If there are no valid piece placements
     * for the given placement string, return null.
     * <p>
     * When symmetric placements of the same piece are viable, only the placement
     * with the lowest rotation should be included in the set.

     *
     * @param placement A valid placement string (comprised of peg and piece placements)
     * @return An set of viable piece placements, or null if there are none.
     */
    public static Set<String> getViablePiecePlacements(String placement) {
        // FIXME Task 6: determine the set of valid next piece placements
        //Task 6 code written by Wei Wan(u6642209)
        String p = placement;
        String p_temp = p;
        String[] array = {"a0", "a1", "a2", "a3", "a4", "a5", "a7","a6",
                          "b0", "b1", "b4", "b5", "b3", "b7", "b2", "b6",
                          "c0", "c1", "c2", "c3",
                          "d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7",
                          "e0", "e1", "e2", "e3", "e4", "e5", "e6","e7",
                          "f0", "f1", "f2", "f3", "f7", "f6", "f4","f5",
                          "g0", "g1", "g2", "g3", "g4", "g5", "g6", "g7",
                          "h0", "h1", "h2", "h3"};
        ArrayList<String> full_set = new ArrayList<String>(Arrays.asList(array));
        ArrayList<String> possible_set = new ArrayList<>();
        Set<String> viable_set = new HashSet<>();
        String[] pos = {"1A", "1B", "1C", "1D", "2A", "2B", "2C", "2D",
                "3A", "3B", "3C", "3D", "4A", "4B", "4C", "4D",
                "5A", "5B", "5C", "5D", "6A", "6B", "6C", "6D",
                "7A", "7B", "7C", "7D", "8A", "8B", "8C", "8D"};
        ArrayList<String> points = new ArrayList<>(Arrays.asList(pos));
        String m;
        char n;
        for (int i = 0; i < (p.length() / 4); i++) {
            String str = p.substring(4 * i, 4 * i + 4);
            char s = str.charAt(0);
            char t = str.charAt(3);
            m = str.charAt(1) + "";
            n = str.charAt(2);
            String point = m + n;
            if (s == 'a' || s == 'b' || s == 'c' || s == 'd' || s == 'e' || s == 'f' || s == 'g' || s == 'h') {
                for (int j = 0; j < 8; j++) {
                    String piece = s + "" + j;
                    full_set.remove(piece);
                }
            }
        }
        possible_set = full_set;

        for (String piece : possible_set) {
            char s = piece.charAt(0);
            char t = piece.charAt(1);
            String place;
            String p1 = "";
            String p2 = "";
            for (int j = 0; j < p.length() / 4; j++) {
                char check = p.charAt(4 * j);
                if (check > s) {
                    p1 = p.substring(0, 4 * j) + s;
                    p2 = t + "" + p.substring(4 * j, p.length());
                    break;
                }
            }

            for (String point : points) {
                p_temp = p1 + point + p2;
                place = s + point + t;

                if (s == 'b' && t == '6' && viable_set.contains("b"+point+"4")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '4');
                }
                else if (s == 'b' && t == '2' && viable_set.contains("b"+point+"0")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '0');
                }
                else if (s == 'b' && t == '7' && viable_set.contains("b"+point+"5")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '5');
                }
                else if (s == 'b' && t == '3' && viable_set.contains("b"+point+"1")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '1');
                }
                else if (s == 'e' && t == '7' && viable_set.contains("e"+point+"0")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '0');
                }
                else if (s == 'e' && t == '6' && viable_set.contains("e"+point+"3")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '3');
                }
                else if (s == 'e' && t == '5' && viable_set.contains("e"+point+"2")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '2');
                }
                else if (s == 'e' && t == '4' && viable_set.contains("e"+point+"1")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '1');
                }
                else if (s == 'f' && t == '6' && viable_set.contains("f"+point+"0")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '0');
                }
                else if (s == 'f' && t == '4' && viable_set.contains("f"+point+"2")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '2');
                }
                else if (s == 'f' && t == '5' && viable_set.contains("f"+point+"3")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '3');
                }
                else if (s == 'h' && t == '2' && viable_set.contains("h"+point+"0")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '0');
                }
                else if (s == 'c' && t == '2' && viable_set.contains("c"+point+"0")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '0');
                }

                else if (s == 'h' && t == '2' && viable_set.contains("h"+point+"0")&&isPlacementStringValid(p_temp)) {
                    viable_set.add(s + point + '0');
                }
                else if (isPlacementStringValid(p_temp)) {
                    viable_set.add(place);
                }
            }
            p_temp = p;
        }
        if (viable_set.isEmpty())
            return null;
        else
            return viable_set;

        //Task 6 code written by Wei Wan(u6642209)
    }


        // The string of placement is used as the base condition of the game.
// The method will cycle multiple piece placements and will return a
// set that is both valid and is not already placed by the initial piece
// placements. To accomplish that, the method will internally generate
// a piece at each cycle to which the method determines whether the
// new piece will be valid. The method then adds the piece to the set.
// This process repeats until there is no more possible pieces left.
//put string objective into the arrayList, which is contain the string except placement
//Select one of the piecePlacement in the objective and use a for loop to add piecePlacement into an ArrayList
//then use a for loop to get next piece (still thinking, maybe using the exhaustion method for these remaining pieces)   Anzee
        /**
         * Return an array of all unique solutions for a given starting placement.
         *
         * Each solution should be a 32-character string giving the placement sequence
         * of all eight pieces, given the starting placement.
         *
         * The set of solutions should not include any symmetric piece placements.
         *
         * In the IQ-Twist game, valid challenges can have only one solution, but
         * other starting placements that are not valid challenges may have more
         * than one solution.  The most obvious example is the unconstrained board,
         * which has very many solutions.
         *
         * @param placement A valid piece placement string.
         * @return An array of strings, each 32-characters long, describing a unique
         * unordered solution to the game given the starting point provided by placement.
         */
        public static String[] getSolutions (String placement){
            // FIXME Task 9: determine all solutions to the game, given a particular starting placement
            //Task code written by Wei Wan (u6642209)
                String p = placement;
                ArrayList<String> solution = new ArrayList<>();
                String p_temp = p;
                String[] array = {"a0", "a1", "a2", "a3", "a4", "a5", "a7","a6",
                        "b0", "b1", "b4", "b5", "b3", "b7", "b2", "b6",
                        "c0", "c1", "c2", "c3",
                        "d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7",
                        "e0", "e1", "e2", "e3", "e4", "e5", "e6","e7",
                        "f0", "f1", "f2", "f3", "f7", "f6", "f4","f5",
                        "g0", "g1", "g2", "g3", "g4", "g5", "g6", "g7",
                        "h0", "h1", "h2", "h3"};
                ArrayList<String> full_set = new ArrayList<String>(Arrays.asList(array));
                ArrayList<String> possible_set = new ArrayList<>();
                Set<String> viable_set = new HashSet<>();
                String[] pos = {"1A", "1B", "1C", "1D", "2A", "2B", "2C", "2D",
                        "3A", "3B", "3C", "3D", "4A", "4B", "4C", "4D",
                        "5A", "5B", "5C", "5D", "6A", "6B", "6C", "6D",
                        "7A", "7B", "7C", "7D", "8A", "8B", "8C", "8D"};
                ArrayList<String> points = new ArrayList<>(Arrays.asList(pos));
                String m;
                char n;
                for (int i = 0; i < (p.length() / 4); i++) {
                    String str = p.substring(4 * i, 4 * i + 4);
                    char s = str.charAt(0);
                    char t = str.charAt(3);
                    m = str.charAt(1) + "";
                    n = str.charAt(2);
                    String point = m + n;
                    if (s == 'a' || s == 'b' || s == 'c' || s == 'd' || s == 'e' || s == 'f' || s == 'g' || s == 'h') {
                        for (int j = 0; j < 8; j++) {
                            String piece = s + "" + j;
                            full_set.remove(piece);
                        }
                    }
                }
                possible_set = full_set;

                for (String piece : possible_set) {
                    char s = piece.charAt(0);
                    char t = piece.charAt(1);
                    String place;
                    String p1 = "";
                    String p2 = "";
                    for (int j = 0; j < p.length() / 4; j++) {
                        char check = p.charAt(4 * j);
                        if (check > s) {
                            p1 = p.substring(0, 4 * j) + s;
                            p2 = t + "" + p.substring(4 * j, p.length());
                            break;
                        }
                    }

                    for (String point : points) {
                        p_temp = p1 + point + p2;
                        place = s + point + t;

                        if (s == 'b' && t == '2' && viable_set.contains("b"+point+"0")&&isPlacementStringValid(p_temp)) {
                            viable_set.add(s + point + '0');
                        }
                        else if (s == 'b' && t == '7' && viable_set.contains("b"+point+"5")&&isPlacementStringValid(p_temp)) {
                            viable_set.add(s + point + '5');
                        }
                        else if (s == 'b' && t == '3' && viable_set.contains("b"+point+"1")&&isPlacementStringValid(p_temp)) {
                            viable_set.add(s + point + '1');
                        }
                        else if (s == 'e' && t == '7' && viable_set.contains("e"+point+"0")&&isPlacementStringValid(p_temp)) {
                            viable_set.add(s + point + '0');
                        }
                        else if (s == 'e' && t == '6' && viable_set.contains("e"+point+"3")&&isPlacementStringValid(p_temp)) {
                            viable_set.add(s + point + '3');
                        }
                        else if (s == 'e' && t == '5' && viable_set.contains("e"+point+"2")&&isPlacementStringValid(p_temp)) {
                            viable_set.add(s + point + '2');
                        }
                        else if (s == 'e' && t == '4' && viable_set.contains("e"+point+"1")&&isPlacementStringValid(p_temp)) {
                            viable_set.add(s + point + '1');
                        }
                        else if (s == 'f' && t == '6' && viable_set.contains("f"+point+"0")&&isPlacementStringValid(p_temp)) {
                            viable_set.add(s + point + '0');
                        }
                        else if (s == 'f' && t == '4' && viable_set.contains("f"+point+"2")&&isPlacementStringValid(p_temp)) {
                            viable_set.add(s + point + '2');
                        }
                        else if (s == 'f' && t == '5' && viable_set.contains("f"+point+"3")&&isPlacementStringValid(p_temp)) {
                            viable_set.add(s + point + '3');
                        }
                        else if (s == 'h' && t == '2' && viable_set.contains("h"+point+"0")&&isPlacementStringValid(p_temp)) {
                            viable_set.add(s + point + '0');
                        }
                        else if (s == 'c' && t == '2' && viable_set.contains("c"+point+"0")&&isPlacementStringValid(p_temp)) {
                            viable_set.add(s + point + '0');
                        }

                        else if (s == 'h' && t == '2' && viable_set.contains("h"+point+"0")&&isPlacementStringValid(p_temp)) {
                            viable_set.add(s + point + '0');
                        }
                        else if (isPlacementStringValid(p_temp)) {
                            viable_set.add(place);
                        }
                    }
                    p_temp = p;
                }

                ArrayList<String> a = new ArrayList<>();
                ArrayList<String> b = new ArrayList<>();
                ArrayList<String> c = new ArrayList<>();
                ArrayList<String> d = new ArrayList<>();
                ArrayList<String> e = new ArrayList<>();
                ArrayList<String> f = new ArrayList<>();
                ArrayList<String> g = new ArrayList<>();
                ArrayList<String> h = new ArrayList<>();
                String pegs = "";
                for(String piece: viable_set){
                    if(piece.charAt(0)=='a'){
                        a.add(piece);
                    }
                    else if(piece.charAt(0)=='b'){
                        if (piece.substring(1,4).equals("2C6")){
                            b.add("b2C4");
                        }
                        else if(piece.substring(1,4).equals("5A6")){
                            b.add("b5A4");
                        }
                        else
                            b.add(piece);
                    }
                    else if(piece.charAt(0)=='c'){
                        c.add(piece);
                    }
                    else if(piece.charAt(0)=='d'){
                        d.add(piece);
                    }
                    else if(piece.charAt(0)=='e'){
                        e.add(piece);
                    }
                    else if(piece.charAt(0)=='f'){
                        f.add(piece);
                    }
                    else if(piece.charAt(0)=='g'){
                        g.add(piece);
                    }
                    else if(piece.charAt(0)=='h'){
                        h.add(piece);
                    }
                }

                for (int j=0; 4*j<p.length();j++){
                    if(p.charAt(4*j)=='a'){
                        a.add(p.substring(4*j,4*j+4));
                    }
                    else if(p.charAt(4*j)=='b'){
                        b.add(p.substring(4*j,4*j+4));
                    }
                    else if(p.charAt(4*j)=='c'){
                        if(p.substring(4*j+1,4*j+4).equals("2D2")){
                            c.add("c2D0");
                        }
                        else
                            c.add(p.substring(4*j,4*j+4));
                    }
                    else if(p.charAt(4*j)=='d'){
                        d.add(p.substring(4*j,4*j+4));
                    }
                    else if(p.charAt(4*j)=='e'){
                        if (p.substring(4*j+1,4*j+4).equals("1A6")){
                            e.add("e1A3");
                        }
                        else {
                            e.add(p.substring(4*j,4*j+4));
                        }
                    }
                    else if(p.charAt(4*j)=='f'){
                        if (p.substring(4*j+1,4*j+4).equals("6A6")){
                            f.add("f6A0");
                        }
                        else if (p.substring(4*j+1,4*j+4).equals("4A6")){
                            f.add("f4A0");
                        }
                        else if (p.charAt(4*j+3)=='5'){
                            f.add(p.substring(4*j,4*j+3)+'3');
                        }
                        else
                            f.add(p.substring(4*j,4*j+4));
                    }
                    else if(p.charAt(4*j)=='g'){
                        g.add(p.substring(4*j,4*j+4));
                    }
                    else if(p.charAt(4*j)=='h'){
                        if(p.substring(4*j+1,4*j+4).equals("4B2"))
                            h.add("h4B0");
                        else
                            h.add(p.substring(4*j,4*j+4));
                    }
                    else {
                        pegs=p.substring(4*j);
                        break;
                    }
                }
                String test_placement="";
                String test_placement1="";
                String test_placement2="";
                String test_placement3="";
                String test_placement4="";
                String test_placement5="";
                String test_placement6="";
                String test_placement7="";
                for(String piece_a:a){
                    test_placement1=piece_a;
                    for(String piece_b:b){
                        test_placement2=test_placement1+piece_b;
                        for(String piece_c:c){
                            test_placement3=test_placement2+piece_c;
                            for(String piece_d:d){
                                test_placement4=test_placement3+piece_d;
                                for(String piece_e:e){
                                    test_placement5=test_placement4+piece_e;
                                    for(String piece_f:f){
                                        test_placement6=test_placement5+piece_f;
                                        for(String piece_g:g){
                                            test_placement7=test_placement6+piece_g;
                                            for(String piece_h:h){
                                                String test_placement8=test_placement7+piece_h;
                                                if(isPlacementStringValid(test_placement8+pegs)){
                                                solution.add(test_placement8);
                                                }
                                                test_placement8=test_placement7;
                                            }
                                            test_placement7=test_placement6;
                                        }
                                        test_placement6=test_placement5;
                                    }
                                    test_placement5=test_placement4;
                                }
                                test_placement4=test_placement3;
                            }
                            test_placement3=test_placement2;
                        }
                        test_placement2=test_placement1;
                    }
                    test_placement1="";
                }
        String[] solutions = new String[solution.size()];
        for(int i=0;i<solution.size();i++){
            solutions[i]=solution.get(i);
        }
        return solutions;
    }
    //Task code written by Wei Wan (u6642209)
}
        //by using getViablePiecePlacements(String placement) in Task 6, and use HashMap and ArrayList to get all the possible
        //pieces solution, then return a set of String   Anzee

        /**
         * Return an array of all unique solutions for a given starting placement.
         *
         * Each solution should be a 32-character string giving the placement sequence
         * of all eight pieces, given the starting placement.
         *
         * The set of solutions should not include any symmetric piece placements.
         *
         * In the IQ-Twist game, valid challenges can have only one solution, but
         * other starting placements that are not valid challenges may have more
         * than one solution.  The most obvious example is the unconstrained board,
         * which has very many solutions.
         *
         * @param placement A valid piece placement string.
         * @return An array of strings, each 32-characters long, describing a unique
         * unordered solution to the game given the starting point provided by placement.
         */


        //by using getViablePiecePlacements(String placement) in Task 6, and use HashMap and ArrayList to get all the possible
        //pieces solution, then return a set of String   Anzee


package es.ucm.stenography;

import es.ucm.stenography.model.Coordinate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.TreeSet;

public class GeneratePositions implements IGeneratePositions {
    @Override
    public TreeSet<Coordinate> Get(String hashStr, int size, int positions) {
        int sizeInPowerOf2 = PreviousPowerOf2(size) - 1;
        BitSet sizeInBits = convert(sizeInPowerOf2);
        return GetAllPositions(hashStr, GetSignificantBits(sizeInBits), positions);
    }

    private BitSet convert(long value) {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0L) {
            if (value % 2L != 0) {
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bits;
    }

    private BitSet hexStringToBitSet(String hexString) {
        return BitSet.valueOf(hexStringToByteArray(hexString.toLowerCase()));
    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i + 1 < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private static int GetSignificantBits(BitSet sizeInBits) {
        return sizeInBits.length();
    }

    private int PreviousPowerOf2(int x) {
        x = x | (x >> 1);
        x = x | (x >> 2);
        x = x | (x >> 4);
        x = x | (x >> 8);
        x = x | (x >> 16);
        return x - (x >> 1);
    }

    private String encryptSHA512(String target) {
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-512");
            sh.update(target.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : sh.digest())
                sb.append(Integer.toHexString(0xff & b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private TreeSet<Coordinate> GetAllPositions(String hashStr, int significantBits, int maxPositions) {
        TreeSet<Coordinate> positions = new TreeSet<>();

        String hashByteFaltantes = hashStr;
        do {
            positions.addAll(GetPositions(hashByteFaltantes, significantBits, maxPositions - positions.size()));
            hashByteFaltantes = encryptSHA512(hashByteFaltantes);
        } while (positions.size() < maxPositions);
        return positions;
    }

    private TreeSet<Coordinate> GetPositions(String hashStr, int significantBits, int maxPositions) {
        BitSet hashByte = hexStringToBitSet(hashStr);

        TreeSet<Coordinate> positions = new TreeSet<>();

        int x = 0, y = 0, number=0;
        int hashByteLength = hashByte.length();
        for (int i = 0; i < hashByteLength && positions.size() < maxPositions; i++) {
            if (i % significantBits == 0 && i != 0) {
                if(x!= 0){
                    y = number;
                }else{
                    x = number;
                }
                number = 0;
            }
            if(x!= 0 && y!=0){
                positions.add(new Coordinate(x,y));
                x = 0;
                y = 0;
            }

            number = number << 1 | (hashByte.get(i) ? 1 : 0);
        }

        return positions;
    }
}

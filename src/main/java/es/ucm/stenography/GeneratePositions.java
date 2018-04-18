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
        BitSet sizeInBits = Convert(sizeInPowerOf2);
        int significantBits = GetSignificantBits(sizeInBits);

        return GetAllPositions(hashStr, significantBits, positions);
    }

    private BitSet Convert(long value) {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0L) {
            if (value % 2L != 0) {
                bits.set(index);
            }
            ++index;
            value = value >>> Constants.OFFSET_1;
        }
        return bits;
    }

    private BitSet HexStringToBitSet(String hexString) {
        return BitSet.valueOf(HexStringToByteArray(hexString.toLowerCase()));
    }

    private byte[] HexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i + 1 < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), Constants.OFFSET_16) << Constants.OFFSET_4) + Character.digit(s.charAt(i + Constants.OFFSET_1), Constants.OFFSET_16));
        }
        return data;
    }

    private static int GetSignificantBits(BitSet sizeInBits) {
        return sizeInBits.length();
    }

    private int PreviousPowerOf2(int x) {
        x = x | (x >> Constants.OFFSET_1);
        x = x | (x >> Constants.OFFSET_2);
        x = x | (x >> Constants.OFFSET_4);
        x = x | (x >> Constants.OFFSET_8);
        x = x | (x >> Constants.OFFSET_16);
        return x - (x >> Constants.OFFSET_1);
    }

    private String EncryptSHA512(String target) {
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-512");
            sh.update(target.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : sh.digest())
                sb.append(Integer.toHexString(0xff & b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private TreeSet<Coordinate> GetAllPositions(String hashStr, int significantBits, int maxPositions) {
        TreeSet<Coordinate> positions = new TreeSet<>();
        String hashByteMissing = hashStr;
        do {
            positions.addAll(GetPositions(hashByteMissing, significantBits, maxPositions - positions.size()));
            hashByteMissing = EncryptSHA512(hashByteMissing);
        } while (positions.size() < maxPositions);
        return positions;
    }

    private TreeSet<Coordinate> GetPositions(String hashStr, int significantBits, int maxPositions) {
        BitSet hashByte = HexStringToBitSet(hashStr);

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
                Coordinate coordinate = new Coordinate<>(x,y);
                positions.add(coordinate);
                x = 0;
                y = 0;
            }

            number = number << Constants.OFFSET_1 | (hashByte.get(i) ? 1 : 0);
        }

        return positions;
    }
}

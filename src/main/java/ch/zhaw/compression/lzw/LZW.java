package ch.zhaw.compression.lzw;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ch.zhaw.compression.util.BitStream;

public class LZW {
	private Map<String, Integer> wbEncoding;
	private Map<Integer, String> wbDecoding;

	public LZW() {

	}

	public List<Integer> encodeData(String data) {
		wbEncoding = new HashMap<String, Integer>();

		StringBuffer sb = new StringBuffer(data);

		List<Integer> encoded = new LinkedList<Integer>();

		int nextIndex = 256;

		while (sb.length() > 0) {
			int i = 1;

			// char not available
			if (wbEncoding.get(sb.substring(0, i)) == null) {
				// ascii char?
				if (sb.substring(0, i).toCharArray()[0] < 256) {
					wbEncoding.put(sb.substring(0, i), (int) sb.substring(0, i)
							.toCharArray()[0]);
				}
				encoded.add(wbEncoding.get(sb.substring(0, i)));

				if (sb.length() > i) {
					wbEncoding.put(sb.substring(0, i + 1), nextIndex);
					nextIndex++;
				}

				sb.delete(0, i);
			} else {
				if (sb.length() < 5) {
					// System.out.println(sb);
				}
				while (wbEncoding.get(sb.substring(0, i)) != null) {
					i++;
					if (i >= sb.length()) {
						break;
					}
				}
				if (wbEncoding.get(sb.substring(0, i)) != null) {
					encoded.add(wbEncoding.get(sb.substring(0, i)));
					// remove scanned
					sb.delete(0, i);
				} else {
					encoded.add(wbEncoding.get(sb.substring(0, i - 1)));

					// add new dictionary entry
					wbEncoding.put(sb.substring(0, i), nextIndex);
					nextIndex++;

					// remove scanned
					sb.delete(0, i - 1);
				}

			}
		}

		for (String s : wbEncoding.keySet()) {
			System.out.println(s + ":" + wbEncoding.get(s));
		}

		System.out.println("lzw encoding finished");
		System.out.println("highest index: " + nextIndex);
		return encoded;
	}

	public String decodeData(List<Integer> data) {
		wbDecoding = new HashMap<Integer, String>();
		wbEncoding = new HashMap<String, Integer>();

		StringBuffer rv = new StringBuffer();

		List<StringBuffer> cache = new LinkedList<StringBuffer>();

		int nextIndex = 256;
		
		for (int i : data) {
			if (i < 256) {
				wbEncoding.put(Character.toString((char) i), i);
				wbDecoding.put(i, Character.toString((char) i));
			}

			rv.append(wbDecoding.get(i));

			// process cache entries
			List<StringBuffer> rm = new LinkedList<StringBuffer>();
			for (StringBuffer sbc : cache) {
				if(wbDecoding.get(i) == null){
					System.out.println(i + " is null");
				}
				sbc.append(wbDecoding.get(i).charAt(0));

				if (wbEncoding.get(sbc.toString()) == null) {
					wbEncoding.put(sbc.toString(), nextIndex);
					wbDecoding.put(nextIndex, sbc.toString());

					rm.add(sbc);
					nextIndex++;
				}
			}

			for (StringBuffer rmb : rm) {
				cache.remove(rmb);
			}
			// add current character to cache
			cache.add(new StringBuffer(wbDecoding.get(i)));
		}

		for (Integer in : wbDecoding.keySet()) {
			System.out.println(in + ":" + wbDecoding.get(in));
		}

		return rv.toString();
	}

	private BitStream encodedListToBitStream(List<Integer> encoded) {
		BitStream out = new BitStream();

		StringBuffer sb = new StringBuffer();
		for (int i : encoded) {
			String s = Integer.toBinaryString(i);
			s = ("000000000000" + s);
			s = s.substring(s.length() - 12, s.length());

			out.add(s);
		}
		return out;
	}
}

import java.io.*;
import java.util.*;

public class cachesim{
	public static void main(String[] args){
		String fileName = args[0];

		File inputFile = new File(fileName);
		BufferedReader br = null;

		System.out.println("\nAll sets,tags,block given in decimal form\n");

		try{
			 br = new BufferedReader(new FileReader(inputFile));
		}catch(FileNotFoundException e1){
			System.out.println("File: was not found!");
		}

		String line;
		StringTokenizer st;
		ArrayList<String> memOps = new ArrayList<String>();

		int number_of_cache_levels = 0, latency_of_main_memory = 0, lineNumber = 1;
		int L1_latency = 0, L1_size = 0, L1_associativity = 0, L1_blockSize = 0;
		int L2_latency = 0, L2_size = 0, L2_associativity = 0, L2_blockSize = 0;
		int L3_latency = 0, L3_size = 0, L3_associativity = 0, L3_blockSize = 0;

		Cache L1 = null, L2 = null, L3 = null;

		// Cycles through each line in the input file
		try{
			for(int i = 1; (line = br.readLine()) != null; i++){

				st = new StringTokenizer(line," ");

				// If we are reading the 1st line
				if(i == 1){
					number_of_cache_levels = Integer.parseInt(st.nextToken());
					latency_of_main_memory = Integer.parseInt(st.nextToken());
				}
				// If we are reading the 2nd line
				else if(i == 2){
					st.nextToken();
					L1_latency =  Integer.parseInt(st.nextToken());
					L1_size = Integer.parseInt(st.nextToken());
					L1_associativity = Integer.parseInt(st.nextToken());
					L1_blockSize = Integer.parseInt(st.nextToken());

					L1 = new Cache(L1_size, L1_associativity, L1_blockSize, L1_latency);
				}
				// If we are reading the 3rd line
				else if(i == 3 && number_of_cache_levels >= 2){
					st.nextToken();
					L2_latency = Integer.parseInt(st.nextToken());
					L2_size = Integer.parseInt(st.nextToken());
					L2_associativity = Integer.parseInt(st.nextToken());
					L2_blockSize = Integer.parseInt(st.nextToken());

					L2 = new Cache(L2_size, L2_associativity, L2_blockSize, L2_latency);
				}
				// If we are reading the 4th line
				else if(i == 4 && number_of_cache_levels == 3){
					st.nextToken();
					L3_latency = Integer.parseInt(st.nextToken());
					L3_size = Integer.parseInt(st.nextToken());
					L3_associativity = Integer.parseInt(st.nextToken());
					L3_blockSize = Integer.parseInt(st.nextToken());

					L3 = new Cache(L3_size, L3_associativity, L3_blockSize, L3_latency);
				}
				else{
					// Add the memory operations to the arrayList
					memOps.add(line);
				}
			}
		}catch(IOException e2){}

		String op, currentAddress, hitOrMiss = null;
		StringBuffer sb;
		int totalCycles = 0, tempCycles = 0, L1hits = 0, L1wbs = 0, L2hits = 0, L2wbs = 0, L3hits = 0, L3wbs = 0, opCount = 0;

		// Cycle through the various memory operations
		for(String s : memOps){
			opCount += 1;
			op = s.substring(0,s.indexOf(" "));
			currentAddress = s.substring(s.indexOf(" ") + 1);
			sb = new StringBuffer("\n" + op + " " + currentAddress);
			tempCycles = 0;

			// If number of cycles in cache = 1
			if(number_of_cache_levels == 1){
				// Get latency for the 1st level
				totalCycles += L1_latency;
				tempCycles += L1_latency;

				// Perform read or write
				if(op.equals("READ")){
					hitOrMiss = L1.read(currentAddress);
				}
				else if(op.equals("WRITE")){
					hitOrMiss = L1.write(currentAddress);
				}

				sb.append("\nL1: " + op.toLowerCase() + " ");

				// If instruciton was a miss
				if(hitOrMiss.equals("miss")){
					sb.append("miss, ");
					// Get latency of main memory
					totalCycles += latency_of_main_memory;
					tempCycles += latency_of_main_memory;
				}
				else{
					// If it is a hit, Level 1 hits increased
					sb.append("hit, ");
					L1hits += 1;
				}

				sb.append("tag " + L1.getTag(currentAddress) + ", set " + L1.getSet(currentAddress) + ", block " + L1.getBlock(currentAddress) + ", ");

				// If there was no writeback
				if( ! Cache.writeBack){
					sb.append("no ");
				}
				else{
					// Increase level 1 writebacks
					L1wbs += 1;
				}

				sb.append("writeback\n" + tempCycles + " cycles\n");
			}

			// If number of cycles in cache = 2
			else if(number_of_cache_levels == 2){
				//Get latency of 1st cache level
				totalCycles += L1_latency;
				tempCycles += L1_latency;

				// Perform read or write
				if(op.equals("READ")){
					hitOrMiss = L1.read(currentAddress);
				}
				else if(op.equals("WRITE")){
					hitOrMiss = L1.write(currentAddress);
				}

				sb.append("\nL1: " + op.toLowerCase() + " ");

				// If op on 1st level was a miss
				if(hitOrMiss.equals("miss")){
					// Get latency of 2nd cache level
					totalCycles += L2_latency;
					tempCycles += L2_latency;
					sb.append("miss, tag " + L1.getTag(currentAddress) + ", set " + L1.getSet(currentAddress) + ", block " + L1.getBlock(currentAddress) + ", ");

					// If not a writeback
					if( ! Cache.writeBack){
						sb.append("no ");
					}
					else{
						// Increase level 1 writebacks
						L1wbs += 1;
					}

					sb.append("writeback");

					// Perform operation on 2nd level
					if(op.equals("READ")){
						hitOrMiss = L2.read(currentAddress);
					}
					else if(op.equals("WRITE")){
						hitOrMiss = L2.write(currentAddress);
					}

					sb.append("\nL2: " + op.toLowerCase() + " ");

					// If op was a miss on the 2nd level
					if(hitOrMiss.equals("miss")){
						sb.append("miss, ");
						//Get latency for main memory
						totalCycles += latency_of_main_memory;
						tempCycles += latency_of_main_memory;
					}
					else{
						// If op was a hit, increase level 2 hits
						L2hits += 1;
						sb.append("hit, ");
					}

					sb.append("tag " + L2.getTag(currentAddress) + ", set " + L2.getSet(currentAddress) + ", block " + L2.getBlock(currentAddress) + ", ");

					// If not a writeback
					if( ! Cache.writeBack){
						sb.append("no ");
					}
					else{
						// if writeback, increase level 2 writebacks
						L2wbs += 1;
					}

					sb.append("writeback");
				}
				else{
					// If op on 1st level was a hit, increase level 1 hits

					L1hits += 1;
					sb.append("hit, tag " + L1.getTag(currentAddress) + ", set " + L1.getSet(currentAddress) + ", block " + L1.getBlock(currentAddress) + ", ");

					// If not a writeback
					if( ! Cache.writeBack){
						sb.append("no ");
					}
					else{
						// If writeback, increase level 1 writebacks
						L1wbs += 1;
					}

					sb.append("writeback");
					sb.append("\nL2: no access, tag " + L2.getTag(currentAddress) + ", set " + L2.getSet(currentAddress) + ", block " + L2.getBlock(currentAddress) + ", no writeback");
				}
				sb.append("\n" + tempCycles + " cycles");
			}
			else if(number_of_cache_levels == 3){
				// Get latency for 1st cache level
				totalCycles += L1_latency;
				tempCycles += L1_latency;

				// Perform op on level 1
				if(op.equals("READ")){
					hitOrMiss = L1.read(currentAddress);
				}
				else if(op.equals("WRITE")){
					hitOrMiss = L1.write(currentAddress);
				}

				sb.append("\nL1: " + op.toLowerCase() + " ");

				// If op on level 1 a miss
				if(hitOrMiss.equals("miss")){
					// Get latency of 2nd level
					totalCycles += L2_latency;
					tempCycles += L2_latency;
					sb.append("miss, tag " + L1.getTag(currentAddress) + ", set " + L1.getSet(currentAddress) + ", block " + L1.getBlock(currentAddress) + ", ");

					// If not a writeback
					if( ! Cache.writeBack){
						sb.append("no ");
					}
					else{
						// IF a writeback, increase level 1 writebacks by 1
						L1wbs += 1;
					}

					sb.append("writeback");

					// Perform op on 2nd level
					if(op.equals("READ")){
						hitOrMiss = L2.read(currentAddress);
					}
					else if(op.equals("WRITE")){
						hitOrMiss = L2.write(currentAddress);
					}

					sb.append("\nL2: " + op.toLowerCase() + " ");

					// If op on 2nd level is a miss
					if(hitOrMiss.equals("miss")){
						// Get latency of 3rd level
						totalCycles += L3_latency;
						tempCycles += L3_latency;
						sb.append("miss, tag " + L2.getTag(currentAddress) + ", set " + L2.getSet(currentAddress) + ", block " + L2.getBlock(currentAddress) + ", ");

						// If not a writeback
						if( ! Cache.writeBack){
							sb.append("no ");
						}
						else{
							// If a writeback, increase level 2 writebacks
							L2wbs += 1;
						}

						sb.append("writeback");

						// Perform op on level 3 cache
						if(op.equals("READ")){
							hitOrMiss = L3.read(currentAddress);
						}
						else if(op.equals("WRITE")){
							hitOrMiss = L3.write(currentAddress);
						}

						sb.append("\nL3: " + op.toLowerCase() + " ");

						// If op on level 3 is s miss
						if(hitOrMiss.equals("miss")){
							sb.append("miss, ");
							// Get latency of main memory
							totalCycles += latency_of_main_memory;
							tempCycles += latency_of_main_memory;
						}
						else{
							// If op on level 3 is a hit, increase level 3 hits by 1
							sb.append("hit, ");
							L3hits += 1;
						}

						sb.append("tag " + L3.getTag(currentAddress) + ", set " + L3.getSet(currentAddress) + ", block " + L3.getBlock(currentAddress) + ", ");

						// If no writebacks
						if( ! Cache.writeBack){
							sb.append("no ");
						}
						else{
							// If writebacks, increase level 3 writebacks by 1
							L3wbs += 1;
						}

						sb.append("writeback");
					}
					else{
						// If op on level 2 is a hit, increase level 2 hits by 1
						L2hits += 1;
						sb.append("hit, tag " + L2.getTag(currentAddress) + ", set " + L2.getSet(currentAddress) + ", block " + L2.getBlock(currentAddress) + ", ");

						// If no writebacks
						if( ! Cache.writeBack){
							sb.append("no ");
						}
						else{
							// Increase level 2 writebacks by 1
							L2wbs += 1;
						}
						sb.append("writeback");
						sb.append("\nL3: no access, tag " + L3.getTag(currentAddress) + ", set " + L3.getSet(currentAddress) + ", block " + L3.getBlock(currentAddress) + ", no writeback");
					}
				}
				else{
					// If op on level 1 is a hit, increase level 1 hits by 1
					L1hits += 1;
					sb.append("hit, tag " + L1.getTag(currentAddress) + ", set " + L1.getSet(currentAddress) + ", block " + L1.getBlock(currentAddress) + ", ");

					// If no writebacks
					if( ! Cache.writeBack){
						sb.append("no ");
					}
					else{
						// If writeback, increase level 1 writebacks by 1
						L1wbs += 1;
					}

					sb.append("writeback");
					sb.append("\nL2: no access, tag " + L2.getTag(currentAddress) + ", set " + L2.getSet(currentAddress) + ", block " + L2.getBlock(currentAddress) + ", no writeback");
					sb.append("\nL3: no access, tag " + L3.getTag(currentAddress) + ", set " + L3.getSet(currentAddress) + ", block " + L3.getBlock(currentAddress) + ", no writeback");
				}

				sb.append("\n" + tempCycles + " cycles");
			}

			System.out.println(sb + "");
		}

		System.out.println("\nNumber of memory addresses: " + opCount);

		// Print level 1 info
		if(number_of_cache_levels >= 1){
			System.out.println("L1 number of cache hits: " + L1hits);
			System.out.println("L1 number of writebacks: " + L1wbs);
		}

		// Print level 2 info
		if(number_of_cache_levels >= 2){
			System.out.println("L2 number of cache hits: " + L2hits);
			System.out.println("L2 number of writebacks: " + L2wbs);
		}

		// Print level 3 info
		if(number_of_cache_levels == 3){
			System.out.println("L3 number of cache hits: " + L3hits);
			System.out.println("L3 number of writebacks: " + L3wbs);
		}

		System.out.println("Total cycles: " + totalCycles + "\n");
	}

	// Converts hex strings to its binary equivalent
	public static String hexToBin(String hex){
		StringBuffer sb = new StringBuffer("");

		for(int i=0; i < hex.length(); i++){
			String ch = "" + hex.charAt(i);

			if(ch.equals("0")){
				sb.append("0000");
			}
			else if(ch.equals("1")){
				sb.append("0001");
			}
			else if(ch.equals("2")){
				sb.append("0010");
			}
			else if(ch.equals("3")){
				sb.append("0011");
			}
			else if(ch.equals("4")){
				sb.append("0100");
			}
			else if(ch.equals("5")){
				sb.append("0101");
			}
			else if(ch.equals("6")){
				sb.append("0110");
			}
			else if(ch.equals("7")){
				sb.append("0111");
			}
			else{
				sb.append(Integer.toBinaryString(Integer.parseInt(ch,16)));
			}
		}

		return "" + sb;
	}
}
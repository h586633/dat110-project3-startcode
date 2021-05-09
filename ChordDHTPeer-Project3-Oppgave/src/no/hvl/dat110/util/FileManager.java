package no.hvl.dat110.util;


/**
 * @author tdoy
 * dat110 - project 3
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import no.hvl.dat110.middleware.Message;
import no.hvl.dat110.middleware.Node;
import no.hvl.dat110.rpc.interfaces.NodeInterface;
import no.hvl.dat110.util.Hash;

public class FileManager {
	
	private BigInteger[] replicafiles;							// array stores replicated files for distribution to matching nodes
	private int numReplicas;									// let's assume each node manages nfiles (5 for now) - can be changed from the constructor
	private NodeInterface chordnode;
	private String filepath; 									// absolute filepath
	private String filename;									// only filename without path and extension
	private BigInteger hash;
	private byte[] bytesOfFile;
	private String sizeOfByte;
	
	private Set<Message> activeNodesforFile = null;
	
	public FileManager(NodeInterface chordnode) throws RemoteException {
		this.chordnode = chordnode;
	}
	
	public FileManager(NodeInterface chordnode, int N) throws RemoteException {
		this.numReplicas = N;
		replicafiles = new BigInteger[N];
		this.chordnode = chordnode;
	}
	
	public FileManager(NodeInterface chordnode, String filepath, int N) throws RemoteException {
		this.filepath = filepath;
		this.numReplicas = N;
		replicafiles = new BigInteger[N];
		this.chordnode = chordnode;
	}
	
	public void createReplicaFiles() throws NoSuchAlgorithmException, UnsupportedEncodingException {
	 	
		// implement
		
		// set a loop where size = numReplicas
		
		for (int i = 0; i < Util.numReplicas; i++) {
			filename += i;
			BigInteger hash = Hash.hashOf(filename);
			replicafiles [i] = hash;
			}
		
		// replicate by adding the index to filename
		
		// hash the replica
		
		// store the hash in the replicafiles array.

		}
	
    /**
     * 
     * @param bytesOfFile
     * @throws RemoteException 
     * @throws NoSuchAlgorithmException 
     */
    public int distributeReplicastoPeers() throws RemoteException, NoSuchAlgorithmException {
    	int counter = 0;
    	
    	// Task1: Given a filename, make replicas and distribute them to all active peers such that: pred < replica <= peer
    	
    	// Task2: assign a replica as the primary for this file. Hint, see the slide (project 3) on Canvas
    	
    	// create replicas of the filename
    	
		// iterate over the replicas
    	
    	// for each replica, find its successor by performing findSuccessor(replica)
    	
    	// call the addKey on the successor and add the replica
    	
    	// call the saveFileContent() on the successor
    	
    	// increment counter
    	
    	Random random = new Random();	//modification for task 5
		int rand = random.nextInt(replicafiles.length);
    	for (int i = 0; i < replicafiles.length; i++) {
			NodeInterface successor = chordnode.findSuccessor(replicafiles[i]);
			successor.addKey(replicafiles[i]);
			if (i == rand){		//modification for task 5, randomly assigning a random peer to the file
				successor.saveFileContent(filename, replicafiles[i], bytesOfFile, true);
			} else {
				successor.saveFileContent(filename, replicafiles[i], bytesOfFile, false);
			}
			counter++;
		}
    		
		return counter;
    }
	
	/**
	 * 
	 * @param filename
	 * @return list of active nodes having the replicas of this file
	 * @throws RemoteException 
	 * @throws NoSuchAlgorithmException 
	 */
	public Set<Message> requestActiveNodesForFile(String filename) throws RemoteException, NoSuchAlgorithmException {
		
		this.filename = filename;
		Set<Message> succinfo = new HashSet<Message>();
		// Task: Given a filename, find all the peers that hold a copy of this file
		
		// generate the N replicas from the filename by calling createReplicaFiles()
		
		// it means, iterate over the replicas of the file
		
		// for each replica, do findSuccessor(replica) that returns successor s.
		
		// get the metadata (Message) of the replica from the successor, s (i.e. active peer) of the file
		
		// save the metadata in the set succinfo.
		
		//this.activeNodesforFile = succinfo;
		
		this.filename = filename;
		this.activeNodesforFile = succinfo;

		try {
			createReplicaFiles();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < replicafiles.length; i++){
			NodeInterface successor = chordnode.findSuccessor(replicafiles[i]);
			succinfo.add(successor.getFilesMetadata(replicafiles[i]));
		}

		return succinfo;
	}
	
	/**
	 * Find the primary server - Remote-Write Protocol
	 * @return 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws RemoteException 
	 */
	public NodeInterface findPrimaryOfItem() throws RemoteException, NoSuchAlgorithmException, UnsupportedEncodingException {

		// Task: Given all the active peers of a file (activeNodesforFile()), find which is holding the primary copy
		
		// iterate over the activeNodesforFile
		
		// for each active peer (saved as Message)
		
		// use the primaryServer boolean variable contained in the Message class to check if it is the primary or not
		
		// return the primary
		
		for (Message m : activeNodesforFile) {
			if (m.isPrimaryServer()) {
				NodeInterface stub = Util.getProcessStub(m.getNodeIP(), m.getPort());
				return stub;
			}
		}
			
		return null;
			
			
		//Set<Message> primary = activeNodesforFile.stream().filter(a -> a.isPrimaryServer()).collect(Collectors.toSet()); 
			/*if (activeNodesforFile.size() < 0) {
				System.out.println("No primary elements found for the given file!");
			}
			activeNodesforFile.
			if (primaryNodesforFile.getNameOfFile() == filename){
				return Util.getProcessStub(prim.getNodeIP(), prim.getPort());
			} else {
				System.out.println("No primary nodes found for file " + filename + " ! FileManager.findPrimaryOfItem()");
			}
			*/
		}
	
		
		
		/*assert primary.size() > 0 : "No primary elements found for the given file!";
		while (primary.iterator().hasNext()){
			Message prim = primary.iterator().next();
			if (prim.getNameOfFile() == filename){
				return Util.getProcessStub(prim.getNodeIP(), prim.getPort());
			} else {
				System.out.println("No primary nodes found for file " + filename + " ! FileManager.findPrimaryOfItem()");
			}
		}
		return null;*/
	
	
    /**
     * Read the content of a file and return the bytes
     * @throws IOException 
     * @throws NoSuchAlgorithmException 
     */
    public void readFile() throws IOException, NoSuchAlgorithmException {
    	
    	File f = new File(filepath);
    	
    	byte[] bytesOfFile = new byte[(int) f.length()];
    	
		FileInputStream fis = new FileInputStream(f);
        
        fis.read(bytesOfFile);
		fis.close();
		
		//set the values
		filename = f.getName().replace(".txt", "");		
		hash = Hash.hashOf(filename);
		this.bytesOfFile = bytesOfFile;
		double size = (double) bytesOfFile.length/1000;
		NumberFormat nf = new DecimalFormat();
		nf.setMaximumFractionDigits(3);
		sizeOfByte = nf.format(size);
		
		System.out.println("filename="+filename+" size="+sizeOfByte);
    	
    }
    
    public void printActivePeers() {
    	
    	activeNodesforFile.forEach(m -> {
    		String peer = m.getNodeIP();
    		String id = m.getNodeID().toString();
    		String name = m.getNameOfFile();
    		String hash = m.getHashOfFile().toString();
    		int size = m.getBytesOfFile().length;
    		
    		System.out.println(peer+": ID = "+id+" | filename = "+name+" | HashOfFile = "+hash+" | size ="+size);
    		
    	});
    }

	/**
	 * @return the numReplicas
	 */
	public int getNumReplicas() {
		return numReplicas;
	}
	
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * @return the hash
	 */
	public BigInteger getHash() {
		return hash;
	}
	/**
	 * @param hash the hash to set
	 */
	public void setHash(BigInteger hash) {
		this.hash = hash;
	}
	/**
	 * @return the bytesOfFile
	 */ 
	public byte[] getBytesOfFile() {
		return bytesOfFile;
	}
	/**
	 * @param bytesOfFile the bytesOfFile to set
	 */
	public void setBytesOfFile(byte[] bytesOfFile) {
		this.bytesOfFile = bytesOfFile;
	}
	/**
	 * @return the size
	 */
	public String getSizeOfByte() {
		return sizeOfByte;
	}
	/**
	 * @param size the size to set
	 */
	public void setSizeOfByte(String sizeOfByte) {
		this.sizeOfByte = sizeOfByte;
	}

	/**
	 * @return the chordnode
	 */
	public NodeInterface getChordnode() {
		return chordnode;
	}

	/**
	 * @return the activeNodesforFile
	 */
	public Set<Message> getActiveNodesforFile() {
		return activeNodesforFile;
	}

	/**
	 * @return the replicafiles
	 */
	public BigInteger[] getReplicafiles() {
		return replicafiles;
	}

	/**
	 * @param filepath the filepath to set
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
}

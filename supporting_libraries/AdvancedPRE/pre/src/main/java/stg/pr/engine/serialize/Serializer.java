/**
 * Copyright (c) 2014 Mastek Ltd. All rights reserved.
 * 
 * This file is part of JBEAM. JBEAM is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation.
 *
 * JBEAM is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for the specific language governing permissions and 
 * limitations.
 *
 *
 * $Revision: 31105 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/serialize/Serializer.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log:  $
 *
 */
package stg.pr.engine.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import com.stg.crypto.IOObfuscationUtility;

/**
 * A class that helps to persist objects to a given file.
 * 
 * This class uses an internal obfuscation routine that obfuscates the 
 * output stream.
 *
 * @author Kedar Raybagkar
 * @version $Revision: 31105 $
 * @since V1.0R28.x
 *
 */
public class Serializer {

	/**
	 * Serialize the given {@link Serializable} object array to the given file.
	 * 
	 * @param objects
	 * @param file
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NullPointerException if file is null
	 */
	public void serialize(Serializable[] objects, File file) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		if (objects == null || objects.length == 0) {
			return;
		}
        if (file == null) {
            throw new NullPointerException("File cannot be null");
        }
        if (file.isDirectory()) {
            throw new FileNotFoundException("File cannot be a directory");
        }
		IOObfuscationUtility obfuscator = new IOObfuscationUtility();
        DataOutputStream objDOS = null;
        FileOutputStream fos = null;
        try {
        	fos = new FileOutputStream(file);
            objDOS = new DataOutputStream(obfuscator.getObfuscatedOutputStream(fos, Cipher.ENCRYPT_MODE));
            for (int i = 0; i < objects.length; i++) {
            	ByteArrayOutputStream objBAOS = new ByteArrayOutputStream();
            	ObjectOutputStream objOOS = new ObjectOutputStream(objBAOS);
                try {
                    objOOS.writeObject(objects[i]);
                    objOOS.flush();
                    objOOS.close();
                    int size = objBAOS.size();
                    objDOS.writeInt(size);
                    objBAOS.writeTo(objDOS);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                	objOOS.close();
                }
            }
        } finally {
            if (objDOS != null) {
                objDOS.close();
            }
            if (fos != null) {
            	fos.close();
            }
        }
	}
	
	/**
	 * Loads the {@link Serializable} objects from the given file.
	 * Returns an array of {@link Serializable}.
	 * 
	 * @param file to be loaded.
	 * @return {@link Serializable}[]
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 */
	public Serializable[] load(File file) throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        if (file == null) {
            return new Serializable[0]; 
        }
        if (!file.exists()) return new Serializable[0];
        if (file.isDirectory()) {
            throw new FileNotFoundException("File is a directory #" + file.getName());
        }
        if (!file.canRead()) {
            throw new IOException("Read permission needed on file " + file.getName());
        }
        IOObfuscationUtility obfuscator = new IOObfuscationUtility();
        
        FileInputStream objFIS = null;
        DataInputStream objDIN = null;
        try {
        	//Get the datainput stream to the underlying file.
        	objFIS = new FileInputStream(file);
			objDIN = new DataInputStream(obfuscator.getObfuscatedInputStream(objFIS,Cipher.DECRYPT_MODE));
        	List<Serializable> alist = new ArrayList<Serializable>();
        	while (true) {
        		int size;
        		try {
        			size = objDIN.readInt();
        		}
        		catch (EOFException e) {
        			try {
        				objFIS.close();
        			} catch (Throwable t) {
        			}
        			try {
        				if (!file.delete()) {
        					System.out.println("Unable to delete the file " + file.getPath());
        				}
					} catch (SecurityException e2) {
						System.out.println("Unable to delete the file due to SecurityException " + file.getPath());
						e2.printStackTrace();
					}
        			
        			Serializable[] objects = new Serializable[alist.size()];
        			alist.toArray(objects);
        			return objects;
        		}
        		byte[] arr = new byte[size];
        		objDIN.readFully(arr);
        		ByteArrayInputStream bin = null;
        		ObjectInputStream oin = null;
        		try {
        			bin = new ByteArrayInputStream(arr);
					oin = new ObjectInputStream(bin);
        			Serializable obj = (Serializable) oin.readObject();
        			alist.add(obj);
        			oin.close();
				} finally {
					if (oin != null) {
						oin.close();
					}
				}
        	}
		} finally {
		    if (objDIN != null) {
		        objDIN.close();
		    }
			if (objFIS != null) {
				objFIS.close();
			}
		}
	}
}

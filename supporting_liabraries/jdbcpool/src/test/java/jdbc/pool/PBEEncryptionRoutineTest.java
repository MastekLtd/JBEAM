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
 */
package jdbc.pool;

import java.util.ArrayList;
import java.util.Iterator;

import com.stg.crypto.PBEEncryptionRoutine;

import junit.framework.TestCase;

public class PBEEncryptionRoutineTest extends TestCase {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public final static String REVISION = "$Revision:: 2                 $";
    
    public void testEncode() {
        PBEEncryptionRoutine encryptor = new PBEEncryptionRoutine();
        String strToBeEcnrypted = "STRINGTOBEENCRYPTED";
        String strEncrypted1 = encryptor.encode(strToBeEcnrypted);
        assertNotSame("Test Encrypted String is not same as passed ", strToBeEcnrypted, strEncrypted1);
        String strEncrypted2 = encryptor.encode(strToBeEcnrypted);
        assertNotSame("Same String encrypted twice with the same instance should not return the same encrypted string ", 
                strEncrypted1, strEncrypted2);
    }

    public void testDecode1() {
        PBEEncryptionRoutine encryptor = new PBEEncryptionRoutine();
        String strToBeEcnrypted = "STRINGTOBEENCRYPTED";
        String strEncrypted1 = encryptor.encode(strToBeEcnrypted);
        
        PBEEncryptionRoutine routine2 = new PBEEncryptionRoutine();
        String strDecoded = routine2.decode(strEncrypted1);
        
        assertEquals("Decrypt the encrypted key with different Encryption routine instance.", strToBeEcnrypted, strDecoded);
    }

    public void TtestDecode2() {
        String strToBeEcnrypted = "STRINGTOBEENCRYPTED";
        try {
            PBEEncryptionRoutine routine2 = new PBEEncryptionRoutine();
            routine2.decode(strToBeEcnrypted);
            fail("Decoded an unecrypted string");
        } catch (SecurityException e) {
            assertTrue("Passed as SecurityException was thrown for decoding decoded ", e.getMessage().startsWith("Could not decrypt"));
        }
    }
    
    
    public void testLoad() {
        ArrayList list = new ArrayList();
        String strToBeEcnrypted = "STRINGTOBEENCRYPTED";
        for (int i = 0; i < 5000; i++) {
            PBEEncryptionRoutine encryptor = new PBEEncryptionRoutine();
            String strEncrypted = encryptor.encode(strToBeEcnrypted);
            if (list.contains(strEncrypted)) {
                fail("Generated the same password again @ " + i + " run.");
            } else {
                list.add(strEncrypted);
            }
        }
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            String object = (String) iter.next();
            PBEEncryptionRoutine decrypt = new PBEEncryptionRoutine();
            assertEquals(decrypt.decode(object), strToBeEcnrypted);
        }
    }
    
}
